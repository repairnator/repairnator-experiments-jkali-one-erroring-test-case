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

package com.github.mjeanroy.restassert.unit.api.cookie.javax;

import com.github.mjeanroy.restassert.tests.builders.javax.JavaxCookieBuilder;

import javax.servlet.http.Cookie;

import static com.github.mjeanroy.restassert.unit.api.cookie.JavaxCookieAssert.assertIsNotSecured;

public class AssertIsNotSecuredTest extends AbstractJavaxCookieTest {

	@Override
	protected void invoke(Cookie actual) {
		assertIsNotSecured(actual);
	}

	@Override
	protected void invoke(String message, Cookie actual) {
		assertIsNotSecured(message, actual);
	}

	@Override
	protected Cookie success() {
		return cookie(false);
	}

	@Override
	protected Cookie failure() {
		return cookie(true);
	}

	@Override
	protected String pattern() {
		return "Expecting cookie not to be secured";
	}

	@Override
	protected Object[] placeholders() {
		return new Object[0];
	}

	private Cookie cookie(boolean secured) {
		return new JavaxCookieBuilder()
			.setSecured(secured)
			.build();
	}
}
