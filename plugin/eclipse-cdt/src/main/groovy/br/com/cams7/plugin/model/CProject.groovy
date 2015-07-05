/**
 * 
 */
package br.com.cams7.plugin.model

import org.gradle.internal.SystemProperties
import org.gradle.internal.UncheckedException
import org.gradle.internal.xml.XmlTransformer
import org.gradle.plugins.ide.eclipse.model.internal.FileReferenceFactory
import org.gradle.plugins.ide.internal.generator.XmlPersistableConfigurationObject
import org.gradle.util.TextUtil

import br.com.cams7.plugin.model.module.CProjectModule
import br.com.cams7.plugin.model.module.CdtBuildSystemModule
import br.com.cams7.plugin.model.module.LanguageSettingsProvidersModule
import br.com.cams7.plugin.model.module.ScannerConfigurationModule
import br.com.cams7.plugin.model.module.SettingsModule

/**
 * @author cams7
 *
 */
class CProject extends XmlPersistableConfigurationObject {

	private final FileReferenceFactory fileReferenceFactory
	List<CProjectModule> modules = []

	/**
	 * @param xmlTransformer
	 * @param fileReferenceFactory
	 */
	public CProject(XmlTransformer transformer, FileReferenceFactory fileReferenceFactory) {
		super(transformer)
		this.fileReferenceFactory = fileReferenceFactory

		transformer.metaClass.transform = {Node original, OutputStream destination ->
			def provider = doTransform(original)

			provider.metaClass.writeTo = {OutputStream stream ->
				try {
					Writer writer = new OutputStreamWriter(stream, "UTF-8")
					writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")
					writer.write(SystemProperties.getInstance().getLineSeparator())
					writer.write("<?fileVersion 4.0.0?>")

					if (node != null)
						printNode(node, writer)
					else if (element != null)
						printDomNode(element, writer)
					else if (builder != null)
						writer.append(TextUtil.toPlatformLineSeparators(stripXmlDeclaration(builder)))
					else
						writer.append(TextUtil.toPlatformLineSeparators(stripXmlDeclaration(stringValue)))

					writer.flush()
				} catch (IOException e) {
					throw UncheckedException.throwAsUncheckedException(e)
				}
			}

			provider.writeTo(destination)
		}
	}

	public CProject(){
		super(new XmlTransformer())
	}

	/* (non-Javadoc)
	 * @see org.gradle.plugins.ide.internal.generator.AbstractPersistableConfigurationObject#getDefaultResourceName()
	 */
	@Override
	protected String getDefaultResourceName() {
		return "defaultCProject.xml";
	}

	@Override
	protected void load(Node node) {
		node[CProjectModule.TAG_STORAGEMODULE].each { Node moduleNode ->
			CProjectModule module = null

			switch(moduleNode.@moduleId){
				case SettingsModule.ATTR_ORG_ECLIPSE_CDT_CORE_SETTINGS:
					module = new SettingsModule(node)
					break
				case CdtBuildSystemModule.ATTR_CDTBUILDSYSTEM:
					module = new CdtBuildSystemModule(node)
					break
				case ScannerConfigurationModule.ATTR_SCANNERCONFIGURATION:
					module = new ScannerConfigurationModule()
					break
				case LanguageSettingsProvidersModule.ATTR_ORG_ECLIPSE_CDT_CORE_LANGUAGESETTINGSPROVIDERS:
					module = new LanguageSettingsProvidersModule()
					break
				default:
					break
			}

			if (module)
				modules << module
		}
	}

	@Override
	protected void store(Node node) {
		node[CProjectModule.TAG_STORAGEMODULE].each { node.remove(it) }

		modules.each { CProjectModule module ->
			module.appendNode(node)
		}
	}

	public void configure(List newEntries) {
		def entriesToBeKept = modules.findAll { !isDependency(it) }
		modules = (entriesToBeKept + newEntries).unique()
	}

	private boolean isDependency(CProjectModule entry) {
		//entry instanceof ProjectDependency || entry instanceof AbstractLibrary
		return false
	}

	@Override
	boolean equals(o) {
		if (this.is(o))
			return true

		if (getClass() != o.class)
			return false

		CProject cproject = (CProject) o;

		if (modules != cproject.modules)
			return false

		return true
	}

	@Override
	int hashCode() {
		int result;

		result = modules.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "CProject {" +
				"modules = " + modules +
				"}"
	}
}
