/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.Transformer;
import org.gradle.nativeplatform.toolchain.internal.OptionsFileArgsWriter
import org.gradle.platform.base.internal.toolchain.ArgWriter;

import com.google.common.collect.Lists;

/**
 * Uses an option file for arguments passed to AVR GCC if possible. Certain AVR GCC
 * options do not function correctly when included in an option file, so include
 * these directly on the command line as well.
 * 
 * @author cams7
 *
 */
class AvrOptionsFileArgsWriter extends OptionsFileArgsWriter {

	private static final List<String> CLI_ONLY_ARGS = Arrays.asList("-m32", "-m64")


	public AvrOptionsFileArgsWriter(File tempDir) {
		super(ArgWriter.unixStyleFactory(), tempDir)
	}

	@Override
	public List<String> transformArgs(List<String> originalArgs, File tempDir) {
		List<String> commandLineOnlyArgs = getCommandLineOnlyArgs(originalArgs)
		List<String> finalArgs = Lists.newArrayList()
		finalArgs.addAll(super.transformArgs(originalArgs, tempDir))
		finalArgs.addAll(commandLineOnlyArgs)
		return finalArgs
	}

	private List<String> getCommandLineOnlyArgs(List<String> allArgs) {
		List<String> commandLineOnlyArgs = new ArrayList<String>(allArgs)
		commandLineOnlyArgs.retainAll(CLI_ONLY_ARGS)
		return commandLineOnlyArgs
	}
}
