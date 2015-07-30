/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.tools;

import java.util.List;

import org.gradle.api.Action;

import br.com.cams7.nativeplatform.toolchain.AvrCommandLineToolConfiguration;
import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType;

/**
 * @author cams7
 *
 */
public interface AvrCommandLineToolConfigurationInternal extends
		AvrCommandLineToolConfiguration {

	AvrToolType getToolType();

	Action<List<String>> getArgAction();

}
