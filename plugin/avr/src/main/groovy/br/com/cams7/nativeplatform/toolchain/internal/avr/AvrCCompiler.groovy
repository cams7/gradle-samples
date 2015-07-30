/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import java.util.List;

import org.gradle.internal.Transformers
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.compilespec.CCompileSpec

/**
 * @author cams7
 *
 */
class AvrCCompiler extends AvrCompatibleNativeCompiler<CCompileSpec> {

	public AvrCCompiler(BuildOperationProcessor buildOperationProcessor, CommandLineToolInvocationWorker commandLineToolInvocationWorker, CommandLineToolContext invocationContext, String objectFileExtension, boolean useCommandFile) {
		super(buildOperationProcessor, commandLineToolInvocationWorker, invocationContext, new CCompileArgsTransformer(), Transformers.<CCompileSpec> noOpTransformer(), objectFileExtension, useCommandFile)
	}

	private static class CCompileArgsTransformer extends AvrCompilerArgsTransformer<CCompileSpec> {

		@Override
		public List<String> transform(CCompileSpec spec) {
			List<String> args = super.transform(spec)

			println "Building file: \$<"
			println "Invoking: AVR Compiler"
			println args
			println "Finished building: \$<\n"

			return args
		}

		protected String getLanguage() {
			return "c";
		}
	}
}
