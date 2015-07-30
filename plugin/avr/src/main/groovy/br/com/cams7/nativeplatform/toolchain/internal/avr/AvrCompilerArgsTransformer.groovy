/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.nativeplatform.toolchain.internal.ArgsTransformer
import org.gradle.nativeplatform.toolchain.internal.MacroArgsConverter
import org.gradle.nativeplatform.toolchain.internal.NativeCompileSpec
import org.gradle.nativeplatform.toolchain.internal.compilespec.CCompileSpec
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppCompileSpec

import com.google.common.collect.Lists

/**
 * Maps common options for AVR C/C++ compiling with GCC
 * 
 * @author cams7
 *
 */
abstract class AvrCompilerArgsTransformer<T extends NativeCompileSpec> implements ArgsTransformer<T> {
	public List<String> transform(T spec) {
		List<String> args = Lists.newArrayList()

		if(spec instanceof CCompileSpec || spec instanceof CppCompileSpec)
			addAvrArgs(spec, args)

		addToolSpecificArgs(spec, args)
		addMacroArgs(spec, args)
		addUserArgs(spec, args)
		addIncludeArgs(spec, args)
		return args
	}

	private void addToolSpecificArgs(T spec, List<String> args) {
		Collections.addAll(args, "-x", getLanguage())
		args.add("-c")
		if (spec.isPositionIndependentCode()) {
			if (!spec.getTargetPlatform().getOperatingSystem().isWindows())
				args.add("-fPIC")
		}
	}

	private void addIncludeArgs(T spec, List<String> args) {
		for (File file : spec.getIncludeRoots()) {
			args.add("-I")
			args.add(file.getAbsolutePath())
		}
	}

	private void addMacroArgs(T spec, List<String> args) {
		for (String macroArg : new MacroArgsConverter().transform(spec.getMacros()))
			args.add("-D" + macroArg)
	}

	private void addUserArgs(T spec, List<String> args) {
		args.addAll(spec.getAllArgs())
	}

	private void addAvrArgs(T spec, List<String> args) {
		final String F_CPU = "16000000UL"
		final String OPT = "s"
		final String MCU = "atmega328p"

		args.add("-DF_CPU=" + F_CPU)
		args.add("-Wall")
		args.add("-O" + OPT)
		args.add("-fpack-struct")
		args.add("-fshort-enums")
		args.add("-ffunction-sections")
		args.add("-fdata-sections")
		args.add("-funsigned-char")
		args.add("-funsigned-bitfields")
		args.add("-mmcu=" + MCU)

		if(spec instanceof CCompileSpec)
			args.add("std=gnu99")
		else if(spec instanceof CppCompileSpec)
			args.add("-fno-exceptions")

		args.add("-MMD")
		args.add("-MP")
		args.add("-MF\"" + getDFileName(spec.getObjectFileDir()) + "\"")
		args.add("-MT\"" + getDFileName(spec.getObjectFileDir()) + "\"")
	}

	protected abstract String getLanguage()

	private String getDFileName(File objectFile){
		String fileName = objectFile.name.replaceFirst(".o", ".d")
		return fileName
	}
}
