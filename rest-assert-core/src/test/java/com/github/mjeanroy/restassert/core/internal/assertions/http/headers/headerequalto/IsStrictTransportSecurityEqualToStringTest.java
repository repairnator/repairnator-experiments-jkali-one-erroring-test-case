/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.STRICT_TRANSPORT_SECURITY;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

public class IsStrictTransportSecurityEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = STRICT_TRANSPORT_SECURITY;
	private static final String NAME = HEADER.getName();
	private static final String VALUE = HEADER.getValue();

	@Override
	protected Header getHeader() {
		return STRICT_TRANSPORT_SECURITY;
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.isStrictTransportSecurityEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}

	@Test
	public void it_should_compare_header_with_case_insensitive_directive_name() {
		// GIVEN
		final String actual = "MAX-AGE=0";
		final String expected = "max-age=0";
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		final AssertionResult result = assertions.isStrictTransportSecurityEqualTo(response, expected);

		// THEN
		checkSuccess(result);
	}

	@Test
	public void it_should_compare_header_with_quoted_directive_value() {
		// GIVEN
		final String actual = "max-age=\"0\"";
		final String expected = "max-age=0";
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		final AssertionResult result = assertions.isStrictTransportSecurityEqualTo(response, expected);

		// THEN
		checkSuccess(result);
	}
}
