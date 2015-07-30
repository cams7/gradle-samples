/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import static java.util.Arrays.asList

import org.gradle.api.Action
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.Actions
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.internal.os.OperatingSystem
import org.gradle.internal.reflect.Instantiator
import org.gradle.nativeplatform.platform.internal.NativePlatformInternal
import org.gradle.nativeplatform.toolchain.NativePlatformToolChain
import org.gradle.nativeplatform.toolchain.internal.ExtendableToolChain
import org.gradle.nativeplatform.toolchain.internal.PlatformToolProvider
import org.gradle.nativeplatform.toolchain.internal.UnavailablePlatformToolProvider
import org.gradle.nativeplatform.toolchain.internal.gcc.version.GccVersionResult
import org.gradle.nativeplatform.toolchain.internal.tools.CommandLineToolSearchResult
import org.gradle.nativeplatform.toolchain.internal.tools.ToolSearchPath
import org.gradle.nativeplatform.toolchain.internal.tools.ToolSearchPath.FoundTool
import org.gradle.platform.base.internal.toolchain.ToolChainAvailability
import org.gradle.process.internal.ExecActionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import br.com.cams7.nativeplatform.toolchain.AvrCompatibleToolChain
import br.com.cams7.nativeplatform.toolchain.AvrPlatformToolChain
import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType
import br.com.cams7.nativeplatform.toolchain.internal.avr.version.AvrCompilerMetaDataProvider
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrCommandLineToolConfigurationInternal
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrMissingTool
import br.com.cams7.nativeplatform.toolchain.internal.tools.DefaultAvrCommandLineToolConfiguration

/**
 * @author cams7
 *
 */
abstract class AbstractAvrCompatibleToolChain extends ExtendableToolChain<AvrPlatformToolChain> implements AvrCompatibleToolChain {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAvrCompatibleToolChain.class)

	private final ExecActionFactory execActionFactory
	private final ToolSearchPath toolSearchPath
	private final List<AvrTargetPlatformConfiguration> platformConfigs = new ArrayList<AvrTargetPlatformConfiguration>()
	
	final AvrCompilerMetaDataProvider metaDataProvider
	private final Instantiator instantiator
	private int configInsertLocation

	public AbstractAvrCompatibleToolChain(String name, BuildOperationProcessor buildOperationProcessor, OperatingSystem operatingSystem, FileResolver fileResolver, ExecActionFactory execActionFactory, AvrCompilerMetaDataProvider metaDataProvider, Instantiator instantiator) {
		this(name, buildOperationProcessor, operatingSystem, fileResolver, execActionFactory, new ToolSearchPath(operatingSystem), metaDataProvider, instantiator)
	}

	private AbstractAvrCompatibleToolChain(String name, BuildOperationProcessor buildOperationProcessor, OperatingSystem operatingSystem, FileResolver fileResolver, ExecActionFactory execActionFactory, ToolSearchPath tools, AvrCompilerMetaDataProvider metaDataProvider, Instantiator instantiator) {
		super(name, buildOperationProcessor, operatingSystem, fileResolver)

		this.execActionFactory = execActionFactory

		tools.metaClass.locate << {AvrToolType key, String exeName ->
			File executable = executables.get(exeName)
			if (executable == null) {
				executable = findExecutable(operatingSystem, exeName)
				if (executable != null)
					executables.put(exeName, executable)
			}

			CommandLineToolSearchResult result

			if(executable == null || !executable.isFile())
				result = new AvrMissingTool(
						key, exeName, pathEntries)
			else
				result = new FoundTool(executable)

			return result
		}

		this.toolSearchPath = tools
		this.metaDataProvider = metaDataProvider
		this.instantiator = instantiator

		target(new Intel32Architecture())
		target(new Intel64Architecture())
		configInsertLocation = 0
	}

	protected CommandLineToolSearchResult locate(
			AvrCommandLineToolConfigurationInternal gccTool) {
		return toolSearchPath.locate(gccTool.getToolType(),
				gccTool.getExecutable())
	}

	public List<File> getPath() {
		return toolSearchPath.getPath()
	}

	public void path(Object... pathEntries) {
		for (Object path : pathEntries)
			toolSearchPath.path(resolve(path))
	}
	

	public void target(String platformName) {
		target(platformName, Actions.<NativePlatformToolChain> doNothing())
	}

	public void target(String platformName,
			Action<? super AvrPlatformToolChain> action) {
		target(new DefaultTargetPlatformConfiguration(asList(platformName),
				action))
	}

	public void target(List<String> platformNames,
			Action<? super AvrPlatformToolChain> action) {
		target(new DefaultTargetPlatformConfiguration(platformNames, action))
	}

	public void target(AvrTargetPlatformConfiguration targetPlatformConfiguration) {
		platformConfigs.add(configInsertLocation, targetPlatformConfiguration)
		configInsertLocation++
	}

	public PlatformToolProvider select(NativePlatformInternal targetPlatform) {
		AvrTargetPlatformConfiguration targetPlatformConfigurationConfiguration = getPlatformConfiguration(targetPlatform)
		ToolChainAvailability result = new ToolChainAvailability()
		if (targetPlatformConfigurationConfiguration == null) {
			result.unavailable(String.format(
					"Don't know how to build for platform '%s'.",
					targetPlatform.getName()))
			return new UnavailablePlatformToolProvider(
					targetPlatform.getOperatingSystem(), result)
		}

		DefaultAvrPlatformToolChain configurableToolChain = instantiator
				.newInstance(DefaultAvrPlatformToolChain.class, targetPlatform)
		addDefaultTools(configurableToolChain)
		configureDefaultTools(configurableToolChain)
		targetPlatformConfigurationConfiguration.apply(configurableToolChain)
		configureActions.execute(configurableToolChain)

		initTools(configurableToolChain, result)
		if (!result.isAvailable()) {
			return new UnavailablePlatformToolProvider(
					targetPlatform.getOperatingSystem(), result)
		}

		return new AvrPlatformToolProvider(buildOperationProcessor,
				targetPlatform.getOperatingSystem(), toolSearchPath,
				configurableToolChain, execActionFactory,
				configurableToolChain.isCanUseCommandFile())
	}

	protected void initTools(DefaultAvrPlatformToolChain platformToolChain,
			ToolChainAvailability availability) {
		// Attempt to determine whether the compiler is the correct
		// implementation
		boolean found = false
		for (AvrCommandLineToolConfigurationInternal tool : platformToolChain.getCompilers()) {
			CommandLineToolSearchResult compiler = locate(tool)
			if (compiler.isAvailable()) {
				GccVersionResult versionResult = getMetaDataProvider()
						.getGccMetaData(compiler.getTool(),
						platformToolChain.getCompilerProbeArgs())
				availability.mustBeAvailable(versionResult)
				if (!versionResult.isAvailable())
					return

				// Assume all the other compilers are ok, if they happen to be
				// installed
				LOGGER.debug("Found {} with version {}",
						AvrToolType.AVR_C_COMPILER.getToolName(), versionResult)
				found = true
				initForImplementation(platformToolChain, versionResult)
				break
			}
		}

		// Attempt to locate each tool
		for (AvrCommandLineToolConfigurationInternal tool : platformToolChain.getTools())
			found |= toolSearchPath.locate(tool.getToolType(), tool.getExecutable()).isAvailable()

		if (!found) {
			// No tools found - report just the C compiler as missing
			// TODO - report whichever tool is actually required, eg if there's
			// only assembler source, complain about the assembler
			AvrCommandLineToolConfigurationInternal cCompiler = platformToolChain
					.getAvrCCompiler()
			availability.mustBeAvailable(locate(cCompiler))
		}
	}

	protected void initForImplementation(
			DefaultAvrPlatformToolChain platformToolChain,
			GccVersionResult versionResult) {
	}

	private void addDefaultTools(DefaultAvrPlatformToolChain toolChain) {
		final String CROSS = "avr-"

		toolChain.add(instantiator.newInstance(
				DefaultAvrCommandLineToolConfiguration.class,
				AvrToolType.AVR_C_COMPILER, CROSS + "gcc"))
		toolChain.add(instantiator.newInstance(
				DefaultAvrCommandLineToolConfiguration.class,
				AvrToolType.AVR_CPP_COMPILER, CROSS + "g++"))
		toolChain.add(instantiator.newInstance(
				DefaultAvrCommandLineToolConfiguration.class,
				AvrToolType.AVR_LIB_ARCHIVER, CROSS + "ar"))
	}

	protected void configureDefaultTools(DefaultAvrPlatformToolChain toolChain) {
	}

	protected AvrTargetPlatformConfiguration getPlatformConfiguration(NativePlatformInternal targetPlatform) {
		for (AvrTargetPlatformConfiguration platformConfig : platformConfigs)
			if (platformConfig.supportsPlatform(targetPlatform))
				return platformConfig

		return null
	}

	private class Intel32Architecture implements AvrTargetPlatformConfiguration {

		public boolean supportsPlatform(NativePlatformInternal targetPlatform) {
			return targetPlatform.getOperatingSystem().isCurrent() && targetPlatform.getArchitecture().isI386()
		}

		public void apply(DefaultAvrPlatformToolChain gccToolChain) {
			gccToolChain.compilerProbeArgs("-m32")
			Action<List<String>> m32args = new Action<List<String>>() {
						public void execute(List<String> args) {
							args.add("-m32")
						}
					};
			gccToolChain.getAvrCppCompiler().withArguments(m32args)
			gccToolChain.getAvrCCompiler().withArguments(m32args)

		}
	}

	private class Intel64Architecture implements AvrTargetPlatformConfiguration {
		public boolean supportsPlatform(NativePlatformInternal targetPlatform) {
			return targetPlatform.getOperatingSystem().isCurrent() && targetPlatform.getArchitecture().isAmd64()
		}

		public void apply(DefaultAvrPlatformToolChain gccToolChain) {
			gccToolChain.compilerProbeArgs("-m64")
			Action<List<String>> m64args = new Action<List<String>>() {
						public void execute(List<String> args) {
							args.add("-m64")
						}
					};
			gccToolChain.getAvrCppCompiler().withArguments(m64args)
			gccToolChain.getAvrCCompiler().withArguments(m64args)
		}
	}

	private static class DefaultTargetPlatformConfiguration implements AvrTargetPlatformConfiguration {
		// TODO this should be a container of platforms
		private final Collection<String> platformNames
		private Action<? super AvrPlatformToolChain> configurationAction

		public DefaultTargetPlatformConfiguration(Collection<String> targetPlatformNames, Action<? super AvrPlatformToolChain> configurationAction) {
			this.platformNames = targetPlatformNames
			this.configurationAction = configurationAction
		}

		public boolean supportsPlatform(NativePlatformInternal targetPlatform) {
			return platformNames.contains(targetPlatform.getName())
		}

		public void apply(DefaultAvrPlatformToolChain platformToolChain) {
			configurationAction.execute(platformToolChain);
		}
	}
}
