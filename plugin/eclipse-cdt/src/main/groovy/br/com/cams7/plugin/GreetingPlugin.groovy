package br.com.cams7.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin

class GreetingPlugin implements Plugin<Project> {
	void apply(Project target) {
		target.task('hello', type: GreetingTask)
	}
}
