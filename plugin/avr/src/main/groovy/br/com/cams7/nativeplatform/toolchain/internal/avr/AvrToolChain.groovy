/**
 *
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.internal.os.OperatingSystem
import org.gradle.internal.reflect.Instantiator
import org.gradle.nativeplatform.toolchain.internal.gcc.version.CompilerMetaDataProviderFactory
import org.gradle.process.internal.ExecActionFactory

import br.com.cams7.nativeplatform.toolchain.Avr
import br.com.cams7.nativeplatform.toolchain.internal.avr.version.AvrVersionResult

/**
 * @author cams7
 *
 */
class AvrToolChain extends AbstractAvrCompatibleToolChain implements Avr {
	public static final String DEFAULT_NAME = "avr";

	public AvrToolChain(Instantiator instantiator, String name, BuildOperationProcessor buildOperationProcessor, OperatingSystem operatingSystem, FileResolver fileResolver, ExecActionFactory execActionFactory, CompilerMetaDataProviderFactory metaDataProviderFactory) {
		super(name, buildOperationProcessor, operatingSystem, fileResolver, execActionFactory, metaDataProviderFactory.avr(execActionFactory), instantiator)
	}

	@Override
	protected String getTypeName() {
		return "Atmel AVR"
	}

	protected void initForImplementation(DefaultAvrPlatformToolChain platformToolChain, AvrVersionResult versionResult) {
		//platformToolChain.setCanUseCommandFile(versionResult.getVersion().getMajor() >= 4);
		platformToolChain.setCanUseCommandFile(true)
	}
}
