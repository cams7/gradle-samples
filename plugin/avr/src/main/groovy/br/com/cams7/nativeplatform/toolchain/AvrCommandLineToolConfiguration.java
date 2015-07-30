/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain;

import org.gradle.api.Incubating;
import org.gradle.nativeplatform.toolchain.GccCommandLineToolConfiguration;

/**
 * An executable tool used for AVR GCC that allows customizing the executable.
 * 
 * @author cams7
 *
 */
@Incubating
public interface AvrCommandLineToolConfiguration extends
		GccCommandLineToolConfiguration {

}
