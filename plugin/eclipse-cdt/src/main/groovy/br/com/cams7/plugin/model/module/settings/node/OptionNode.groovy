/**
 * 
 */
package br.com.cams7.plugin.model.module.settings.node

import com.google.common.collect.Sets.SetView;

import bsh.This;

/**
 * @author cams7
 *
 */
class OptionNode  {
	String id = ""
	String name = ""
	String superClass = ""

	def OptionNode(String id, String name, String superClass) {
		setId(id)
		setName(name)
		setSuperClass(superClass)
	}

	String valueType = ""
	String value = ""
	String defaultValue = ""

	def OptionNode(String id, String name, String superClass, String valueType) {
		this(id, name, superClass)

		setValueType(valueType)
	}
}
