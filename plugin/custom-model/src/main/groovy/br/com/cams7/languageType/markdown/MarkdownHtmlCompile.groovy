/**
 * 
 */
package br.com.cams7.languageType.markdown

import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.pegdown.PegDownProcessor

/**
 * @author cams7
 *
 */
class MarkdownHtmlCompile extends SourceTask {
	@OutputDirectory
	File destinationDir

	@TaskAction
	void process() {
		final String ENCODING = "UTF-8"
		PegDownProcessor processor = new PegDownProcessor()

		getSource().each { sourceFile ->
			String markdown = sourceFile.getText(ENCODING)
			String html = processor.markdownToHtml(markdown)
			File outputFile = new File(destinationDir, sourceFile.name.replace(".md", ".html"))
			outputFile.write(html, ENCODING)
		}
		generateIndex()
	}

	private void generateIndex() {
		File indexFile = new File(destinationDir, "index.html")
		indexFile.withWriter { writer ->
			def markup = new groovy.xml.MarkupBuilder(writer)  // the builder
			markup.html{
				h1"Sample Userguide"
				h2"Content"
				ol {
					getSource().each { sourceFile ->
						def chapterTitle = sourceFile.name - ".md"
						li {
							a(href:chapterTitle + ".html", chapterTitle)
						}
					}
				}
			}
		}
	}
}
