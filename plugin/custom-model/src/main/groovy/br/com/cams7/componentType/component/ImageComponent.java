/**
 * 
 */
package br.com.cams7.componentType.component;

import java.util.List;

import org.gradle.platform.base.LibrarySpec;

/**
 * @author cams7
 *
 */
public interface ImageComponent extends LibrarySpec {
	public String getTitle();

	public List<String> getSizes();
}
