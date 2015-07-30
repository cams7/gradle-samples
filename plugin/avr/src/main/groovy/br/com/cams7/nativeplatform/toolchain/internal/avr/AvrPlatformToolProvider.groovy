/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr

import org.gradle.internal.operations.BuildOperationProcessor
import org.gradle.language.base.internal.compile.Compiler
import org.gradle.nativeplatform.internal.StaticLibraryArchiverSpec
import org.gradle.nativeplatform.platform.internal.OperatingSystemInternal
import org.gradle.nativeplatform.toolchain.internal.AbstractPlatformToolProvider
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.DefaultCommandLineToolInvocationWorker
import org.gradle.nativeplatform.toolchain.internal.DefaultMutableCommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.MutableCommandLineToolContext
import org.gradle.nativeplatform.toolchain.internal.OutputCleaningCompiler
import org.gradle.nativeplatform.toolchain.internal.compilespec.CCompileSpec
import org.gradle.nativeplatform.toolchain.internal.compilespec.CPCHCompileSpec
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppCompileSpec
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppPCHCompileSpec
import org.gradle.nativeplatform.toolchain.internal.gcc.ArStaticLibraryArchiver
import org.gradle.nativeplatform.toolchain.internal.tools.ToolSearchPath
import org.gradle.process.internal.ExecActionFactory

import br.com.cams7.nativeplatform.toolchain.internal.AvrToolType
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrCommandLineToolConfigurationInternal
import br.com.cams7.nativeplatform.toolchain.internal.tools.AvrToolRegistry

/**
 * @author cams7
 *
 */
class AvrPlatformToolProvider extends AbstractPlatformToolProvider {

	private final ToolSearchPath toolSearchPath
	private final AvrToolRegistry toolRegistry
	private final ExecActionFactory execActionFactory
	private final boolean useCommandFile

	public AvrPlatformToolProvider(BuildOperationProcessor buildOperationProcessor, OperatingSystemInternal targetOperatingSystem, ToolSearchPath toolSearchPath, AvrToolRegistry toolRegistry, ExecActionFactory execActionFactory, boolean useCommandFile) {
		super(buildOperationProcessor, targetOperatingSystem)

		this.toolSearchPath = toolSearchPath
		this.toolRegistry = toolRegistry
		this.execActionFactory = execActionFactory
		this.useCommandFile = useCommandFile
	}

	@Override
	protected Compiler<CppCompileSpec> createCppCompiler() {
		AvrCommandLineToolConfigurationInternal cppCompilerTool = toolRegistry.getTool(AvrToolType.AVR_CPP_COMPILER)
		AvrCppCompiler cppCompiler = new AvrCppCompiler(buildOperationProcessor, commandLineTool(cppCompilerTool), context(cppCompilerTool), getObjectFileExtension(), useCommandFile)
		return new OutputCleaningCompiler<CppCompileSpec>(cppCompiler, getObjectFileExtension())
	}

	@Override
	protected Compiler<CppPCHCompileSpec> createCppPCHCompiler() {
		AvrCommandLineToolConfigurationInternal cppCompilerTool = toolRegistry.getTool(AvrToolType.AVR_CPP_COMPILER)
		AvrCppPCHCompiler cppPCHCompiler = new AvrCppPCHCompiler(buildOperationProcessor, commandLineTool(cppCompilerTool), context(cppCompilerTool), getPCHFileExtension(), useCommandFile)
		return new OutputCleaningCompiler<CppPCHCompileSpec>(cppPCHCompiler, getPCHFileExtension())
	}

	@Override
	protected Compiler<CCompileSpec> createCCompiler() {
		AvrCommandLineToolConfigurationInternal cCompilerTool = toolRegistry.getTool(AvrToolType.AVR_C_COMPILER)
		AvrCCompiler cCompiler = new AvrCCompiler(buildOperationProcessor, commandLineTool(cCompilerTool), context(cCompilerTool), getObjectFileExtension(), useCommandFile)
		return new OutputCleaningCompiler<CCompileSpec>(cCompiler, getObjectFileExtension())
	}

	@Override
	protected Compiler<CPCHCompileSpec> createCPCHCompiler() {
		AvrCommandLineToolConfigurationInternal cCompilerTool = toolRegistry.getTool(AvrToolType.AVR_C_COMPILER)
		AvrCPCHCompiler cpchCompiler = new AvrCPCHCompiler(buildOperationProcessor, commandLineTool(cCompilerTool), context(cCompilerTool), getPCHFileExtension(), useCommandFile)
		return new OutputCleaningCompiler<CPCHCompileSpec>(cpchCompiler, getPCHFileExtension())
	}

	@Override
	protected Compiler<StaticLibraryArchiverSpec> createStaticLibraryArchiver() {
		AvrCommandLineToolConfigurationInternal staticLibArchiverTool = toolRegistry.getTool(AvrToolType.AVR_LIB_ARCHIVER)
		return new ArStaticLibraryArchiver(buildOperationProcessor, commandLineTool(staticLibArchiverTool), context(staticLibArchiverTool))
	}

	@Override
	public String getPCHFileExtension() {
		return ".h.gch"
	}

	private CommandLineToolInvocationWorker commandLineTool(
			AvrCommandLineToolConfigurationInternal tool) {
		AvrToolType key = tool.getToolType()
		String exeName = tool.getExecutable()
		return new DefaultCommandLineToolInvocationWorker(key.getToolName(),
				toolSearchPath.locate(key, exeName).getTool(),
				execActionFactory)
	}

	private CommandLineToolContext context(
			AvrCommandLineToolConfigurationInternal toolConfiguration) {
		MutableCommandLineToolContext baseInvocation = new DefaultMutableCommandLineToolContext()
		// MinGW requires the path to be set
		baseInvocation.addPath(toolSearchPath.getPath())
		baseInvocation.addEnvironmentVar("CYGWIN", "nodosfilewarning")
		baseInvocation.setArgAction(toolConfiguration.getArgAction())
		return baseInvocation
	}
}
