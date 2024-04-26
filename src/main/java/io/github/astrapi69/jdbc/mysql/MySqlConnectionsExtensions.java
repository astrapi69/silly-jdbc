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
package io.github.astrapi69.jdbc.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.astrapi69.jdbc.CreationState;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The class {@link MySqlConnectionsExtensions} have convenience methods to create and connect to
 * mysql databases
 *
 * @author Asterios Raptis
 */
@UtilityClass
public final class MySqlConnectionsExtensions
{

	/** MySQL-database constants. */
	/** Constant for the drivername from MySQL-database. */
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

	/** Constant for the urlprefix from MySQL-database. */
	public static final String URL_PREFIX = "jdbc:mysql://";

	/** Constant for the default port where the MySQL-database listen. */
	public static final int MYSQL_PORT = 3306;

	/** Constant for the default user from MySQL-database. */
	public static final String DEFAULT_HOST = "localhost";

	/** Constant for the default user from MySQL-database. */
	public static final String DEFAULT_USER = "root";

	/** Constant for the default password from MySQL-database. */
	public static final String DEFAULT_PASSWORD = "root";

	/**
	 * Checks if the given database exists in the MySqlDatabase.
	 *
	 * @param hostname
	 *            the hostname
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return true, if successful
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 */
	public static boolean existsDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws SQLException, ClassNotFoundException
	{
		try (Connection connection = MySqlConnectionsExtensions.getConnection(hostname, "", dbuser,
			dbpasswort))
		{
			final DatabaseMetaData meta = connection.getMetaData();
			try (ResultSet rs = meta.getCatalogs())
			{
				final List<String> existingDatabases = new ArrayList<>();
				while (rs.next())
				{
					final String existingDatabaseName = rs.getString("TABLE_CAT");
					existingDatabases.add(existingDatabaseName);
				}
				if (existingDatabases.contains(databaseName))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the my sql connection.
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
	 * @return the my sql connection
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
		final String url = URL_PREFIX + hostname + ":" + portNumber + "/" + databaseName;
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(url, dbuser, dbpasswort);
	}

	/**
	 * Gets the mySql connection
	 *
	 * @param hostname
	 *            the host name
	 * @param databaseName
	 *            the database name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @return the mySql connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword) throws ClassNotFoundException, SQLException
	{
		return getConnection(hostname, MYSQL_PORT, databaseName, dbUser, dbPassword);
	}

	/**
	 * Creates a database with the given databaseName (and sets the character set to utf8 and the
	 * collate to utf8_general_ci) if it does not exist.
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
	 *             is thrown if the Class was not found or could not be located.
	 */
	public static CreationState newDatabase(final @NonNull String hostname,
		final @NonNull String databaseName, final @NonNull String dbUser,
		final @NonNull String dbPassword) throws SQLException, ClassNotFoundException
	{
		return newDatabase(hostname, databaseName, dbUser, dbPassword, "utf8", "utf8_general_ci");
	}

	/**
	 * Creates the a mySql database with the given databaseName if it does not exist.
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
		if (existsDatabase(hostname, databaseName, dbUser, dbPassword))
		{
			return CreationState.ALREADY_EXISTS;
		}
		try (
			Connection connection = MySqlConnectionsExtensions.getConnection(hostname, "", dbUser,
				dbPassword);
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

}
