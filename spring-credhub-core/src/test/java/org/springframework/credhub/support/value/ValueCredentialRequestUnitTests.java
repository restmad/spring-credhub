/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.support.value;

import org.junit.Before;
import org.junit.Test;

import org.springframework.credhub.support.CredentialRequestUnitTestsBase;
import org.springframework.credhub.support.SimpleCredentialName;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.hasJsonPath;

public class ValueCredentialRequestUnitTests extends CredentialRequestUnitTestsBase {

	@Before
	public void setUp() {
		requestBuilder = ValueCredentialRequest.builder()
				.name(new SimpleCredentialName("example", "credential"))
				.overwrite(true)
				.value(new ValueCredential("somevalue"));
	}

	@Test
	public void serializeWithValue() throws Exception {
		String jsonValue = serializeToJson(requestBuilder);

		assertThat(jsonValue,
				allOf(hasJsonPath("$.overwrite", equalTo(true)),
						hasJsonPath("$.name", equalTo("/example/credential")),
						hasJsonPath("$.type", equalTo("value")),
						hasJsonPath("$.value", equalTo("somevalue"))));

		assertNoPermissions(jsonValue);
	}

	@Test
	public void serializeWithStringValue() throws Exception {
		requestBuilder = ValueCredentialRequest.builder()
				.name(new SimpleCredentialName("example", "credential"))
				.overwrite(true)
				.value("somevalue");

		String jsonValue = serializeToJson(requestBuilder);

		assertThat(jsonValue,
				allOf(hasJsonPath("$.overwrite", equalTo(true)),
						hasJsonPath("$.name", equalTo("/example/credential")),
						hasJsonPath("$.type", equalTo("value")),
						hasJsonPath("$.value", equalTo("somevalue"))));

		assertNoPermissions(jsonValue);
	}
}