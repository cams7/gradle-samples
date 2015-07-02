/**
 * 
 */
package br.com.cams7.plugin

import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import org.gradle.api.Task

/**
 * @author cams7
 *
 */
class EclipseCDTTaskTest {
	@Test
	public void canAddTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		def task = project.task('eclipse', type: Task)
		assertTrue(task instanceof Task)
	}
}
