/**
 * 
 */
package br.com.cams7.plugin.model.module.settings.node

/**
 * @author cams7
 *
 */
class InputTypeNode {
	String id = ""
	String superClass = ""

	def InputTypeNode(String id, String superClass) {
		setId(id)
		setSuperClass(superClass)
	}

	List<AdditionalInputNode> additionalInputs = []
}
