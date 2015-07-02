package br.com.cams7.plugin

import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GreetingTaskTest {
	@Test
	public void canAddTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		def task = project.task('greeting', type: GreetingTask)
		assertTrue(task instanceof GreetingTask)
	}
}
