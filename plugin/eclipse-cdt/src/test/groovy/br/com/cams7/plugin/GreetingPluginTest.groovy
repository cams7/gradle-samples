package br.com.cams7.plugin

import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GreetingPluginTest {
	@Test
	public void greeterPluginAddsGreetingTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		project.pluginManager.apply 'br.com.cams7.greeting'

		assertTrue(project.tasks.hello instanceof GreetingTask)
	}
}
