/**
 * 
 */
package br.com.cams7.componentType.component

import org.gradle.platform.base.component.BaseComponentSpec

/**
 * @author cams7
 *
 */
class DefaultImageComponent extends BaseComponentSpec implements ImageComponent {
	List<String> sizes
	String title
}
