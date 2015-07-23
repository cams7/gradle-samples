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

package org.gradle.platform.base;

import org.gradle.api.GradleException;

/**
 * Thrown when a component model is declared in an invalid way.
 */
public class InvalidModelException extends GradleException {

	private static final long serialVersionUID = 1L;

	public InvalidModelException(String message) {
		super(message);
	}

	public InvalidModelException(String message, Exception cause) {
		super(message, cause);
	}
}
