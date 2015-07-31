/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.cams7.language.nativeplatform.tasks;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.Incubating;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.WorkResult;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.internal.Cast;
import org.gradle.internal.operations.logging.BuildOperationLogger;
import org.gradle.internal.operations.logging.BuildOperationLoggerFactory;
import org.gradle.language.base.internal.compile.Compiler;
import org.gradle.language.nativeplatform.internal.incremental.IncrementalCompilerBuilder;
import org.gradle.nativeplatform.internal.BuildOperationLoggingCompilerDecorator;
import org.gradle.nativeplatform.platform.NativePlatform;
import org.gradle.nativeplatform.platform.internal.NativePlatformInternal;
import org.gradle.nativeplatform.toolchain.NativeToolChain;
import org.gradle.nativeplatform.toolchain.internal.NativeCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.NativeToolChainInternal;
import org.gradle.nativeplatform.toolchain.internal.PCHObjectDirectoryGeneratorUtil;
import org.gradle.nativeplatform.toolchain.internal.PlatformToolProvider;

/**
 * Compiles native source files into object files.
 */
@Incubating
public abstract class AbstractNativeCompileTask extends DefaultTask {
	private NativeToolChainInternal toolChain;
	private NativePlatformInternal targetPlatform;
	private boolean positionIndependentCode;
	private File objectFileDir;
	private ConfigurableFileCollection includes;
	private ConfigurableFileCollection source;
	private Map<String, String> macros;
	private List<String> compilerArgs;
	private File prefixHeaderFile;
	private Set<String> preCompiledHeaders;
	private ConfigurableFileCollection preCompiledHeaderInclude;

	public AbstractNativeCompileTask() {
		includes = getProject().files();
		source = getProject().files();
		preCompiledHeaderInclude = getProject().files();
		getInputs().property("outputType", new Callable<String>() {
			@Override
			public String call() throws Exception {
				return NativeToolChainInternal.Identifier.identify(toolChain,
						targetPlatform);
			}
		});
	}

	@Inject
	public IncrementalCompilerBuilder getIncrementalCompilerBuilder() {
		throw new UnsupportedOperationException();
	}

	@Inject
	public BuildOperationLoggerFactory getOperationLoggerFactory() {
		throw new UnsupportedOperationException();
	}

	@TaskAction
	public void compile(IncrementalTaskInputs inputs) {
		BuildOperationLogger operationLogger = getOperationLoggerFactory()
				.newOperationLogger(getName(), getTemporaryDir());
		NativeCompileSpec spec = createCompileSpec();
		spec.setTargetPlatform(targetPlatform);
		spec.setTempDir(getTemporaryDir());
		spec.setObjectFileDir(getObjectFileDir());
		spec.include(getIncludes());
		spec.source(getSource());
		spec.setMacros(getMacros());
		spec.args(getCompilerArgs());
		spec.setPositionIndependentCode(isPositionIndependentCode());
		spec.setIncrementalCompile(inputs.isIncremental());
		spec.setOperationLogger(operationLogger);

		if (!preCompiledHeaderInclude.isEmpty()) {
			File pchDir = PCHObjectDirectoryGeneratorUtil
					.generatePCHObjectDirectory(spec.getTempDir(),
							getPrefixHeaderFile(),
							preCompiledHeaderInclude.getSingleFile());
			spec.setPrefixHeaderFile(new File(pchDir, prefixHeaderFile
					.getName()));
			spec.setPreCompiledHeaderObjectFile(new File(pchDir,
					preCompiledHeaderInclude.getSingleFile().getName()));
			spec.setPreCompiledHeaders(getPreCompiledHeaders());
		}

		PlatformToolProvider platformToolProvider = toolChain
				.select(targetPlatform);
		setDidWork(doCompile(spec, platformToolProvider).getDidWork());
	}

	private <T extends NativeCompileSpec> WorkResult doCompile(T spec,
			PlatformToolProvider platformToolProvider) {
		Class<T> specType = Cast.uncheckedCast(spec.getClass());
		Compiler<T> baseCompiler = platformToolProvider.newCompiler(specType);
		Compiler<T> incrementalCompiler = getIncrementalCompilerBuilder()
				.createIncrementalCompiler(this, baseCompiler, toolChain);
		Compiler<T> loggingCompiler = BuildOperationLoggingCompilerDecorator
				.wrap(incrementalCompiler);
		return loggingCompiler.execute(spec);
	}

	protected abstract NativeCompileSpec createCompileSpec();

	/**
	 * The tool chain used for compilation.
	 */
	public NativeToolChain getToolChain() {
		return toolChain;
	}

	public void setToolChain(NativeToolChain toolChain) {
		this.toolChain = (NativeToolChainInternal) toolChain;
	}

	/**
	 * The platform being targeted.
	 */
	public NativePlatform getTargetPlatform() {
		return targetPlatform;
	}

	public void setTargetPlatform(NativePlatform targetPlatform) {
		this.targetPlatform = (NativePlatformInternal) targetPlatform;
	}

	/**
	 * Should the compiler generate position independent code?
	 */
	@Input
	public boolean isPositionIndependentCode() {
		return positionIndependentCode;
	}

	public void setPositionIndependentCode(boolean positionIndependentCode) {
		this.positionIndependentCode = positionIndependentCode;
	}

	/**
	 * The directory where object files will be generated.
	 */
	@OutputDirectory
	public File getObjectFileDir() {
		return objectFileDir;
	}

	public void setObjectFileDir(File objectFileDir) {
		this.objectFileDir = objectFileDir;
	}

	/**
	 * Returns the header directories to be used for compilation.
	 */
	@InputFiles
	public FileCollection getIncludes() {
		return includes;
	}

	/**
	 * Add directories where the compiler should search for header files.
	 */
	public void includes(Object includeRoots) {
		includes.from(includeRoots);
	}

	/**
	 * Returns the source files to be compiled.
	 */
	@InputFiles
	public FileCollection getSource() {
		return source;
	}

	/**
	 * Adds a set of source files to be compiled. The provided sourceFiles
	 * object is evaluated as per
	 * {@link org.gradle.api.Project#files(Object...)}.
	 */
	public void source(Object sourceFiles) {
		source.from(sourceFiles);
	}

	/**
	 * Macros that should be defined for the compiler.
	 */
	@Input
	public Map<String, String> getMacros() {
		return macros;
	}

	public void setMacros(Map<String, String> macros) {
		this.macros = macros;
	}

	/**
	 * Additional arguments to provide to the compiler.
	 */
	@Input
	public List<String> getCompilerArgs() {
		return compilerArgs;
	}

	public void setCompilerArgs(List<String> compilerArgs) {
		this.compilerArgs = compilerArgs;
	}

	/**
	 * Returns the pre-compiled header file to be used during compilation
	 */
	public File getPrefixHeaderFile() {
		return prefixHeaderFile;
	}

	public void setPrefixHeaderFile(File prefixHeaderFile) {
		this.prefixHeaderFile = prefixHeaderFile;
	}

	/**
	 * Returns the pre-compiled header object file to be used during compilation
	 */
	@InputFiles
	public FileCollection getPreCompiledHeaderInclude() {
		return preCompiledHeaderInclude;
	}

	/**
	 * Set the pre-compiled header the compiler should use.
	 */
	public void preCompiledHeaderInclude(Object preCompiledHeader) {
		preCompiledHeaderInclude.from(preCompiledHeader);
	}

	public Set<String> getPreCompiledHeaders() {
		return preCompiledHeaders;
	}

	public void setPreCompiledHeaders(Set<String> preCompiledHeaders) {
		this.preCompiledHeaders = preCompiledHeaders;
	}
}