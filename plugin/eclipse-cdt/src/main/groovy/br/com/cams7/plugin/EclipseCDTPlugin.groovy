/**
 * 
 */
package br.com.cams7.plugin

import javax.inject.Inject

import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.language.cpp.plugins.CppLangPlugin.Cpp;
import org.gradle.language.cpp.plugins.CppPlugin
import org.gradle.language.cpp.tasks.CppCompile;
import org.gradle.nativeplatform.NativeExecutableSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppCompileSpec;
import org.gradle.plugins.ide.api.XmlFileContentMerger
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.eclipse.GenerateEclipseProject
import org.gradle.plugins.ide.eclipse.model.BuildCommand
import org.gradle.plugins.ide.eclipse.model.EclipseModel
import org.gradle.plugins.ide.eclipse.model.EclipseProject
import org.gradle.plugins.ide.internal.IdePlugin
import org.gradle.util.ConfigureUtil

import br.com.cams7.plugin.model.EclipseCProject

/**
 * @author cams7
 *
 */
class EclipseCDTPlugin extends IdePlugin {
	static final String ECLIPSE_TASK_NAME = "eclipseCDT"
	static final String ECLIPSE_CPROJECT_TASK_NAME = "eclipseCProject"

	private final Instantiator instantiator

	@Inject
	public EclipseCDTPlugin(Instantiator instantiator) {
		this.instantiator = instantiator
	}

	/* (non-Javadoc)
	 * @see org.gradle.plugins.ide.internal.IdePlugin#getLifecycleTaskName()
	 */
	@Override
	protected String getLifecycleTaskName() {
		return ECLIPSE_TASK_NAME
	}

	@Override
	protected void onApply(Project project) {
		project.pluginManager.apply(EclipsePlugin)
		def model = project.extensions.getByType(EclipseModel)

		lifecycleTask.description = "Generates Eclipse CDT configuration files."
		cleanTask.description = "Cleans Eclipse CDT configuration files."

		def delegatePlugin = project.plugins.getPlugin(EclipsePlugin)
		delegatePlugin.lifecycleTask.dependsOn(lifecycleTask)
		delegatePlugin.cleanTask.dependsOn(cleanTask)

		configureEclipseProject(project)

		configureEclipseCProject(project, model)
	}

	private void configureEclipseProject(Project project) {
		def configureClosure = {
			project.tasks.withType(GenerateEclipseProject) {
				BuildCommand.metaClass.triggers = null

				EclipseProject.metaClass.buildCommand << { String name, String triggers ->
					assert name != null && triggers != null

					def buildCommand = new BuildCommand(name)
					buildCommand.triggers = triggers

					buildCommands << buildCommand
				}

				org.gradle.plugins.ide.eclipse.model.Project.metaClass {
					readBuildCommands = {
						->
						return xml.buildSpec.buildCommand.each { command ->
							def buildCommand = new BuildCommand(command.name.text())
							buildCommand.triggers = command.triggers.text()

							buildCommands << buildCommand
						}
					}

					addBuildSpecToXml = {
						->
						def buildSpec = xml.appendNode('buildSpec')
						buildCommands.each { command ->
							def commandNode = buildSpec.appendNode('buildCommand')
							commandNode.appendNode('name', command.name)
							commandNode.appendNode('triggers', command.triggers)
							commandNode.appendNode('arguments')
						}
					}
				}

				projectModel.buildCommand 'org.eclipse.cdt.managedbuilder.core.genmakebuilder', 'clean,full,incremental,'
				projectModel.buildCommand 'org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder', 'full,incremental,'

				projectModel.natures 'org.eclipse.cdt.core.cnature'
				projectModel.natures 'org.eclipse.cdt.core.ccnature'
				projectModel.natures 'org.eclipse.cdt.managedbuilder.core.managedBuildNature'
				projectModel.natures 'org.eclipse.cdt.managedbuilder.core.ScannerConfigNature'
			}
		}
		project.plugins.withType(CppPlugin, configureClosure)
	}

	private void configureEclipseCProject(Project project, EclipseModel model) {
		project.plugins.withType(CppPlugin) {
			EclipseModel.metaClass {
				cproject = null

				cproject << { Closure closure ->
					ConfigureUtil.configure(closure, cproject)
				}
			}

			model.cproject = instantiator.newInstance(EclipseCProject, project)

			final String CPROJECT_FILE = ".cproject"

			maybeAddTask(project, this, ECLIPSE_CPROJECT_TASK_NAME, GenerateEclipseCProject) { task ->
				//task properties:
				description = "Generates the Eclipse cproject file."
				inputFile = project.file(CPROJECT_FILE)
				outputFile = project.file(CPROJECT_FILE)
				//model properties:
				cproject = model.cproject
				cproject.file = new XmlFileContentMerger(xmlTransformer)
			}
		}
	}

	private void maybeAddTask(Project project, IdePlugin plugin, String taskName, Class taskType, Closure action) {
		if (project.tasks.findByName(taskName))
			return

		def task = project.tasks.create(taskName, taskType)
		project.configure(task, action)
		plugin.addWorker(task)
	}
}
