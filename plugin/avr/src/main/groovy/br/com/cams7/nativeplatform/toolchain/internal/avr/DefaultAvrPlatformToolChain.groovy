/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.nativeplatform.platform.NativePlatform

import br.com.cams7.nativeplatform.toolchain.AvrCommandLineToolConfiguration
import br.com.cams7.nativeplatform.toolchain.AvrPlatformToolChain
import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrCommandLineToolConfigurationInternal
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrToolRegistry
import br.com.cams7.nativeplatform.toolchain.internal.tools.DefaultAvrCommandLineToolConfiguration

/**
 * @author cams7
 *
 */
class DefaultAvrPlatformToolChain implements AvrPlatformToolChain,
AvrToolRegistry {

	boolean canUseCommandFile = true
	final List<String> compilerProbeArgs = new ArrayList<String>()

	private final NativePlatform platform

	private final Map<AvrToolType, AvrCommandLineToolConfigurationInternal> tools = new HashMap<AvrToolType, AvrCommandLineToolConfigurationInternal>()

	public DefaultAvrPlatformToolChain(NativePlatform platform) {
		this.platform = platform
	}

	public void compilerProbeArgs(String... args) {
		getCompilerProbeArgs().addAll(Arrays.asList(args))
	}

	@Override
	public AvrCommandLineToolConfigurationInternal getTool(AvrToolType toolType) {
		return tools.get(toolType)
	}

	public Collection<AvrCommandLineToolConfigurationInternal> getTools() {
		return tools.values()
	}

	public Collection<AvrCommandLineToolConfigurationInternal> getCompilers() {
		return Arrays.asList(tools.get(AvrToolType.AVR_C_COMPILER),
				tools.get(AvrToolType.AVR_CPP_COMPILER))
	}

	public void add(DefaultAvrCommandLineToolConfiguration tool) {
		tools.put(tool.getToolType(), tool)
	}

	@Override
	public NativePlatform getPlatform() {
		return platform
	}

	@Override
	public AvrCommandLineToolConfiguration getAvrCCompiler() {
		return tools.get(AvrToolType.AVR_C_COMPILER)
	}


	@Override
	public AvrCommandLineToolConfiguration getAvrCppCompiler() {
		return tools.get(AvrToolType.AVR_CPP_COMPILER)
	}


	@Override
	public AvrCommandLineToolConfiguration getAvrLibArchiver() {
		return tools.get(AvrToolType.AVR_LIB_ARCHIVER)
	}
}
