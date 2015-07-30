/**
 * 
 */
package br.com.cams7.componentType

import org.gradle.api.Task
import org.gradle.model.RuleSource
import org.gradle.model.collection.CollectionBuilder
import org.gradle.platform.base.BinaryTasks
import org.gradle.platform.base.BinaryType
import org.gradle.platform.base.BinaryTypeBuilder
import org.gradle.platform.base.ComponentBinaries
import org.gradle.platform.base.ComponentType
import org.gradle.platform.base.ComponentTypeBuilder

import br.com.cams7.componentType.binary.DefaultImageBinary
import br.com.cams7.componentType.binary.ImageBinary
import br.com.cams7.componentType.component.DefaultImageComponent
import br.com.cams7.componentType.component.ImageComponent

/**
 * @author cams7
 *
 */
class MyImageRenderingPlugin extends RuleSource {
	@ComponentType
	void register(ComponentTypeBuilder<ImageComponent> builder) {
		builder.defaultImplementation(DefaultImageComponent)
	}

	@BinaryType
	void register(BinaryTypeBuilder<ImageBinary> builder) {
		builder.defaultImplementation(DefaultImageBinary)
	}

	@ComponentBinaries
	void createBinariesForBinaryComponent(CollectionBuilder<ImageBinary> binaries, ImageComponent library) {
		library.sizes.each{ fontSize ->
			binaries.create("${library.title}${fontSize}Binary"){
				it.size = fontSize
				it.title = library.title
			}
		}
	}

	@BinaryTasks
	void createRenderingTasks(CollectionBuilder<Task> tasks, ImageBinary binary) {
		tasks.create("render${binary.title}${binary.size}Task", RenderSvg){
			it.content = binary.title
			it.fontSize = binary.size
			it.outputFile = new File(it.project.buildDir, "renderedSvg/${binary.title}_${binary.size}.svg")
		}
	}
}
