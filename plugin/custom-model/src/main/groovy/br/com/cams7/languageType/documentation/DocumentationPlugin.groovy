/**
 * 
 */
package br.com.cams7.languageType.documentation

import org.gradle.api.Action
import org.gradle.api.Incubating;
import org.gradle.api.Task
import org.gradle.api.tasks.bundling.Zip
import org.gradle.model.Path
import org.gradle.model.RuleSource
import org.gradle.model.collection.CollectionBuilder
import org.gradle.platform.base.BinaryTasks
import org.gradle.platform.base.BinaryType
import org.gradle.platform.base.BinaryTypeBuilder
import org.gradle.platform.base.ComponentBinaries
import org.gradle.platform.base.ComponentType
import org.gradle.platform.base.ComponentTypeBuilder

/**
 * @author cams7
 *
 */
@Incubating
class DocumentationPlugin extends RuleSource {
	@ComponentType
	void register(ComponentTypeBuilder<DocumentationComponent> builder) {
		builder.defaultImplementation(DefaultDocumentationComponent)
	}

	@BinaryType
	void register(BinaryTypeBuilder<DocumentationBinary> builder) {
		builder.defaultImplementation(DefaultDocumentationBinary)
	}

	@ComponentBinaries
	void createBinariesForBinaryComponent(CollectionBuilder<DocumentationBinary> binaries, DocumentationComponent component) {
		binaries.create("${component.name}Binary")
	}

	@BinaryTasks
	void createZip(CollectionBuilder<Task> tasks, final DocumentationBinary binary, @Path("buildDir") final File buildDir) {
		tasks.create("zip${binary.name.capitalize()}", Zip, new Action<Zip>() {
					@Override
					public void execute(Zip zipBinary) {
						binary.source.withType(DocumentationSourceSet) { source ->
							println "source name: ${source.name}"
							println "source outputDir: ${source.outputDir}"
							println "source taskName: ${source.taskName}"

							//zipBinary.into(source.name) {  from(source.outputDir)  }
							//zipBinary.dependsOn source.taskName
						}
						//zipBinary.setDestinationDir(new File(buildDir, binary.name))
						//zipBinary.setArchiveName(binary.name + ".zip")
					}
				})
	}
}
