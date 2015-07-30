package org.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
	void apply(Project target) {
		target.task('hello', type: GreetingTask)
	}
}
