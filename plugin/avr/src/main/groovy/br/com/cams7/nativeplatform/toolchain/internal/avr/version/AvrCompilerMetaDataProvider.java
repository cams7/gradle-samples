/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr.version;

import java.io.File;
import java.util.List;

/**
 * @author cams7
 *
 */
public interface AvrCompilerMetaDataProvider {
	public AvrVersionResult getAvrMetaData(File avrBinary,
			List<String> additionalArgs);
}
