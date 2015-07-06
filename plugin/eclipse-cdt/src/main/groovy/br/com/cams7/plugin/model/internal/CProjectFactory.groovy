/**
 * 
 */
package br.com.cams7.plugin.model.internal

import org.apache.tools.ant.TaskContainer;
import org.gradle.api.Task;
import org.gradle.api.component.SoftwareComponentContainer;
import org.gradle.api.file.DirectoryTree
import org.gradle.api.tasks.SourceSet
import org.gradle.plugins.ide.eclipse.model.SourceFolder
import org.gradle.plugins.ide.eclipse.model.internal.SourceFoldersCreator

import br.com.cams7.plugin.model.EclipseCProject
import br.com.cams7.plugin.model.module.CProjectModule
import br.com.cams7.plugin.model.module.CdtBuildSystemModule
import br.com.cams7.plugin.model.module.LanguageSettingsProvidersModule
import br.com.cams7.plugin.model.module.ScannerConfigurationModule
import br.com.cams7.plugin.model.module.SettingsModule

/**
 * @author cams7
 *
 */
class CProjectFactory {

	//private final sourceFoldersCreator = new SourceFoldersCreator()
	//private final IdeDependenciesExtractor dependenciesExtractor = new IdeDependenciesExtractor()
	//private final classFoldersCreator = new ClassFoldersCreator()

	private final CProjectModuleBuilder settingsCreator = new CProjectModuleBuilder() {
		void update(List<CProjectModule> modules, EclipseCProject cproject) {
			
			final sourceFoldersCreator = new SourceFoldersCreator()
			sourceFoldersCreator.metaClass {

				sortSourceSetsAsPerUsualConvention << {Collection<SourceSet> sourceSets ->
					return sourceSets.sort { sourceSet ->
						switch(sourceSet.name) {
							case SourceSet.MAIN_SOURCE_SET_NAME:
								return 0
							case SourceSet.TEST_SOURCE_SET_NAME:
								return 1
							default:
								return 2
						}
					}
				}

				sortSourceDirsAsPerUsualConvention << {Collection<DirectoryTree> sourceDirs ->
					return sourceDirs.sort { sourceDir ->
						if (sourceDir.dir.path.endsWith("java")) { 0 }
						else if (sourceDir.dir.path.endsWith("resources")) { 2 }
						else { 1 }
					}
				}

				projectRelativeFolders << {Iterable<SourceSet> sourceSets, Closure provideRelativePath ->
					def entries = []
					//println "sourceSets: " + sourceSets
					def sortedSourceSets = sortSourceSetsAsPerUsualConvention(sourceSets.collect{it})
					//println "sortedSourceSets: " + sortedSourceSets

					sortedSourceSets.each { SourceSet sourceSet ->
						//println "srcDirTrees: " + sourceSet.allSource.srcDirTrees
						def sortedSourceDirs = sortSourceDirsAsPerUsualConvention(sourceSet.allSource.srcDirTrees)
						//println "sortedSourceDirs: " + sortedSourceDirs

						sortedSourceDirs.each { tree ->
							def dir = tree.dir
//							println "dir: " + dir
							if (dir.isDirectory()) {
								String projectRelativePath = provideRelativePath(dir)
//								println "projectRelativePath: " + projectRelativePath
								def folder = new SourceFolder(projectRelativePath, null)
								folder.dir = dir
								folder.includes = tree.patterns.includes as List
								folder.excludes = tree.patterns.excludes as List
								entries.add(folder)
							}
						}
					}
					entries
				}

				getRegularSourceFolders = {Iterable<SourceSet> sourceSets, Closure provideRelativePath ->
					def sourceFolders = projectRelativeFolders(sourceSets, provideRelativePath)
					return sourceFolders.findAll { !it.path.contains('..') }
				}

				populateForCProject << {List<CProjectModule> _modules, EclipseCProject _cproject ->
					def provideRelativePath = { _cproject.project.relativePath(it)}
					List regulars = getRegularSourceFolders(_cproject.sourceSets, provideRelativePath)

//					println "regulars: " + regulars

					//externals are mapped to linked resources so we just need a name of the resource, without full path
					//				List trimmedExternals = getExternalSourceFolders(_cproject.sourceSets, provideRelativePath).collect { SourceFolder folder ->
					//					folder.trimPath()
					//					folder
					//				}

					//_modules.addAll(regulars)
					//_modules.addAll(trimmedExternals)
				}
			}
			sourceFoldersCreator.populateForCProject(modules, cproject)

			modules << new SettingsModule(cproject.getProject().getName())
		}
	}

	private final CProjectModuleBuilder cdtBuildSystemCreator = new CProjectModuleBuilder() {
		void update(List<CProjectModule> modules, EclipseCProject cproject) {
			modules << new CdtBuildSystemModule(cproject.getProject().getName())
		}
	}

	private final CProjectModuleBuilder scannerConfigurationCreator = new CProjectModuleBuilder() {
		void update(List<CProjectModule> modules, EclipseCProject cproject) {
			modules << new ScannerConfigurationModule()
		}
	}

	private final CProjectModuleBuilder languageSettingsProvidersCreator = new CProjectModuleBuilder() {
		void update(List<CProjectModule> modules, EclipseCProject cproject) {
			modules << new LanguageSettingsProvidersModule()
		}
	}

	//	private final CProjectModuleBuilder containersCreator = new CProjectModuleBuilder() {
	//		void update(List<CProjectModule> entries, EclipseCProject eclipseClasspath) {
	//			eclipseClasspath.containers.each { container ->
	//				Container entry = new Container(container)
	//				entry.exported = true
	//				entries << entry
	//			}
	//		}
	//	}

	//	private final CProjectModuleBuilder projectDependenciesCreator = new CProjectModuleBuilder() {
	//		void update(List<CProjectModule> modules, EclipseCProject cproject) {
	//			modules.addAll(dependenciesExtractor.extractProjectDependencies(cproject.project, cproject.plusConfigurations, cproject.minusConfigurations)
	//					.collect { IdeProjectDependency it -> new ProjectDependencyBuilder().build(it.project, it.declaredConfiguration.name) })
	//		}
	//	}

	//	private final CProjectModuleBuilder librariesCreator = new CProjectModuleBuilder() {
	//		void update(List<CProjectModule> modules, EclipseCProject cproject) {
	//			dependenciesExtractor.extractRepoFileDependencies(
	//					cproject.project.dependencies, cproject.plusConfigurations, cproject.minusConfigurations, cproject.downloadSources, cproject.downloadJavadoc)
	//					.each { IdeExtendedRepoFileDependency it ->
	//						println it
	//						//modules << createLibraryEntry(it.file, it.sourceFile, it.javadocFile, it.declaredConfiguration.name, cproject, it.id)
	//					}
	//
	//			dependenciesExtractor.extractLocalFileDependencies(cproject.plusConfigurations, cproject.minusConfigurations)
	//					.each { IdeLocalFileDependency it ->
	//						println it
	//						//modules << createLibraryEntry(it.file, null, null, it.declaredConfiguration.name, cproject, null)
	//					}
	//		}
	//	}

	List<CProjectModule> createModules(EclipseCProject cproject) {
		def modules = []

		settingsCreator.update(modules, cproject)
		cdtBuildSystemCreator.update(modules, cproject)
		scannerConfigurationCreator.update(modules, cproject)
		languageSettingsProvidersCreator.update(modules, cproject)
		//		sourceFoldersCreator.populateForClasspath(entries, cproject)
		//		containersCreator.update(entries, cproject)
		//
		//		if (cproject.projectDependenciesOnly) {
		//			projectDependenciesCreator.update(entries, cproject)
		//		} else {
		//			projectDependenciesCreator.update(entries, cproject)
		//			librariesCreator.update(entries, cproject)
		//			entries.addAll(classFoldersCreator.create(cproject))
		//		}
		return modules
	}

}

interface CProjectModuleBuilder {
	void update(List<CProjectModule> modules, EclipseCProject eclipseCProject)
}
