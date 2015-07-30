/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr;

import org.gradle.nativeplatform.platform.internal.NativePlatformInternal;

/**
 * @author cams7
 *
 */
public interface AvrTargetPlatformConfiguration {
	/**
	 * Returns whether a platform is supported or not.
	 */
	boolean supportsPlatform(NativePlatformInternal targetPlatform);

	/**
	 * applies a platform specific toolchain configuration
	 */
	void apply(DefaultAvrPlatformToolChain platformToolChain);
}
