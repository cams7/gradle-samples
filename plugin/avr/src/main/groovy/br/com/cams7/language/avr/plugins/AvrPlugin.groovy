/**
 * 
 */
package br.com.cams7.language.avr.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.service.ServiceRegistry
import org.gradle.nativeplatform.BuildTypeContainer;
import org.gradle.nativeplatform.FlavorContainer;
import org.gradle.nativeplatform.toolchain.internal.NativeToolChainRegistryInternal

/**
 * @author cams7
 *
 */
@Incubating
class AvrPlugin implements Plugin<Project> {

	//	private final Instantiator instantiator
	Project project
	//
	//	@Inject
	//	public AvrPlugin(Instantiator instantiator) {
	//		this.instantiator = instantiator
	//	}

	@Override
	public void apply(Project project) {
		//		if(hasCppOrCPlugin(project)) {
		//			if(project.plugins.hasPlugin(CPlugin))
		//				project.description = "AVR GCC project"
		//
		//			if(project.plugins.hasPlugin(CppPlugin))
		//				project.description = "AVR G++ project"
		//		}
		//
		//		project.getExtensions().create("avrToolChains",
		//			DefaultNativeToolChainRegistry.class, instantiator)

		project.task('printInfo', type: InfoTask)
		setProject(project)
	}

	//	private boolean hasCppOrCPlugin(Project project) {
	//		project.plugins.hasPlugin(CPlugin) || project.plugins.hasPlugin(CppPlugin)
	//	}

	//	static class Rules extends RuleSource {
	//		@Defaults
	//		public static void addToolChain(
	//				NativeToolChainRegistryInternal toolChainRegistry,
	//				ServiceRegistry serviceRegistry) {
	//			final FileResolver fileResolver = serviceRegistry
	//					.get(FileResolver.class)
	//			final ExecActionFactory execActionFactory = serviceRegistry
	//					.get(ExecActionFactory.class)
	//			final Instantiator instantiator = serviceRegistry
	//					.get(Instantiator.class)
	//			final BuildOperationProcessor buildOperationProcessor = serviceRegistry
	//					.get(BuildOperationProcessor.class)
	//			final CompilerMetaDataProviderFactory metaDataProviderFactory = serviceRegistry
	//					.get(CompilerMetaDataProviderFactory.class)
	//
	//			metaDataProviderFactory.metaClass.avr << {ExecActionFactory factory ->
	//				AvrCachingCompilerMetaDataProvider avr = new AvrCachingCompilerMetaDataProvider(
	//						AvrVersionDeterminer.forAvr(factory))
	//				return avr
	//			}
	//
	//			toolChainRegistry.registerFactory(Avr.class, new NamedDomainObjectFactory<Avr>() {
	//						public Avr create(String name) {
	//							return instantiator.newInstance(AvrToolChain.class, instantiator, name, buildOperationProcessor, OperatingSystem.current(), fileResolver, execActionFactory, metaDataProviderFactory)
	//						}
	//					})
	//			toolChainRegistry.registerDefaultToolChain(AvrToolChain.DEFAULT_NAME, Avr.class)
	//		}
	//	}

	private static class InfoTask extends DefaultTask {
		@TaskAction
		def print() {
			println "project: " + getProject()
			println "binaries: " + getProject().binaries
			println "components: " + getProject().components

			def buildTypes = getProject().getExtensions().getByType(BuildTypeContainer)
			println "buildTypes: " + buildTypes
			def flavors = getProject().getExtensions().getByType(FlavorContainer)
			println "flavors: " + flavors
			def toolChains = getProject().getExtensions().getByType(NativeToolChainRegistryInternal)
			println "toolChains: " + toolChains

			println "\nplugins:"
			project.plugins.each {  println it }

			println "\nextensions:"
			project.extensions.each {  println it }
		}
	}
}
