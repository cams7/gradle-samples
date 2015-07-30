/**
 * 
 */
package br.com.cams7.componentType

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author cams7
 *
 */
class RenderSvg extends DefaultTask {
	@Input
	String content

	@Input
	String fontSize

	@OutputFile
	File outputFile

	@TaskAction
	void paint(){
		outputFile.write("""
             <svg xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg"
             xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="100%" viewBox="16 8 400 100" preserveAspectRatio="xMidYMid meet" zoomAndPan="disable" onload="">
             <text x="50" y="70" id="e1_texte" style="fill: #000000; font-family: Verdana; font-size: ${fontSize}; stroke: #008000; font-style: normal; font-weight: bold;">$content</text></svg>
             """.stripIndent());
	}
}
