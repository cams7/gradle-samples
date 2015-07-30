/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.api.Transformer
import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.nativeplatform.toolchain.internal.ArgsTransformer
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.NativeCompileSpec
import org.gradle.nativeplatform.toolchain.internal.NativeCompiler
import org.gradle.nativeplatform.toolchain.internal.OptionsFileArgsWriter

/**
 * @author cams7
 *
 */
abstract class AvrCompatibleNativeCompiler<T extends NativeCompileSpec> extends NativeCompiler<T> {

	AvrCompatibleNativeCompiler(BuildOperationProcessor buildOperationProcessor, CommandLineToolInvocationWorker commandLineTool, CommandLineToolContext invocationContext, 	final ArgsTransformer<T> argsTransformer, Transformer<T, T> specTransformer, String objectFileExtension, boolean useCommandFile) {
		super(buildOperationProcessor, commandLineTool, invocationContext, argsTransformer, specTransformer, objectFileExtension, useCommandFile)
	}

	@Override
	protected List<String> getOutputArgs(T spec, File outputFile) {
		return Arrays.asList("-o", outputFile.getAbsolutePath())
	}

	@Override
	protected void addOptionsFileArgs(List<String> args, File tempDir) {
		OptionsFileArgsWriter writer = new AvrOptionsFileArgsWriter(tempDir)
		// modifies args in place
		writer.execute(args)
	}

	@Override
	protected List<String> getPCHArgs(T spec) {
		List<String> pchArgs = new ArrayList<String>()
		if (spec.getPrefixHeaderFile() != null) {
			pchArgs.add("-include")
			pchArgs.add(spec.getPrefixHeaderFile().getAbsolutePath())
		}
		return pchArgs
	}
}
