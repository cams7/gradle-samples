/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.tools

import org.gradle.api.GradleException
import org.gradle.internal.text.TreeFormatter
import org.gradle.nativeplatform.toolchain.internal.tools.CommandLineToolSearchResult
import org.gradle.util.TreeVisitor

import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType

/**
 * @author cams7
 *
 */
class AvrMissingTool implements CommandLineToolSearchResult {

	private final AvrToolType type
	private final String exeName
	private final List<File> path

	private AvrMissingTool(AvrToolType type, String exeName, List<File> path) {
		this.type = type
		this.exeName = exeName
		this.path = path
	}

	public void explain(TreeVisitor<? super String> visitor) {
		if (path.isEmpty()) {
			visitor.node(String.format(
					"Could not find %s '%s' in system path.",
					type.getToolName(), exeName))
		} else {
			visitor.node(String.format(
					"Could not find %s '%s'. Searched in",
					type.getToolName(), exeName))
			visitor.startChildren()
			for (File location : path)
				visitor.node(location.toString())

			visitor.endChildren()
		}
	}

	public File getTool() {
		TreeFormatter formatter = new TreeFormatter()
		explain(formatter)
		throw new GradleException(formatter.toString())
	}

	public boolean isAvailable() {
		return false
	}
}
