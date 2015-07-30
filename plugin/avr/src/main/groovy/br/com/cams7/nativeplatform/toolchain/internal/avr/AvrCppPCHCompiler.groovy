/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.internal.Transformers
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppPCHCompileSpec
import org.gradle.nativeplatform.toolchain.internal.gcc.GccCompilerArgsTransformer

/**
 * @author cams7
 *
 */
class AvrCppPCHCompiler extends AvrCompatibleNativeCompiler<CppPCHCompileSpec> {

	public AvrCppPCHCompiler(BuildOperationProcessor buildOperationProcessor, CommandLineToolInvocationWorker commandLineTool, CommandLineToolContext invocationContext, String objectFileExtension, boolean useCommandFile) {
		super(buildOperationProcessor, commandLineTool, invocationContext, new CppPCHCompileArgsTransformer(), Transformers.<CppPCHCompileSpec> noOpTransformer(), objectFileExtension, useCommandFile);
	}

	private static class CppPCHCompileArgsTransformer extends AvrCompilerArgsTransformer<CppPCHCompileSpec> {
		protected String getLanguage() {
			return "c++-header";
		}
	}
}
