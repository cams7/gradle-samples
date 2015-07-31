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

package br.com.cams7.language.objectivecpp.plugins;

import java.util.Map;

import org.gradle.language.base.internal.SourceTransformTaskConfig;
import org.gradle.model.Mutate;
import org.gradle.model.RuleSource;
import org.gradle.nativeplatform.internal.pch.PreCompiledHeaderTransformContainer;

import br.com.cams7.language.nativeplatform.internal.DefaultPreprocessingTool;
import br.com.cams7.language.nativeplatform.internal.NativeLanguageTransform;
import br.com.cams7.language.nativeplatform.internal.PCHCompileTaskConfig;
import br.com.cams7.language.objectivecpp.ObjectiveCppSourceSet;
import br.com.cams7.language.objectivecpp.tasks.ObjectiveCppPreCompiledHeaderCompile;

import com.google.common.collect.Maps;

/**
 * Adds support for compiling Objective C++ pre-compiled headers.
 */
public class ObjectiveCppLangPCHPlugin extends RuleSource {
	@Mutate
	void registerPreCompiledHeaderTask(
			PreCompiledHeaderTransformContainer pchTransformContainer) {
		pchTransformContainer.add(new ObjectiveCppPCH());
	}

	private static class ObjectiveCppPCH extends
			NativeLanguageTransform<ObjectiveCppSourceSet> {
		public Class<ObjectiveCppSourceSet> getSourceSetType() {
			return ObjectiveCppSourceSet.class;
		}

		public Map<String, Class<?>> getBinaryTools() {
			Map<String, Class<?>> tools = Maps.newLinkedHashMap();
			tools.put("objcppCompiler", DefaultPreprocessingTool.class);
			return tools;
		}

		@Override
		public SourceTransformTaskConfig getTransformTask() {
			return new PCHCompileTaskConfig(this,
					ObjectiveCppPreCompiledHeaderCompile.class);
		}
	}
}