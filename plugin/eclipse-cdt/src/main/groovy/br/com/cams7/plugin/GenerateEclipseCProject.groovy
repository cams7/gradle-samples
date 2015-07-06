/**
 * 
 */
package br.com.cams7.plugin

import org.gradle.plugins.ide.api.XmlGeneratorTask

import br.com.cams7.plugin.model.CProject
import br.com.cams7.plugin.model.EclipseCProject
/**
 * @author cams7
 *
 *Generates an Eclipse <code>.cproject</code> file. If you want to fine tune the eclipse configuration
 * <p>
 * At this moment nearly all configuration is done via {@link EclipseCProject}.
 */
class GenerateEclipseCProject extends XmlGeneratorTask<CProject> {
	/**
	 * The Eclipse CProject model containing the information required to generate the cproject file.
	 */
	EclipseCProject cproject

	def GenerateEclipseCProject() {
		xmlTransformer.indentation = "\t"
	}

	@Override
	protected CProject create() {
		return new CProject(xmlTransformer, cproject.fileReferenceFactory)
	}

	@Override
	protected void configure(CProject cproject) {
		getCproject().mergeXmlCProject(cproject)
	}

	@Override
	public String toString() {
		return "GenerateEclipseCProject {" +
				"cproject = " + cproject +
				"}"
	}
}
