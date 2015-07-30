/**
 * 
 */
package br.com.cams7.languageType.markdown

import org.gradle.language.base.sources.BaseLanguageSourceSet

/**
 * @author cams7
 *
 */
class DefaultMarkdownSourceSet extends BaseLanguageSourceSet implements MarkdownSourceSet {
	File outputDir
	String taskName
}
