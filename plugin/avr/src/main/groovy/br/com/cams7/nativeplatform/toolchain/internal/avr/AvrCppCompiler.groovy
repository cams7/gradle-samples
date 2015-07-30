/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import java.util.List;

import org.gradle.internal.Transformers
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppCompileSpec

/**
 * @author cams7
 *
 */
class AvrCppCompiler extends AvrCompatibleNativeCompiler<CppCompileSpec> {

	public AvrCppCompiler(BuildOperationProcessor buildOperationProcessor, CommandLineToolInvocationWorker commandLineToolInvocationWorker, CommandLineToolContext invocationContext, String objectFileExtension, boolean useCommandFile) {
		super(buildOperationProcessor, commandLineToolInvocationWorker, invocationContext, new CppCompileArgsTransformer(), Transformers.<CppCompileSpec> noOpTransformer(), objectFileExtension, useCommandFile)
	}

	private static class CppCompileArgsTransformer extends AvrCompilerArgsTransformer<CppCompileSpec> {

		@Override
		public List<String> transform(CppCompileSpec spec) {
			List<String> args = super.transform(spec)

			println "Building file: \$<"
			println "Invoking: AVR C++ Compiler"
			println args
			println "Finished building: \$<\n"

			return args
		}

		protected String getLanguage() {
			return "c++";
		}
	}
}
