/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr.version;

import org.gradle.nativeplatform.platform.internal.ArchitectureInternal;
import org.gradle.platform.base.internal.toolchain.ToolSearchResult;
import org.gradle.util.VersionNumber;

/**
 * @author cams7
 *
 */
public interface AvrVersionResult extends ToolSearchResult {

	public ArchitectureInternal getDefaultArchitecture();

	public VersionNumber getVersion();

}
