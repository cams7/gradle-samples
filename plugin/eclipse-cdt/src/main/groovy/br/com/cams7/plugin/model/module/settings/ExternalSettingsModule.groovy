/**
 * 
 */
package br.com.cams7.plugin.model.module.settings

import br.com.cams7.plugin.model.module.AbstractCProjectModule

/**
 * @author cams7
 *
 */
class ExternalSettingsModule extends AbstractCProjectModule {

	private final String ATTR_ORG_ECLIPSE_CDT_CORE_EXTERNALSETTINGS = "org.eclipse.cdt.core.externalSettings"

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ATTR_ORG_ECLIPSE_CDT_CORE_EXTERNALSETTINGS;
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#appendNode(groovy.util.Node)
	 */
	@Override
	public void appendNode(Node node) {
		node.appendNode(TAG_STORAGEMODULE, [moduleId: getModuleId()])
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		//		ExternalSettingsModule module = (ExternalSettingsModule) o

		return true
	}


	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "ExternalSettingsModule{}";
	}
}
