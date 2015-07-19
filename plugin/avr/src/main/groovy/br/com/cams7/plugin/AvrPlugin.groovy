/**
 * 
 */
package br.com.cams7.plugin

import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.c.plugins.CLangPCHPlugin
import org.gradle.language.c.plugins.CLangPlugin
import org.gradle.nativeplatform.plugins.NativeComponentPlugin

/**
 * @author cams7
 *
 */
@Incubating
class AvrPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getPluginManager().apply(NativeComponentPlugin.class)
		project.getPluginManager().apply(CLangPlugin.class)
		project.getPluginManager().apply(CLangPCHPlugin.class)
	}
}
