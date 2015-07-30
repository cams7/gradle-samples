/**
 * 
 */
package br.com.cams7.componentType.binary;

import org.gradle.platform.base.BinarySpec;

/**
 * @author cams7
 *
 */
public interface ImageBinary extends BinarySpec {
	public String getTitle();

	public String getSize();
}
