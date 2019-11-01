/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.jdbc;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

/**
 * The test class for {@link JdbcUrlBean}
 */
public class JdbcUrlBeanTest
{

	@Test
	public void testNewH2JdbcUrl()
	{
		String expected;
		String actual;

		expected = "jdbc:h2:file:~/bundlemanagement;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:h2:")
			.database("file:~/bundlemanagement").parameter("MODE=PostgreSQL")
			.parameter("DB_CLOSE_ON_EXIT=FALSE").parameter("DB_CLOSE_DELAY=-1").build();
		actual = JdbcUrlBean.newH2JdbcUrl(bean);
		assertEquals(expected, actual);
	}

	@Test
	public void testNewMysqlJdbcUrl()
	{
		String expected;
		String actual;
		expected = "jdbc:mysql://localhost:3306/foo";
		actual = JdbcUrlBean.newDefaultMysqlJdbcUrl("foo");
		assertEquals(expected, actual);

		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:mysql://").host("10.0.101.23")
			.port(5555).database("foo").build();

		expected = "jdbc:mysql://10.0.101.23:5555/foo";
		actual = JdbcUrlBean.newPostgresJdbcUrl(bean);
		assertEquals(expected, actual);
	}

	@Test
	public void testNewPostgresJdbcUrl()
	{
		String expected;
		String actual;
		expected = "jdbc:postgresql://localhost:5432/foo";
		actual = JdbcUrlBean.newDefaultPostgresJdbcUrl("foo");
		assertEquals(expected, actual);

		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:postgresql://")
			.host("10.0.101.23").port(5555).database("foo").build();

		expected = "jdbc:postgresql://10.0.101.23:5555/foo";
		actual = JdbcUrlBean.newPostgresJdbcUrl(bean);
		assertEquals(expected, actual);

	}

}
