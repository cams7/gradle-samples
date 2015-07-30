/**
 * 
 */
package br.com.cams7.componentType

import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import br.com.cams7.componentType.binary.ImageBinary
import br.com.cams7.componentType.component.ImageComponent

/**
 * @author cams7
 *
 */
class MyImageRenderingPluginTest {

	@Test
	public void pluginAddsTaskToProject() {
		Project project = ProjectBuilder.builder().build()
		project.pluginManager.apply 'org.samples.componentType'
				
//		project.components = {
//			imageA(ImageComponent) {
//				title = "TitleA"
//				sizes = ["14px", "28px", "40px"]
//			}
//		}
		
		println "components: " + project.components

		//assertTrue(project.binaries.TitleA14pxBinary instanceof ImageBinary)
	}
}
