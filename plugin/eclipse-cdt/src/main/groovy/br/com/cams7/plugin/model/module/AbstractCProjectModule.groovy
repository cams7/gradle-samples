/**
 * 
 */
package br.com.cams7.plugin.model.module

import groovy.util.Node

/**
 * @author cams7
 *
 */
abstract class AbstractCProjectModule implements CProjectModule {
	protected final ATTR_EMPTY = ""

	protected final String ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE = "cdt.managedbuild.config.gnu.exe.release"
	protected final String ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_CONFIG_GNU_EXE_RELEASE + ".1985296083"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.cpp.compiler.exe.release"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE + ".124940959"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT = "cdt.managedbuild.tool.gnu.cpp.compiler.input"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_INPUT + ".927367087"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.c.compiler.exe.release"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_EXE_RELEASE + ".2078148869"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT = "cdt.managedbuild.tool.gnu.c.compiler.input"
	protected final String ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT_ID = ATTR_CDT_MANAGEDBUILD_TOOL_GNU_C_COMPILER_INPUT + ".273658613"

	//protected final String CDT_MANAGEDBUILD_TOOL_GNU_CPP_COMPILER_EXE_RELEASE = "cdt.managedbuild.tool.gnu.cpp.compiler.exe.release.124940959"
}
