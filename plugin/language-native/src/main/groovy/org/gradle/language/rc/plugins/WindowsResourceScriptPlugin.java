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
package org.gradle.language.rc.plugins;

import java.util.Map;

import org.gradle.api.Incubating;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.language.base.internal.SourceTransformTaskConfig;
import org.gradle.language.base.internal.registry.LanguageTransformContainer;
import org.gradle.language.base.plugins.ComponentModelBasePlugin;
import org.gradle.language.nativeplatform.internal.DefaultPreprocessingTool;
import org.gradle.language.nativeplatform.internal.NativeLanguageTransform;
import org.gradle.language.rc.WindowsResourceSet;
import org.gradle.language.rc.internal.DefaultWindowsResourceSet;
import org.gradle.language.rc.plugins.internal.WindowsResourcesCompileTaskConfig;
import org.gradle.model.Mutate;
import org.gradle.model.RuleSource;
import org.gradle.nativeplatform.NativeBinarySpec;
import org.gradle.platform.base.BinarySpec;
import org.gradle.platform.base.LanguageType;
import org.gradle.platform.base.LanguageTypeBuilder;

import com.google.common.collect.Maps;

/**
 * Adds core language support for Windows resource script files.
 */
@Incubating
public class WindowsResourceScriptPlugin implements Plugin<Project> {

	public void apply(final Project project) {
		project.getPluginManager().apply(ComponentModelBasePlugin.class);
	}

	static class Rules extends RuleSource {
		@LanguageType
		void registerLanguage(LanguageTypeBuilder<WindowsResourceSet> builder) {
			builder.setLanguageName("rc");
			builder.defaultImplementation(DefaultWindowsResourceSet.class);
		}

		@Mutate
		void registerLanguageTransform(LanguageTransformContainer languages,
				ServiceRegistry serviceRegistry) {
			languages.add(new WindowsResources());
		}
	}

	private static class WindowsResources extends
			NativeLanguageTransform<WindowsResourceSet> {
		public Class<WindowsResourceSet> getSourceSetType() {
			return WindowsResourceSet.class;
		}

		public Map<String, Class<?>> getBinaryTools() {
			Map<String, Class<?>> tools = Maps.newLinkedHashMap();
			tools.put("rcCompiler", DefaultPreprocessingTool.class);
			return tools;
		}

		public SourceTransformTaskConfig getTransformTask() {
			return new WindowsResourcesCompileTaskConfig();
		}

		@Override
		public boolean applyToBinary(BinarySpec binary) {
			return binary instanceof NativeBinarySpec
					&& shouldProcessResources((NativeBinarySpec) binary);
		}

		private boolean shouldProcessResources(NativeBinarySpec binary) {
			return binary.getTargetPlatform().getOperatingSystem().isWindows();
		}
	}
}
