/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal;

import org.gradle.util.GUtil;

/**
 * @author cams7
 *
 */
public enum AvrToolType {
	AVR_C_COMPILER("AVR C compiler"), AVR_CPP_COMPILER("AVR C++ compiler"), AVR_LIB_ARCHIVER(
			"AVR library archiver");

	private final String toolName;

	AvrToolType(String toolName) {
		this.toolName = toolName;
	}

	public String getToolName() {
		return toolName;
	}

	@Override
	public String toString() {
		return GUtil.toLowerCamelCase(name());
	}
}
