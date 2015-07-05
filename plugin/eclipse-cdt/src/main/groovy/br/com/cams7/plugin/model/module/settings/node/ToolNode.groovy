/**
 * 
 */
package br.com.cams7.plugin.model.module.settings.node

/**
 * @author cams7
 *
 */
class ToolNode  {
	String id = ""
	String name = ""
	String superClass = ""

	def ToolNode(String id, String name, String superClass) {
		setId(id)
		setName(name)
		setSuperClass(superClass)
	}

	List<OptionNode> options = []
	InputTypeNode inputType = null
}
