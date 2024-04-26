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
package io.github.astrapi69.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.jdbc.hsqldb.HyperSQLExtensions;

/**
 * The test class for {@link JdbcUrlBean}
 */
public class JdbcUrlBeanTest
{

	@Test
	public void testNewHsqldbJdbcUrl()
	{
		String expected;
		String actual;
		JdbcUrlBean jdbcUrlBean;

		expected = "jdbc:hsqldb:mem:accounts;shutdown=true";
		jdbcUrlBean = JdbcUrlBean.builder().protocol(HyperSQLExtensions.URL_PREFIX)
			.protocolIdentifier(HyperSQLExtensions.CATALOG_TYPE_MEMORY).database("accounts")
			.parameter("shutdown=true").build();
		actual = JdbcUrlBean.newHsqldbJdbcUrl(jdbcUrlBean);
		assertEquals(expected, actual);

		expected = "jdbc:hsqldb:file:accounts;shutdown=true";
		jdbcUrlBean = JdbcUrlBean.builder().protocol(HyperSQLExtensions.URL_PREFIX)
			.protocolIdentifier(HyperSQLExtensions.CATALOG_TYPE_FILE).database("accounts")
			.parameter("shutdown=true").build();
		actual = JdbcUrlBean.newHsqldbJdbcUrl(jdbcUrlBean);
		assertEquals(expected, actual);

		expected = "jdbc:hsqldb:res:accounts;shutdown=true";
		jdbcUrlBean = JdbcUrlBean.builder().protocol(HyperSQLExtensions.URL_PREFIX)
			.protocolIdentifier(HyperSQLExtensions.CATALOG_TYPE_RESOURCE).database("accounts")
			.parameter("shutdown=true").build();
		actual = JdbcUrlBean.newHsqldbJdbcUrl(jdbcUrlBean);
		assertEquals(expected, actual);
	}

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

	@Test
	void testNewDefaultMysqlJdbcUrl()
	{
		String expected;
		String actual;

		expected = "jdbc:mysql://localhost:3306/resourcebundles";
		actual = JdbcUrlBean.newDefaultMysqlJdbcUrl("resourcebundles");
		assertEquals(expected, actual);
	}

	@Test
	void testNewDefaultPostgresJdbcUrl()
	{
		String expected;
		String actual;

		expected = "jdbc:postgresql://localhost:5432/resourcebundles";
		actual = JdbcUrlBean.newDefaultPostgresJdbcUrl("resourcebundles");
		assertEquals(expected, actual);
	}
}
