/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain;

import org.gradle.api.Incubating;
import org.gradle.nativeplatform.toolchain.NativePlatformToolChain;

/**
 * AVR GCC specific settings for the tools used to build for a particular
 * platform.
 * 
 * @author cams7
 *
 */
@Incubating
public interface AvrPlatformToolChain extends NativePlatformToolChain {
	/**
	 * Returns the settings to use for the AVR C compiler.
	 */
	AvrCommandLineToolConfiguration getAvrCCompiler();

	/**
	 * Returns the settings to use for the AVR C++ compiler.
	 */
	AvrCommandLineToolConfiguration getAvrCppCompiler();

	/**
	 * Returns the settings to use for the archiver.
	 */
	AvrCommandLineToolConfiguration getAvrLibArchiver();

}
