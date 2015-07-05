/**
 * 
 */
package br.com.cams7.plugin.model.module.settings.node

/**
 * @author cams7
 *
 */
class AdditionalInputNode {
	String kind="";
	String paths="";

	def AdditionalInputNode(String kind, String paths) {
		setKind(kind)
		setPaths(paths)
	}
}
