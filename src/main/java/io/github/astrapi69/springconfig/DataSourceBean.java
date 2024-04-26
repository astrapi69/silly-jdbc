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
package io.github.astrapi69.springconfig;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * The class {@link DataSourceBean} encapsulates the data for a <code>DataSource</code> object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataSourceBean
{

	/** The default H2 builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_H2_BUILDER = DataSourceBean.builder()
		.driverClassName(H2ConnectionsExtensions.DRIVER_NAME)
		.username(H2ConnectionsExtensions.DEFAULT_USER)
		.password(H2ConnectionsExtensions.DEFAULT_PASSWORD).build();

	/** The default HyperSQL builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_HSQLDB_BUILDER = DataSourceBean.builder()
		.driverClassName(HyperSQLExtensions.DRIVER_NAME).username(HyperSQLExtensions.DEFAULT_USER)
		.password(HyperSQLExtensions.DEFAULT_PASSWORD).build();

	/** The default sqlite builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_SQLLITE_BUILDER = DataSourceBean.builder()
		.driverClassName(SqliteExtensions.DRIVER_NAME).username(SqliteExtensions.DEFAULT_USER)
		.password(SqliteExtensions.DEFAULT_PASSWORD).build();

	/** The default PostgreSQL builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_POSTGRESQL_BUILDER = DataSourceBean.builder()
		.driverClassName(PostgreSQLConnectionsExtensions.DRIVER_NAME)
		.username(PostgreSQLConnectionsExtensions.DEFAULT_USER)
		.password(PostgreSQLConnectionsExtensions.DEFAULT_PASSWORD).build();

	/** The default MySQL builder as start point to build a new DataSourceBean. */
	public static final DataSourceBean DEFAULT_MYSQL_BUILDER = DataSourceBean.builder()
		.driverClassName(MySqlConnectionsExtensions.DRIVER_NAME)
		.username(MySqlConnectionsExtensions.DEFAULT_USER)
		.password(MySqlConnectionsExtensions.DEFAULT_PASSWORD).build();

	/** The default H2 {@link DataSource} with the default values */
	public static final DataSource DEFAULT_H2_DATA_SOURCE = DataSourceBean
		.newDataSource(DEFAULT_H2_BUILDER.toBuilder().build());

	/** The default HyperSQL {@link DataSource} with the default values */
	public static final DataSource DEFAULT_HSQLDB_DATA_SOURCE = DataSourceBean
		.newDataSource(DEFAULT_HSQLDB_BUILDER.toBuilder().build());

	/** The default sqlite {@link DataSource} with the default values */
	public static final DataSource DEFAULT_SQLLITE_DATA_SOURCE = DataSourceBean
		.newDataSource(DEFAULT_SQLLITE_BUILDER.toBuilder().build());

	/** The default PostgreSQL {@link DataSource} with the default values */
	public static final DataSource DEFAULT_POSTGRESQL_DATA_SOURCE = DataSourceBean
		.newDataSource(DEFAULT_POSTGRESQL_BUILDER.toBuilder().build());

	/** The default MySQL {@link DataSource} with the default values */
	public static final DataSource DEFAULT_MYSQL_DATA_SOURCE = DataSourceBean
		.newDataSource(DEFAULT_MYSQL_BUILDER.toBuilder().build());

	/** The driver class name. */
	String driverClassName;

	/** The password. */
	String password;

	/** The url. */
	String url;

	/** The username. */
	String username;

	/**
	 * Factory method for create a new {@link DataSource} object from the given
	 * {@link DataSourceBean} object
	 * 
	 * @param dataSourceBean
	 *            the {@link DataSourceBean} object
	 * @return the new created {@link DataSource} object from the given {@link DataSourceBean}
	 *         object
	 */
	public static DataSource newDataSource(final @NonNull DataSourceBean dataSourceBean)
	{
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceBean.getDriverClassName());
		dataSource.setUrl(dataSourceBean.getUrl());
		dataSource.setUsername(dataSourceBean.getUsername());
		dataSource.setPassword(dataSourceBean.getPassword());
		return dataSource;
	}

}
