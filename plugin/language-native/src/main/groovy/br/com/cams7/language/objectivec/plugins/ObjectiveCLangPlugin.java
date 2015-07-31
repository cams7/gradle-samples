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
package br.com.cams7.language.objectivec.plugins;

import java.util.Map;

import org.gradle.api.Incubating;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.language.base.internal.SourceTransformTaskConfig;
import org.gradle.language.base.internal.registry.LanguageTransformContainer;
import org.gradle.language.base.plugins.ComponentModelBasePlugin;
import org.gradle.model.Mutate;
import org.gradle.model.RuleSource;
import org.gradle.platform.base.LanguageType;
import org.gradle.platform.base.LanguageTypeBuilder;

import br.com.cams7.language.nativeplatform.internal.CompileTaskConfig;
import br.com.cams7.language.nativeplatform.internal.DefaultPreprocessingTool;
import br.com.cams7.language.nativeplatform.internal.NativeLanguageTransform;
import br.com.cams7.language.objectivec.ObjectiveCSourceSet;
import br.com.cams7.language.objectivec.internal.DefaultObjectiveCSourceSet;
import br.com.cams7.language.objectivec.tasks.ObjectiveCCompile;

import com.google.common.collect.Maps;

/**
 * Adds core Objective-C language support.
 */
@Incubating
public class ObjectiveCLangPlugin implements Plugin<Project> {
	public void apply(final Project project) {
		project.getPluginManager().apply(ComponentModelBasePlugin.class);
	}

	static class Rules extends RuleSource {
		@LanguageType
		void registerLanguage(LanguageTypeBuilder<ObjectiveCSourceSet> builder) {
			builder.setLanguageName("objc");
			builder.defaultImplementation(DefaultObjectiveCSourceSet.class);
		}

		@Mutate
		void registerLanguageTransform(LanguageTransformContainer languages,
				ServiceRegistry serviceRegistry) {
			languages.add(new ObjectiveC());
		}
	}

	private static class ObjectiveC extends
			NativeLanguageTransform<ObjectiveCSourceSet> {
		public Class<ObjectiveCSourceSet> getSourceSetType() {
			return ObjectiveCSourceSet.class;
		}

		public Map<String, Class<?>> getBinaryTools() {
			Map<String, Class<?>> tools = Maps.newLinkedHashMap();
			tools.put("objcCompiler", DefaultPreprocessingTool.class);
			return tools;
		}

		public SourceTransformTaskConfig getTransformTask() {
			return new CompileTaskConfig(this, ObjectiveCCompile.class);
		}
	}
}
