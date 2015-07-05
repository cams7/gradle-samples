/**
 * 
 */
package br.com.cams7.plugin.model.internal

import org.gradle.api.artifacts.ModuleVersionIdentifier
//import org.gradle.plugins.ide.eclipse.model.*
import org.gradle.plugins.ide.internal.IdeDependenciesExtractor
import org.gradle.plugins.ide.internal.resolver.model.IdeLocalFileDependency
import org.gradle.plugins.ide.internal.resolver.model.IdeProjectDependency
import org.gradle.plugins.ide.internal.resolver.model.IdeExtendedRepoFileDependency
import br.com.cams7.plugin.model.module.CProjectModule
import br.com.cams7.plugin.model.EclipseCProject
import br.com.cams7.plugin.model.module.LanguageSettingsProvidersModule
import org.gradle.plugins.ide.eclipse.model.internal.ProjectDependencyBuilder
import br.com.cams7.plugin.model.module.ScannerConfigurationModule
import br.com.cams7.plugin.model.module.CdtBuildSystemModule
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
