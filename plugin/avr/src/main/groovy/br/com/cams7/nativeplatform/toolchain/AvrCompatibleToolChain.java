/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain;

import java.io.File;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.nativeplatform.toolchain.NativeToolChain;

/**
 * A ToolChain that can handle additional platforms simply by configuring the
 * NativeBinary.
 * 
 * @author cams7
 *
 */
@Incubating
public interface AvrCompatibleToolChain extends NativeToolChain {

	/**
	 * The paths setting required for executing the tool chain. These are used
	 * to locate tools for this tool chain, and are prepended to the system PATH
	 * when executing these tools.
	 */
	public List<File> getPath();

	/**
	 * Append an entry or entries to the tool chain path.
	 *
	 * @param pathEntries
	 *            The path values to append. These are evaluated as per
	 *            {@link org.gradle.api.Project#files(Object...)}
	 */
	public void path(Object... pathEntries);

	/**
	 * Add support for target platform specified by name.
	 */
	public void target(String platformName);

	/**
	 * Add configuration for a target platform specified by name with additional
	 * configuration action.
	 */
	public void target(String platformName,
			Action<? super AvrPlatformToolChain> action);

	/**
	 * Adds an action that can fine-tune the tool configuration for each
	 * platform supported by this tool chain.
	 */
	public void eachPlatform(Action<? super AvrPlatformToolChain> action);

}
