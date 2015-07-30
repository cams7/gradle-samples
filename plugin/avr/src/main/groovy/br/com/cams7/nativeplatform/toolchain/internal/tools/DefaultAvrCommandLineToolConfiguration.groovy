/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.tools

import org.gradle.api.Action
import org.gradle.internal.Actions
import org.gradle.nativeplatform.toolchain.internal.tools.CommandLineToolConfigurationInternal

import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType

/**
 * @author cams7
 *
 */
class DefaultAvrCommandLineToolConfiguration implements CommandLineToolConfigurationInternal {

	final AvrToolType toolType
	final List<Action<? super List<String>>> argActions = new ArrayList<Action<? super List<String>>>()

	String executable

	private DefaultAvrCommandLineToolConfiguration(AvrToolType toolType) {
		this.toolType = toolType
	}

	public DefaultAvrCommandLineToolConfiguration(AvrToolType toolType, String executable) {
		this(toolType)
		setExecutable(executable)
	}

	@Override
	public void withArguments(Action<? super List<String>> action) {
		getArgActions().add(action)
	}

	@Override
	public Action<List<String>> getArgAction() {
		return Actions.composite(getArgActions())
	}
}
