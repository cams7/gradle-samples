/**
 * 
 */
package br.com.cams7.plugin.model.module.settings

import static br.com.cams7.plugin.model.module.CdtBuildSystemModule.ATTR_CDTBUILDSYSTEM
import static br.com.cams7.plugin.model.module.CdtBuildSystemModule.ATTR_VERSION
import static br.com.cams7.plugin.model.module.SettingsModule.ATTR_ORG_ECLIPSE_CDT_CORE_SETTINGS
import static br.com.cams7.plugin.model.module.SettingsModule.ATTR_RELEASE
import static br.com.cams7.plugin.model.module.SettingsModule.TAG_CCONFIGURATION
import br.com.cams7.plugin.model.module.AbstractCProjectModule
import br.com.cams7.plugin.model.module.settings.node.AdditionalInputNode
import br.com.cams7.plugin.model.module.settings.node.InputTypeNode
import br.com.cams7.plugin.model.module.settings.node.OptionNode
import br.com.cams7.plugin.model.module.settings.node.ToolNode

/**
 * @author cams7
 *
 */
class CdtBuildSystemModule extends AbstractCProjectModule {

	private final String TAG_CONFIGURATION = "configuration"
	private final String TAG_FOLDERINFO = "folderInfo"
	private final String TAG_TOOLCHAIN = "toolChain"
	private final String TAG_TARGETPLATFORM = "targetPlatform"
	private final String TAG_BUILDER = "builder"
	private final String TAG_TOOL = "tool"
	private final String TAG_OPTION = "option"
	private final String TAG_INPUTTYPE = "inputType"
	private final String TAG_ADDITIONALINPUT = "additionalInput"
	private final String TAG_SOURCEENTRIES = "sourceEntries"
	private final String TAG_ENTRY = "entry"

	private final String ATTR_PROJNAME = "\${ProjName}"
	private final String ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE = "org.eclipse.cdt.build.core.buildArtefactType"
	private final String ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE_EXE = ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE + ".exe"
	private final String ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDTYPE = "org.eclipse.cdt.build.core.buildType"
	private final String ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDTYPE_RELEASE = ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDTYPE + ".release"
	private final String ATTR_CLEANCOMMAND = "rm -rf"
	private final String ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE = "cdt.managedbuild.toolchain.gnu.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE + ".2108041945"
	private final String ATTR_LINUXGCC = "Linux GCC"
	private final String ATTR_CDT_MANAGEDBUILD_TARGET_GNU_PLATFORM_EXE_RELEASE = "cdt.managedbuild.target.gnu.platform.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TARGET_GNU_PLATFORM_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TARGET_GNU_PLATFORM_EXE_RELEASE + ".777563348"
	private final String ATTR_DEBUGPLATFORM = "Debug Platform"
	private final String ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE = "cdt.managedbuild.target.gnu.builder.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE + ".1901366259"
	private final String ATTR_GNUMAKEBUILDER = "Gnu Make Builder"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ARCHIVER_BASE = "cdt.managedbuild.tool.gnu.archiver.base"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ARCHIVER_BASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ARCHIVER_BASE + ".1661855754"
	private final String ATTR_GCCARCHIVER = "GCC Archiver"
	private final String ATTR_GCCCPPCOMPILER = "GCC C++ Compiler"
	private final String ATTR_ENUMERATED = "enumerated"
	private final String ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL = "gnu.cpp.compiler.exe.release.option.optimization.level"
	private final String ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL_ID = ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL + ".1983815045"
	private final String ATTR_OPTIMIZATIONLEVEL = "Optimization Level"
	private final String ATTR_GNU_CPP_COMPILER_OPTIMIZATION_LEVEL_MOST = "gnu.cpp.compiler.optimization.level.most"
	private final String ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL = "gnu.cpp.compiler.exe.release.option.debugging.level"
	private final String ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL_ID = ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL + ".1895626073"
	private final String ATTR_DEBUGLEVEL = "Debug Level"
	private final String ATTR_GNU_CPP_COMPILER_DEBUGGING_LEVEL_NONE = "gnu.cpp.compiler.debugging.level.none"
	private final String ATTR_GCCCCOMPILER = "GCC C Compiler"
	private final String ATTR_GNU_C_OPTIMIZATION_LEVEL_MOST = "gnu.c.optimization.level.most"
	private final String ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL = "gnu.c.compiler.exe.release.option.optimization.level"
	private final String ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL_ID = ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL + ".966594895"
	private final String ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL = "gnu.c.compiler.exe.release.option.debugging.level"
	private final String ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL_ID = ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL + ".382745248"
	private final String ATTR_GNU_C_DEBUGGING_LEVEL_NONE = "gnu.c.debugging.level.none"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_LINKER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.c.linker.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_LINKER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_LINKER_EXE_RELEASE + ".1039060989"
	private final String ATTR_GCCCLINKER = "GCC C Linker"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.cpp.linker.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_EXE_RELEASE + ".1676407555"
	private final String ATTR_GCCCPPLINKER = "GCC C++ Linker"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_INPUT = "cdt.managedbuild.tool.gnu.cpp.linker.input"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_INPUT_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_INPUT + ".404959389"
	private final String ATTR_ADDITIONALINPUTDEPENDENCY = "additionalinputdependency"
	private final String ATTR_USER_OBJS = "\$(USER_OBJS)"
	private final String ATTR_ADDITIONALINPUT = "additionalinput"
	private final String ATTR_LIBS = "\$(LIBS)"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.assembler.exe.release"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_EXE_RELEASE + ".1464134583"
	private final String ATTR_GCCASSEMBLER = "GCC Assembler"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_INPUT = "cdt.managedbuild.tool.gnu.assembler.input"
	private final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_INPUT_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_INPUT + ".351339875"
	private final String ATTR_ENTRY_FLAGS = "VALUE_WORKSPACE_PATH|RESOLVED"
	private final String ATTR_ENTRY_KIND = "sourcePath"
	private final String ATTR_ENTRY_NAME = "src"

	private final String projectName

	def CdtBuildSystemModule(String projectName) {
		assert projectName != null
		this.projectName = projectName
	}

	def CdtBuildSystemModule(Node cconfiguration) {
		projectName = getProjectName(cconfiguration)
	}

	private String getProjectName(Node node) {
		Node storageModuleNode = node[TAG_STORAGEMODULE].find { it.@moduleId == getModuleId() }
		Node configurationNode = storageModuleNode[TAG_CONFIGURATION].find { it.@buildArtefactType == ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE_EXE }
		Node folderInfoNode = configurationNode[TAG_FOLDERINFO].find { it.@id == getCdtManagedbuildConfigGnuExeReleaseId()}
		Node toolChainNode = folderInfoNode[TAG_TOOLCHAIN].find { it.@id == ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE_ID }
		Node builderNode = toolChainNode[TAG_BUILDER].find { it.@id == ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE_ID }
		String buildPath  =  builderNode.@buildPath
		String projectName =  buildPath.substring(buildPath.indexOf("/") + 1, buildPath.lastIndexOf("/") - 1)
		return projectName
	}

	private String getBuildPath(){
		final String ATTR_BUILDPATH = "\${workspace_loc:/" + projectName + "}/" + ATTR_RELEASE
		return ATTR_BUILDPATH
	}

	private String getCdtManagedbuildConfigGnuExeReleaseId(){
		return ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID + "."
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

		final String BUILD_PROPERTIES = ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDTYPE + "=" + ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDTYPE_RELEASE + "," + ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE + "=" + ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE_EXE
		Node configurationNode = storageModuleNode.appendNode(TAG_CONFIGURATION, [artifactName: ATTR_PROJNAME, buildArtefactType: ATTR_ORG_ECLIPSE_CDT_BUILD_CORE_BUILDARTEFACTTYPE_EXE, buildProperties: BUILD_PROPERTIES, cleanCommand: ATTR_CLEANCOMMAND, description: ATTR_EMPTY, id: ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID, name: ATTR_RELEASE, parent: ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE])

		Node folderInfoNode = configurationNode.appendNode(TAG_FOLDERINFO, [id: getCdtManagedbuildConfigGnuExeReleaseId(), name: "/", resourcePath: ATTR_EMPTY])
		Node toolChainNode = folderInfoNode.appendNode(TAG_TOOLCHAIN, [id: ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE_ID, name: ATTR_LINUXGCC, superClass: ATTR_CDT_MANAGEDBUILD_TOOLCHAIN_GNU_EXE_RELEASE])
		toolChainNode.appendNode(TAG_TARGETPLATFORM, [id: ATTR_CDT_MANAGEDBUILD_TARGET_GNU_PLATFORM_EXE_RELEASE_ID, name: ATTR_DEBUGPLATFORM, superClass: ATTR_CDT_MANAGEDBUILD_TARGET_GNU_PLATFORM_EXE_RELEASE])
		toolChainNode.appendNode(TAG_BUILDER, [buildPath: getBuildPath(), id: ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE_ID, keepEnvironmentInBuildfile: false, managedBuildOn: true, name: ATTR_GNUMAKEBUILDER, superClass: ATTR_CDT_MANAGEDBUILD_TARGET_GNU_BUILDER_EXE_RELEASE])

		ToolNode tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ARCHIVER_BASE_ID, ATTR_GCCARCHIVER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ARCHIVER_BASE)
		addToolNode(toolChainNode, tool)

		tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE_ID, ATTR_GCCCPPCOMPILER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE)

		OptionNode option = new OptionNode(ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL_ID, ATTR_OPTIMIZATIONLEVEL, ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL, ATTR_ENUMERATED)
		option.setValue(ATTR_GNU_CPP_COMPILER_OPTIMIZATION_LEVEL_MOST)
		tool.getOptions() << option

		option = new OptionNode(ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL_ID, ATTR_DEBUGLEVEL, ATTR_GNU_CPP_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL, ATTR_ENUMERATED)
		option.setValue(ATTR_GNU_CPP_COMPILER_DEBUGGING_LEVEL_NONE)
		tool.getOptions() << option

		InputTypeNode inputType = new InputTypeNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT_ID, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT)
		tool.setInputType(inputType)

		addToolNode(toolChainNode, tool)

		tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE_ID, ATTR_GCCCCOMPILER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE)

		option = new OptionNode(ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL_ID, ATTR_OPTIMIZATIONLEVEL, ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_OPTIMIZATION_LEVEL, ATTR_ENUMERATED)
		option.setDefaultValue(ATTR_GNU_C_OPTIMIZATION_LEVEL_MOST)
		tool.getOptions() << option

		option = new OptionNode(ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL_ID, ATTR_DEBUGLEVEL, ATTR_GNU_C_COMPILER_EXE_RELEASE_OPTION_DEBUGGING_LEVEL, ATTR_ENUMERATED)
		option.setValue(ATTR_GNU_C_DEBUGGING_LEVEL_NONE)
		tool.getOptions() << option

		inputType = new InputTypeNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT_ID, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT)
		tool.setInputType(inputType)

		addToolNode(toolChainNode, tool)

		tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_LINKER_EXE_RELEASE_ID, ATTR_GCCCLINKER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_LINKER_EXE_RELEASE)
		addToolNode(toolChainNode, tool)

		tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_EXE_RELEASE_ID, ATTR_GCCCPPLINKER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_EXE_RELEASE)

		inputType = new InputTypeNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_INPUT_ID, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_LINKER_INPUT)

		inputType.getAdditionalInputs() << new AdditionalInputNode(ATTR_ADDITIONALINPUTDEPENDENCY, ATTR_USER_OBJS)
		inputType.getAdditionalInputs() << new AdditionalInputNode(ATTR_ADDITIONALINPUT, ATTR_LIBS)

		tool.setInputType(inputType)

		addToolNode(toolChainNode, tool)

		tool = new ToolNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_EXE_RELEASE_ID, ATTR_GCCASSEMBLER, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_EXE_RELEASE)

		inputType = new InputTypeNode(ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_INPUT_ID, ATTR_CDT_MANAGEDBUILD_TOOL_GNU_ASSEMBLER_INPUT)
		tool.setInputType(inputType)

		addToolNode(toolChainNode, tool)

		Node sourceEntriesNode = configurationNode.appendNode(TAG_SOURCEENTRIES)
		sourceEntriesNode.appendNode(TAG_ENTRY, [flags: ATTR_ENTRY_FLAGS, kind: ATTR_ENTRY_KIND, name: ATTR_ENTRY_NAME])
	}

	private void addAdditionalInputNode(Node node, AdditionalInputNode additionalInput){
		node.appendNode(TAG_ADDITIONALINPUT, [kind: additionalInput.getKind(), paths: additionalInput.getPaths()])
	}

	private void addInputTypeNode(Node node, InputTypeNode inputType){
		def inputTypeNode = node.appendNode(TAG_INPUTTYPE, [id: inputType.getId(), superClass: inputType.getSuperClass()])
		inputType.getAdditionalInputs().each {AdditionalInputNode additionalInput->
			addAdditionalInputNode(inputTypeNode, additionalInput)
		}
	}

	private void addOptionNode(Node node, OptionNode option){
		node.appendNode(TAG_OPTION, [id: option.getId(), name: option.getName(), superClass: option.getSuperClass(), valueType: option.getValueType(), value: option.getValue(), defaultValue: option.getDefaultValue()])
	}

	private void addToolNode(Node node, ToolNode tool){
		def toolNode = node.appendNode(TAG_TOOL, [id: tool.getId(), name: tool.getName(), superClass: tool.getSuperClass()])

		tool.getOptions().each{OptionNode option ->
			addOptionNode(toolNode, option)
		}

		if(tool.getInputType()!=null)
			addInputTypeNode(toolNode, tool.getInputType())
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
