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

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.FieldDefaults;

/**
 * The class {@link JdbcUrlBean} hold data to build a jdbc url and factory methods for create a jdbc
 * url as an {@link String} object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JdbcUrlBean
{

	/** The default builder for the mysql jdbc url. */
	public static final JdbcUrlBean DEFAULT_MYSQL_URL = JdbcUrlBean.builder()
		.protocol("jdbc:mysql://").host("localhost").port(3306).build();

	/** The default builder for the postgresql jdbc url. */
	public static final JdbcUrlBean DEFAULT_POSTGRESQL_URL = JdbcUrlBean.builder()
		.protocol("jdbc:postgresql://").host("localhost").port(5432).build();

	/**
	 * Builds a default mysql jdbc url with the given database.
	 *
	 * @param database
	 *            the database
	 * @return the jdbc url
	 */
	public static String newDefaultMysqlJdbcUrl(final String database)
	{
		return newMysqlJdbcUrl(
			JdbcUrlBean.DEFAULT_MYSQL_URL.toBuilder().database(database).build());
	}

	/**
	 * Builds a default postgres jdbc url with the given database.
	 *
	 * @param database
	 *            the database
	 * @return the jdbc url
	 */
	public static String newDefaultPostgresJdbcUrl(final String database)
	{
		return newPostgresJdbcUrl(
			JdbcUrlBean.DEFAULT_POSTGRESQL_URL.toBuilder().database(database).build());
	}

	/**
	 * Builds a H2 jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newH2JdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getDatabase());
		if (bean.getParameters() != null && !bean.getParameters().isEmpty())
		{
			for (final String parameter : bean.getParameters())
			{
				sb.append(";").append(parameter);
			}
		}
		return sb.toString();
	}

	/**
	 * Builds a mysql jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newMysqlJdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getHost()).append(":").append(bean.getPort())
			.append("/").append(bean.getDatabase());
		return sb.toString();
	}

	/**
	 * Builds a postgres jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newPostgresJdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getHost()).append(":").append(bean.getPort())
			.append("/").append(bean.getDatabase());
		return sb.toString();
	}

	/** The database. */
	String database;

	/** The host. */
	String host;

	/** The parameters. */
	@Singular
	List<String> parameters;

	/** The port. */
	int port;

	/** The protocol. */
	String protocol;

}
