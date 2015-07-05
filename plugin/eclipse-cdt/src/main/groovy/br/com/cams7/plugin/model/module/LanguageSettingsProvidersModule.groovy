/**
 * 
 */
package br.com.cams7.plugin.model.module


/**
 * @author cams7
 *
 */
class LanguageSettingsProvidersModule extends AbstractCProjectModule {

	public static final String ATTR_ORG_ECLIPSE_CDT_CORE_LANGUAGESETTINGSPROVIDERS = "org.eclipse.cdt.core.LanguageSettingsProviders";

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.CProjectEntry#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ATTR_ORG_ECLIPSE_CDT_CORE_LANGUAGESETTINGSPROVIDERS;
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.CProjectEntry#appendNode(groovy.util.Node)
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

		//		LanguageSettingsProvidersModule module = (LanguageSettingsProvidersModule) o

		return true
	}


	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "LanguageSettingsProvidersModule{}";
	}
}
