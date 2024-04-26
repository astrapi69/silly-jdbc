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

import java.util.List;

import io.github.astrapi69.jdbc.h2.H2ConnectionsExtensions;
import io.github.astrapi69.jdbc.hsqldb.HyperSQLExtensions;
import io.github.astrapi69.jdbc.mysql.MySqlConnectionsExtensions;
import io.github.astrapi69.jdbc.postgresql.PostgreSQLConnectionsExtensions;
import io.github.astrapi69.jdbc.sqlite.SqliteExtensions;
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
public class JdbcUrlBean implements Cloneable
{

	/** The default builder for the H2 jdbc url. */
	public static final JdbcUrlBean DEFAULT_H2_URL = JdbcUrlBean.builder()
		.protocol(H2ConnectionsExtensions.URL_PREFIX).build();

	/** The default builder for the HyperSQL jdbc url. */
	public static final JdbcUrlBean DEFAULT_HSQLDB_URL = JdbcUrlBean.builder()
		.protocol(HyperSQLExtensions.URL_PREFIX).build();

	/** The default builder for the sqlite jdbc url. */
	public static final JdbcUrlBean DEFAULT_SQLITE_URL = JdbcUrlBean.builder()
		.protocol(SqliteExtensions.URL_PREFIX).build();

	/** The default builder for the mysql jdbc url. */
	public static final JdbcUrlBean DEFAULT_MYSQL_URL = JdbcUrlBean.builder()
		.protocol(MySqlConnectionsExtensions.URL_PREFIX)
		.host(MySqlConnectionsExtensions.DEFAULT_HOST).port(MySqlConnectionsExtensions.MYSQL_PORT)
		.build();

	/** The default builder for the postgresql jdbc url. */
	public static final JdbcUrlBean DEFAULT_POSTGRESQL_URL = JdbcUrlBean.builder()
		.protocol(PostgreSQLConnectionsExtensions.URL_PREFIX)
		.host(PostgreSQLConnectionsExtensions.DEFAULT_HOST)
		.port(PostgreSQLConnectionsExtensions.DEFAULT_POSTGRESQL_PORT).build();

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
	/** The protocol identifier of the database. This is only for some databases like hsqldb */
	String protocolIdentifier;

	/**
	 * Builds a H2 jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newH2JdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(bean.getProtocol()).append(bean.getDatabase());
		if (bean.getParameters() != null && !bean.getParameters().isEmpty())
		{
			for (final String parameter : bean.getParameters())
			{
				stringBuilder.append(";").append(parameter);
			}
		}
		return stringBuilder.toString();
	}

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
	 * Builds a HyperSQL jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newHsqldbJdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(bean.getProtocol()).append(bean.getProtocolIdentifier()).append(":")
			.append(bean.getDatabase());
		if (bean.getParameters() != null && !bean.getParameters().isEmpty())
		{
			for (final String parameter : bean.getParameters())
			{
				stringBuilder.append(";").append(parameter);
			}
		}
		return stringBuilder.toString();
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
		return buildUrlString(bean);
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
		return buildUrlString(bean);
	}

	public static String buildUrlString(JdbcUrlBean bean)
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(bean.getProtocol()).append(bean.getHost()).append(":")
			.append(bean.getPort()).append("/").append(bean.getDatabase());
		return stringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return this.toBuilder().build();
	}

}
