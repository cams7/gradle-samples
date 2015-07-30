/**
 * 
 */
package br.com.cams7.languageType.documentation;

import java.io.File;

import org.gradle.language.base.LanguageSourceSet;

/**
 * @author cams7
 *
 */
public interface DocumentationSourceSet extends LanguageSourceSet {
	public File getOutputDir();

	public void setOutputDir(File file);

	public String getTaskName();

	public void setTaskName(String taskName);

}
