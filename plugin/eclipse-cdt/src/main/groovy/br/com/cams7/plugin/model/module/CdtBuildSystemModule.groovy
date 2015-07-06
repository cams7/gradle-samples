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

	private final String TAG_PROJECT = "project"

	private final String ATTR_PROJECT_NAME = "Executable"
	private final String ATTR_PROJECT_PROJECTTYPE = "cdt.managedbuild.target.gnu.exe"

	private final String projectName

	def CdtBuildSystemModule(String projectName) {
		assert projectName != null
		this.projectName = projectName
	}

	def CdtBuildSystemModule(Node node) {
		projectName = getProjectName(node)
	}

	private String getProjectName(Node node) {
		Node storageModuleNode = node[TAG_STORAGEMODULE].find { it.@moduleId == getModuleId() }
		Node projectModule =  storageModuleNode[TAG_PROJECT].find { it.@projectType == ATTR_PROJECT_PROJECTTYPE }
		String projectId  =  projectModule.@id
		String projectName =  projectId.substring(0, projectId.indexOf("."))
		return projectName
	}

	private String getProjectId(){
		final String ATTR_PROJECT_ID = projectName + "." + ATTR_PROJECT_PROJECTTYPE + ".1407714280"
		return ATTR_PROJECT_ID
	}

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
		Node storageModuleNode = node.appendNode(TAG_STORAGEMODULE, [moduleId: getModuleId(), version: ATTR_VERSION])
		storageModuleNode.appendNode(TAG_PROJECT, [id: projectId, name: ATTR_PROJECT_NAME, projectType: ATTR_PROJECT_PROJECTTYPE])
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		CdtBuildSystemModule module = (CdtBuildSystemModule) o

		if(projectName != module.projectName)
			return false

		return true
	}

	//	@Override
	//	public int hashCode() {
	//		return super.hashCode();
	//	}

	@Override
	public String toString() {
		return "CdtBuildSystemModule{projectName = '" + projectName + "'}";
	}
}
