/**
 * 
 */
package br.com.cams7.plugin.model.module

import groovy.util.Node;
import br.com.cams7.plugin.model.module.settings.CdtBuildSystemModule
import br.com.cams7.plugin.model.module.settings.ExternalSettingsModule
/**
 * @author cams7
 *
 */
class SettingsModule extends AbstractCProjectModule {
	public static final String TAG_CCONFIGURATION = "cconfiguration"

	public static final String ATTR_ORG_ECLIPSE_CDT_CORE_SETTINGS = "org.eclipse.cdt.core.settings";
	public static final String ATTR_RELEASE = "Release"

	private final String projectName
	private Node cconfigurationNode

	def SettingsModule(String projectName) {
		assert projectName != null
		this.projectName = projectName
	}

	def SettingsModule(Node node) {
		setCconfiguration(node)
	}

	private void setCconfiguration(Node node){
		Node storageModuleNode = node[TAG_STORAGEMODULE].find { it.@moduleId == getModuleId() }
		cconfigurationNode =  storageModuleNode[TAG_CCONFIGURATION].find{it.@id == ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID}
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ATTR_ORG_ECLIPSE_CDT_CORE_SETTINGS;
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#appendNode(groovy.util.Node)
	 */
	@Override
	public void appendNode(Node node) {
		Node storageModuleNode = node.appendNode(TAG_STORAGEMODULE, [moduleId: getModuleId()])
		Node cconfigurationNode = storageModuleNode.appendNode(TAG_CCONFIGURATION, [id: ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID])

		new br.com.cams7.plugin.model.module.settings.SettingsModule().appendNode(cconfigurationNode)

		CdtBuildSystemModule cdtBuildSystem
		if(projectName!=null)
			cdtBuildSystem = new CdtBuildSystemModule(projectName)
		else
			cdtBuildSystem = new CdtBuildSystemModule(this.cconfigurationNode)

		cdtBuildSystem.appendNode(cconfigurationNode)

		new ExternalSettingsModule().appendNode(cconfigurationNode)
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		//		SettingsModule module = (SettingsModule) o

		return true
	}


	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "SettingsModule{}";
	}
}
