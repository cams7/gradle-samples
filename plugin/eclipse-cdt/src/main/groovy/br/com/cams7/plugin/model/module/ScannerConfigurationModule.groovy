/**
 * 
 */
package br.com.cams7.plugin.model.module


/**
 * @author cams7
 *
 */
class ScannerConfigurationModule extends AbstractCProjectModule {

	public static final String ATTR_SCANNERCONFIGURATION = "scannerConfiguration";

	private final String TAG_AUTODISCOVERY = "autodiscovery"

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.CProjectModule#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ATTR_SCANNERCONFIGURATION;
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.CProjectModule#appendNode(groovy.util.Node)
	 */
	@Override
	public void appendNode(Node node) {
		def storageModuleNode = node.appendNode(TAG_STORAGEMODULE, [moduleId: getModuleId()])
		addAutodiscoveryNode(storageModuleNode)
		addScannerConfigBuildInfoNode(storageModuleNode, ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID + ";" + ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID + ".;" + ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE_ID + ";" + ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT_ID)
		addScannerConfigBuildInfoNode(storageModuleNode, ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID + ";" + ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID + ".;" + ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE_ID + ";" + ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT_ID)
	}

	private void addAutodiscoveryNode(Node node) {
		node.appendNode(TAG_AUTODISCOVERY, [enabled : true, problemReportingEnabled : true, selectedProfileId : ATTR_EMPTY])
	}

	private void addScannerConfigBuildInfoNode(Node node, String instanceId) {
		def scannerConfigBuildInfoNode = node.appendNode('scannerConfigBuildInfo', ['instanceId' : instanceId])
		addAutodiscoveryNode(scannerConfigBuildInfoNode)
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		//		ScannerConfigurationModule module = (ScannerConfigurationModule) o

		return true
	}


	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "ScannerConfigurationModule{}";
	}
}
