/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.internal.Transformers
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.compilespec.CPCHCompileSpec

/**
 * @author cams7
 *
 */
class AvrCPCHCompiler extends AvrCompatibleNativeCompiler<CPCHCompileSpec> {

	public AvrCPCHCompiler(BuildOperationProcessor buildOperationProcessor, CommandLineToolInvocationWorker commandLineTool, CommandLineToolContext invocationContext, String objectFileExtension, boolean useCommandFile) {
		super(buildOperationProcessor, commandLineTool, invocationContext, new CPCHCompileArgsTransformer(), Transformers.<CPCHCompileSpec> noOpTransformer(), objectFileExtension, useCommandFile)
	}

	private static class CPCHCompileArgsTransformer extends AvrCompilerArgsTransformer<CPCHCompileSpec> {
		protected String getLanguage() {
			return "c-header";
		}
	}
}
