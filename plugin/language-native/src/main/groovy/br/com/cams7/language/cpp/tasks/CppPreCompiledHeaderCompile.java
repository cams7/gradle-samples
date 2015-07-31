/*
 * Copyright 2015 the original author or authors.
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

package br.com.cams7.language.cpp.tasks;

import org.gradle.nativeplatform.toolchain.internal.NativeCompileSpec;

import br.com.cams7.language.cpp.internal.DefaultCppPCHCompileSpec;
import br.com.cams7.language.nativeplatform.tasks.AbstractNativePCHCompileTask;

/**
 * Compiles C++ header source files into object files.
 */
public class CppPreCompiledHeaderCompile extends AbstractNativePCHCompileTask {
	@Override
	protected NativeCompileSpec createCompileSpec() {
		return new DefaultCppPCHCompileSpec();
	}
}