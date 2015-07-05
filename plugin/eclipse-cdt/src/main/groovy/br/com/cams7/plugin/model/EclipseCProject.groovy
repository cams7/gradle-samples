/**
 * 
 */
package br.com.cams7.plugin.model

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.SourceSet
import org.gradle.plugins.ide.api.XmlFileContentMerger
import org.gradle.plugins.ide.eclipse.model.internal.ExportedEntriesUpdater
import org.gradle.plugins.ide.eclipse.model.internal.FileReferenceFactory
import org.gradle.util.ConfigureUtil

import br.com.cams7.plugin.model.internal.CProjectFactory
import br.com.cams7.plugin.model.module.CProjectModule

/**
 * @author cams7
 *
 */
class EclipseCProject {
	/**
	 * The source sets to be added.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	Iterable<SourceSet> sourceSets

	/**
	 * The configurations whose files are to be added as classpath entries.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	Collection<Configuration> plusConfigurations = []

	/**
	 * The configurations whose files are to be excluded from the classpath entries.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	Collection<Configuration> minusConfigurations = []

	/**
	 * A subset of {@link #plusConfigurations} whose files are not to be exported to downstream Eclipse projects.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	Collection<Configuration> noExportConfigurations = []

	/**
	 * The classpath containers to be added.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	Set<String> containers = new LinkedHashSet<String>()

	/**
	 * Further classpath containers to be added.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 *
	 * @param containers the classpath containers to be added
	 */
	//	void containers(String... containers) {
	//		assert containers != null
	//		this.containers.addAll(containers as List)
	//	}

	/**
	 * The default output directory where Eclipse puts compiled classes.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	File defaultOutputDir

	/**
	 * Whether to download and associate source Jars with the dependency Jars. Defaults to true.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	boolean downloadSources = true

	/**
	 * Whether to download and associate Javadoc Jars with the dependency Jars. Defaults to false.
	 * <p>
	 * See {@link EclipseClasspath} for an example.
	 */
	//	boolean downloadJavadoc = false

	/**
	 * Enables advanced configuration like tinkering with the output XML or affecting the way
	 * that the contents of an existing .cproject file is merged with Gradle build information.
	 * The object passed to the whenMerged{} and beforeMerged{} closures is of type {@link CProject}.
	 * <p>
	 * See {@link EclipseProject} for an example.
	 */
	void file(Closure closure) {
		ConfigureUtil.configure(closure, file)
	}

	/**
	 * See {@link #file(Closure)}.
	 */
	XmlFileContentMerger file

	final Project project
	Map<String, File> pathVariables = [:]
	//	boolean projectDependenciesOnly = false
	//
	//	List<File> classFolders

	def EclipseCProject(Project project) {
		this.project = project
	}

	/**
	 * Calculates, resolves and returns dependency entries of this classpath.
	 */
	public List<CProjectModule> resolveDependencies() {
		def modules = new CProjectFactory().createModules(this)
		new ExportedEntriesUpdater().updateExported(modules, this.noExportConfigurations*.name)
		return modules
	}

	public void mergeXmlClasspath(CProject cproject) {
		file.beforeMerged.execute(cproject)
		def modules = resolveDependencies()
		cproject.configure(modules)
		file.whenMerged.execute(cproject)
	}

	public FileReferenceFactory getFileReferenceFactory() {
		def referenceFactory = new FileReferenceFactory()
		pathVariables.each { name, dir -> referenceFactory.addPathVariable(name, dir) }
		return referenceFactory
	}
}
