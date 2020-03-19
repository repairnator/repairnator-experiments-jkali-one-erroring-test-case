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

package com.github.mjeanroy.restassert.unit.api.http.core.headers.headerequalto;

import static com.github.mjeanroy.restassert.test.commons.DateTestUtils.fromInternetMessageFormat;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.EXPIRES;

import java.util.Date;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.unit.api.http.HttpAssert;

public class AssertIsExpiresEqualToWithDateTest extends AbstractCoreHttpHeaderEqualToTest {

	private static final String VALUE = EXPIRES.getValue();
	private static final String FAILED_VALUE = "Wed, 15 Nov 1995 12:45:26 GMT";

	@Override
	protected Header getHeader() {
		return EXPIRES;
	}

	@Override
	protected void invoke(HttpResponse actual) {
		Date date = fromInternetMessageFormat(VALUE);
		HttpAssert.assertIsExpiresEqualTo(actual, date);
	}

	@Override
	protected void invoke(String message, HttpResponse actual) {
		Date date = fromInternetMessageFormat(VALUE);
		HttpAssert.assertIsExpiresEqualTo(message, actual, date);
	}

	@Override
	protected String failValue() {
		return FAILED_VALUE;
	}
}
