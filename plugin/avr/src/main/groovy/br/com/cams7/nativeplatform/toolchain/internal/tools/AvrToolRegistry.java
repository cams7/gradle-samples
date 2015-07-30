/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.tools;

import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType;

/**
 * @author cams7
 *
 */
public interface AvrToolRegistry {
	AvrCommandLineToolConfigurationInternal getTool(AvrToolType toolType);
}
