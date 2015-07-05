/**
 * 
 */
package br.com.cams7.plugin.model.module


/**
 * @author cams7
 *
 */
class CdtBuildSystemModule extends AbstractCProjectModule {

	public static final String ATTR_CDTBUILDSYSTEM = "cdtBuildSystem"
	public static final String ATTR_VERSION = "4.0.0"

	private final String ATTR_PROJECT_PROJECTTYPE = "cdt.managedbuild.target.gnu.exe"
	private final String ATTR_PROJECT_ID = "teste1." + ATTR_PROJECT_PROJECTTYPE + ".1407714280"
	private final String ATTR_PROJECT_NAME = "Executable"

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ATTR_CDTBUILDSYSTEM;
	}

	/* (non-Javadoc)
	 * @see br.com.cams7.plugin.model.module.CProjectModule#appendNode(groovy.util.Node)
	 */
	@Override
	public void appendNode(Node node) {
		def storageModuleNode = node.appendNode(TAG_STORAGEMODULE, [moduleId: getModuleId(), version: ATTR_VERSION])
		storageModuleNode.appendNode('project', [id: ATTR_PROJECT_ID, name: ATTR_PROJECT_NAME, projectType: ATTR_PROJECT_PROJECTTYPE])
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		//		CdtBuildSystemModule module = (CdtBuildSystemModule) o

		return true
	}

	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "CdtBuildSystemModule{}";
	}
}
