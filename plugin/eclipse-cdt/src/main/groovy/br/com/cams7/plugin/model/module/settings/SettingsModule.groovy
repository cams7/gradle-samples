/**
 * 
 */
package br.com.cams7.plugin.model.module.settings

import static br.com.cams7.plugin.model.module.SettingsModule.ATTR_ORG_ECLIPSE_CDT_CORE_SETTINGS
import static br.com.cams7.plugin.model.module.SettingsModule.ATTR_RELEASE
import groovy.util.Node;
import br.com.cams7.plugin.model.module.AbstractCProjectModule

/**
 * @author cams7
 *
 */
class SettingsModule extends AbstractCProjectModule {
	private final String TAG_EXTERNALSETTINGS = "externalSettings"
	private final String TAG_EXTENSIONS = "extensions"
	private final String TAG_EXTENSION = "extension"

	private final String ATTR_ORG_ECLIPSE_CDT_MANAGEDBUILDER_CORE_CONFIGURATIONDATAPROVIDER = "org.eclipse.cdt.managedbuilder.core.configurationDataProvider"

	private final String ATTR_ORG_ECLIPSE_CDT_CORE_GMAKEERRORPARSER = "org.eclipse.cdt.core.GmakeErrorParser"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_CWDLOCATOR = "org.eclipse.cdt.core.CWDLocator"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_GCCERRORPARSER = "org.eclipse.cdt.core.GCCErrorParser"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_GASERRORPARSER = "org.eclipse.cdt.core.GASErrorParser"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_GLDERRORPARSER = "org.eclipse.cdt.core.GLDErrorParser"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER = "org.eclipse.cdt.core.ErrorParser"

	private final String ATTR_ORG_ECLIPSE_CDT_CORE_ELF = "org.eclipse.cdt.core.ELF"
	private final String ATTR_ORG_ECLIPSE_CDT_CORE_BINARYPARSER = "org.eclipse.cdt.core.BinaryParser"

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
		def storageModuleNode = node.appendNode(TAG_STORAGEMODULE, [buildSystemId: ATTR_ORG_ECLIPSE_CDT_MANAGEDBUILDER_CORE_CONFIGURATIONDATAPROVIDER, id: ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID, moduleId: getModuleId(), name: ATTR_RELEASE])
		storageModuleNode.appendNode(TAG_EXTERNALSETTINGS)
		def extensionsNode = storageModuleNode.appendNode(TAG_EXTENSIONS)

		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_GMAKEERRORPARSER, ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER)
		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_CWDLOCATOR, ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER)
		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_GCCERRORPARSER, ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER)
		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_GASERRORPARSER, ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER)
		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_GLDERRORPARSER, ATTR_ORG_ECLIPSE_CDT_CORE_ERRORPARSER)
		addExtensionNode(extensionsNode, ATTR_ORG_ECLIPSE_CDT_CORE_ELF, ATTR_ORG_ECLIPSE_CDT_CORE_BINARYPARSER)
	}

	private void addExtensionNode(Node node, String id, String point) {
		node.appendNode(TAG_EXTENSION, ['id': id, 'point': point])
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
