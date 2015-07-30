/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr.version

import java.util.regex.Matcher
import java.util.regex.Pattern

import org.gradle.api.UncheckedIOException
import org.gradle.nativeplatform.platform.internal.ArchitectureInternal
import org.gradle.nativeplatform.platform.internal.Architectures
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.process.ExecResult
import org.gradle.process.internal.ExecAction
import org.gradle.process.internal.ExecActionFactory
import org.gradle.util.TreeVisitor
import org.gradle.util.VersionNumber

import com.google.common.base.Joiner

/**
 * Given a File pointing to an (existing) avr-gcc/avr-g++ binary, extracts
 * the version number and default architecture by running with -dM -E and
 * scraping the output.
 * 
 * @author cams7
 *
 */
class AvrVersionDeterminer implements AvrCompilerMetaDataProvider {
	private static final Pattern DEFINE_PATTERN = Pattern.compile("\\s*#define\\s+(\\S+)\\s+(.*)")
	private final ExecActionFactory execActionFactory

	public AvrVersionDeterminer(ExecActionFactory execActionFactory) {
		this.execActionFactory = execActionFactory
	}

	public static AvrVersionDeterminer forAvr(ExecActionFactory execActionFactory) {
		return new AvrVersionDeterminer(execActionFactory)
	}

	@Override
	public AvrVersionResult getAvrMetaData(File avrBinary, 	List<String> args) {
		List<String> allArgs = new ArrayList<String>(args)
		allArgs.add("-dM")
		allArgs.add("-E")
		allArgs.add("-")
		String output = transform(avrBinary, allArgs)

		println "avrMetadata: " + output

		if (output == null)
			return new BrokenResult(String.format("Could not determine %s version: failed to execute %s %s.", getDescription(), avrBinary.getName(), Joiner.on(' ').join(allArgs)))

		return transform(output, avrBinary)
	}

	private String getDescription() {
		return "AVR Version";
	}

	private String transform(File avrBinary, List<String> args) {
		ExecAction exec = execActionFactory.newExecAction()
		exec.executable(avrBinary.getAbsolutePath())
		exec.setWorkingDir(avrBinary.getParentFile())
		exec.args(args)
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		exec.setStandardOutput(baos)
		exec.setErrorOutput(new ByteArrayOutputStream())
		exec.setIgnoreExitValue(true)
		ExecResult result = exec.execute()

		int exitValue = result.getExitValue()
		if (exitValue == 0)
			return new String(baos.toByteArray())

		return null
	}

	private AvrVersionResult transform(String output, File avrBinary) {
		BufferedReader reader = new BufferedReader(new StringReader(output))
		String line
		Map<String, String> defines = new HashMap<String, String>()
		try {
			while ((line = reader.readLine()) != null) {
				Matcher matcher = DEFINE_PATTERN.matcher(line)
				if (!matcher.matches())
					return new BrokenResult(String.format("Could not determine %s version: %s produced unexpected output.", getDescription(), avrBinary.getName()))

				defines.put(matcher.group(1), matcher.group(2))
			}
		} catch (IOException e) {
			// Should not happen reading from a StringReader
			throw new UncheckedIOException(e)
		}

		if (!defines.containsKey("__GNUC__"))
			return new BrokenResult(String.format("Could not determine %s version: %s produced unexpected output.", getDescription(), avrBinary.getName()))

		int major = toInt(defines.get("__GNUC__"))
		int minor = toInt(defines.get("__GNUC_MINOR__"))
		int patch = toInt(defines.get("__GNUC_PATCHLEVEL__"))

		VersionNumber version = new VersionNumber(major, minor, patch, null)
		final ArchitectureInternal architecture = determineArchitecture(defines)

		return new DefaultAvrVersionResult(version, architecture)
	}

	private ArchitectureInternal determineArchitecture(Map<String, String> defines) {
		boolean i386 = defines.containsKey("__i386__")
		boolean amd64 = defines.containsKey("__amd64__")

		if (i386)
			return Architectures.forInput("i386")

		if (amd64)
			return Architectures.forInput("amd64")

		return DefaultNativePlatform.getCurrentArchitecture()
	}

	private int toInt(String value) {
		if (value == null)
			return 0

		try {
			return Integer.parseInt(value)
		} catch (NumberFormatException e) {
			return 0
		}
	}

	private static class DefaultAvrVersionResult implements AvrVersionResult {
		final VersionNumber version
		final ArchitectureInternal defaultArchitecture

		public DefaultAvrVersionResult(VersionNumber version, ArchitectureInternal defaultArchitecture) {
			this.version = version
			this.defaultArchitecture = defaultArchitecture
		}

		public boolean isAvailable() {
			return true
		}

		public void explain(TreeVisitor<? super String> visitor) {
		}
	}

	private static class BrokenResult implements AvrVersionResult {
		private final String message

		private BrokenResult(String message) {
			this.message = message
		}

		public VersionNumber getVersion() {
			throw new UnsupportedOperationException()
		}

		public ArchitectureInternal getDefaultArchitecture() {
			throw new UnsupportedOperationException()
		}

		public boolean isAvailable() {
			return false
		}

		public void explain(TreeVisitor<? super String> visitor) {
			visitor.node(message)
		}
	}
}
