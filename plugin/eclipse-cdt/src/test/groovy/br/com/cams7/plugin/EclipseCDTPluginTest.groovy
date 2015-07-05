/**
 * 
 */
package br.com.cams7.plugin

import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * @author cams7
 *
 */
class EclipseCDTPluginTest {
	static final String PLUGIN = 'br.com.cams7.eclipse-cdt'

	@Test
	public void pluginAddsEclipseTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		project.pluginManager.apply PLUGIN

		assertTrue(project.tasks.eclipseCDT instanceof Task)
	}

	@Test
	public void pluginAddsCleanEclipseTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		project.pluginManager.apply PLUGIN

		assertTrue(project.tasks.cleanEclipseCDT instanceof Task)
	}

	//	@Test
	//	public void pluginAddsEclipseCProjectTaskToProject() {
	//		Project project = ProjectBuilder.builder().build()
	//		project.pluginManager.apply PLUGIN
	//
	//		assertTrue(project.tasks.eclipseCProject instanceof Task)
	//	}
}
