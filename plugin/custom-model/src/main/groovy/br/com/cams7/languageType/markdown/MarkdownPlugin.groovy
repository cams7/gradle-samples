/**
 * 
 */
package br.com.cams7.languageType.markdown

import org.gradle.api.Incubating;
import org.gradle.model.Defaults
import org.gradle.model.Path
import org.gradle.model.RuleSource
import org.gradle.model.collection.CollectionBuilder
import org.gradle.platform.base.LanguageType
import org.gradle.platform.base.LanguageTypeBuilder

import br.com.cams7.languageType.documentation.DocumentationBinary

/**
 * @author cams7
 *
 */
@Incubating
class MarkdownPlugin extends RuleSource {

	@LanguageType
	void declareMarkdownLanguage(LanguageTypeBuilder<MarkdownSourceSet> builder) {
		builder.setLanguageName("Markdown")
		builder.defaultImplementation(DefaultMarkdownSourceSet)
	}

	@Defaults
	void createMarkdownHtmlCompilerTasks(CollectionBuilder<DocumentationBinary> binaries, @Path("buildDir") File buildDir) {
		println "binaries: $binaries"
		println "buildDir: $buildDir\n"

		binaries.beforeEach { binary ->
			println "binary: $binary\n"
			//println "source: ${binary.source}\n"
			//
			//				source.withType(MarkdownSourceSet.class) { markdownSourceSet ->
			//				 taskName = binary.name + name.capitalize() + "HtmlCompile"
			//				 outputDir = new File(buildDir, "${binary.name}/src/${name}")
			//				 binary.tasks.create(markdownSourceSet.taskName, MarkdownHtmlCompile) {
			//				 source = markdownSourceSet.source
			//				 destinationDir = markdownSourceSet.outputDir
			//				 }
			//				 }
		}
	}
}
