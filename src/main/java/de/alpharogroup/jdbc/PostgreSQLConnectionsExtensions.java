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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The class {@link PostgreSQLConnectionsExtensions} have convenience methods to create and connect
 * to postgresql databases
 *
 * @author Asterios Raptis
 */
@UtilityClass
public final class PostgreSQLConnectionsExtensions
{

	/** PostgreSQL-database constants. */
	/** Constant for the drivername from PostgreSQL-database. */
	public static final String DRIVERNAME = "org.postgresql.Driver";

	/** Constant for the urlprefix from PostgreSQL-database. */
	public static final String URL_PREFIX = "jdbc:postgresql://";

	/** Constant for the port where the PostgreSQL-database listen. */
	public static final int POSTGRESQL_PORT = 5432;

	/** Constant for the default user from PostgreSQL-database. */
	public static final String POSTGRESQL_DEFAULT_USER = "postgres";

	public static final String APP_DB_HOST = "app.db-host";
	public static final String APP_DB_PORT = "app.db-port";
	public static final String APP_DB_NAME = "app.db-name";
	public static final String APP_DB_USERNAME = "app.db-username";
	public static final String APP_DB_PASSWORD = "app.db-password";

	/**
	 * Drops the given PostgreSQL database with the given databaseName if it does exist.
	 *
	 * @param hostname
	 *            the hostname
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static void dropDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws SQLException, ClassNotFoundException
	{
		if (existsDatabase(hostname, databaseName, dbuser, dbpasswort))
		{
			try (
				Connection connection = PostgreSQLConnectionsExtensions.getConnection(hostname, "",
					dbuser, dbpasswort);
				Statement stmt = connection.createStatement())
			{


				final StringBuilder sb = new StringBuilder();
				sb.append("DROP DATABASE ");
				sb.append(databaseName);

				stmt.executeUpdate(sb.toString());
			}
		}
	}

	/**
	 * Checks if the given database exists in the Postgresql Database.
	 *
	 * @param hostname
	 *            the hostname
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return true, if successful otherwise false
	 */
	public static boolean existsDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort)
	{
		return existsDatabase(hostname, POSTGRESQL_PORT, databaseName, dbuser, dbpasswort);
	}

	/**
	 * Checks if the given database exists in the Postgresql Database.
	 *
	 * @param hostname
	 *            the hostname
	 * @param port
	 *            the port number
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return true, if successful otherwise false
	 */
	public static boolean existsDatabase(final @NonNull String hostname, final int port,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort)
	{
		try (Connection connection = PostgreSQLConnectionsExtensions.getConnection(hostname, port,
			databaseName, dbuser, dbpasswort))
		{
			return true;
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	/**
	 * Gets the postgre sql connection.
	 *
	 * @param hostname
	 *            the hostname
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return the postgre sql connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws ClassNotFoundException, SQLException
	{
		return getConnection(hostname, POSTGRESQL_PORT, databaseName, dbuser, dbpasswort);
	}

	/**
	 * Gets the postgres sql connection from the given parameters.
	 *
	 * @param hostname
	 *            the hostname
	 * @param portNumber
	 *            the port number
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return the postgres sql connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String hostname, final int portNumber,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws ClassNotFoundException, SQLException
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(URL_PREFIX);
		sb.append(hostname);
		sb.append(":");
		sb.append(portNumber);
		sb.append("/");
		sb.append(databaseName);
		Class.forName(DRIVERNAME);
		return getConnection(sb.toString().trim(), dbuser, dbpasswort);
	}

	/**
	 * Gets the postgres sql connection from the given parameters.
	 *
	 * @param url
	 *            the database url
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return the postgres sql connection
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String url, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws SQLException
	{
		return DriverManager.getConnection(url, dbuser, dbpasswort);
	}

	/**
	 * Gets the postgres sql connection from the given parameters.
	 *
	 * @param jdbcUrlBean
	 *            the bean that encapsulates the database url
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return the postgres sql connection
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull JdbcUrlBean jdbcUrlBean,
		final @NonNull String dbuser, final @NonNull String dbpasswort) throws SQLException
	{
		return DriverManager.getConnection(jdbcUrlBean.buildUrlString(jdbcUrlBean), dbuser,
			dbpasswort);
	}

	/**
	 * Gets the postgres sql connection from the given parameters.
	 *
	 * @param jdbcConnectionInfo
	 *            the bean that encapsulates the database url, the db user and password
	 * @return the postgres sql connection
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull JdbcConnectionInfo jdbcConnectionInfo)
		throws SQLException
	{
		return getConnection(jdbcConnectionInfo.getJdbcUrlBean(), jdbcConnectionInfo.getUser(),
			jdbcConnectionInfo.getPasswort());
	}

	/**
	 * Creates the a PostgreSQL database with the given databaseName if it does not exist
	 *
	 * @param hostname
	 *            the host name
	 * @param databaseName
	 *            the database name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @param characterSet
	 *            the character set
	 * @param collate
	 *            the collate
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 */
	public static CreationState newDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword, final @NonNull String characterSet,
		final @NonNull String collate) throws SQLException, ClassNotFoundException
	{
		return newDatabase(hostname, POSTGRESQL_PORT, databaseName, dbUser, dbPassword,
			characterSet, collate);
	}

	/**
	 * Creates a new PostgreSQL database with the given database name if it does not exist
	 *
	 * @param hostname
	 *            the host name
	 * @param portNumber
	 *            the port number
	 * @param databaseName
	 *            the database name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @param characterSet
	 *            the character set for the new database
	 * @param collate
	 *            the collate for the new database
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static CreationState newDatabase(final @NonNull String hostname, final int portNumber,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword, final @NonNull String characterSet,
		final @NonNull String collate) throws SQLException, ClassNotFoundException
	{
		if (existsDatabase(hostname, portNumber, databaseName, dbUser, dbPassword))
		{
			return CreationState.ALREADY_EXISTS;
		}
		try (
			Connection connection = PostgreSQLConnectionsExtensions.getConnection(hostname,
				portNumber, "", dbUser, dbPassword);
			Statement stmt = connection.createStatement())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE DATABASE ");
			sb.append(databaseName);
			if (characterSet != null && !characterSet.isEmpty())
			{
				sb.append(" DEFAULT CHARACTER SET ");
				sb.append(characterSet);
				if (collate != null && !collate.isEmpty())
				{
					sb.append(" COLLATE ");
					sb.append(collate);
				}
			}
			stmt.executeUpdate(sb.toString());
		}
		return CreationState.CREATED;
	}

	/**
	 * Creates a new PostgreSQL database with the given databaseName if it does not exist
	 *
	 * @param hostname
	 *            the host name
	 * @param portNumber
	 *            the port number
	 * @param databaseName
	 *            the database name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static CreationState newDatabase(final @NonNull String hostname, final int portNumber,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword) throws SQLException, ClassNotFoundException
	{
		return PostgreSQLConnectionsExtensions.newDatabase(hostname, portNumber, databaseName,
			dbUser, dbPassword, "", "");
	}

	/**
	 * Creates a new PostgreSQL database with the given databaseName if it does not exist
	 *
	 * @param hostname
	 *            the host name
	 * @param databaseName
	 *            the database name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static CreationState newDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword) throws SQLException, ClassNotFoundException
	{
		return PostgreSQLConnectionsExtensions.newDatabase(hostname, POSTGRESQL_PORT, databaseName,
			dbUser, dbPassword);
	}

	/**
	 * Creates a new PostgreSQL database with the given databaseName if it does not exist with the
	 * default port, user and password
	 *
	 * @param hostname
	 *            the host name
	 * @param databaseName
	 *            the database name
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static CreationState newDatabase(final @NonNull String hostname,
		final @NonNull String databaseName) throws SQLException, ClassNotFoundException
	{
		return PostgreSQLConnectionsExtensions.newDatabase(hostname, databaseName,
			POSTGRESQL_DEFAULT_USER, POSTGRESQL_DEFAULT_USER);
	}


	/**
	 * Creates a new PostgreSQL database from the given properties.
	 *
	 * @param properties
	 *            the properties that contains all relevant data
	 * @return the {@link CreationState}
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located
	 */
	public static CreationState newDatabase(final @NonNull Properties properties)
		throws SQLException, ClassNotFoundException
	{
		String host = properties.getProperty(APP_DB_HOST);
		int port = Integer.valueOf(properties.getProperty(APP_DB_PORT));
		String dbName = properties.getProperty(APP_DB_NAME);
		String dbUser = properties.getProperty(APP_DB_USERNAME);
		String dbPw = properties.getProperty(APP_DB_PASSWORD);
		return PostgreSQLConnectionsExtensions.newDatabase(host, port, dbName, dbUser, dbPw);
	}

}
