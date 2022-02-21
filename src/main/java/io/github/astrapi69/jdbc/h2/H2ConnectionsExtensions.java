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
package io.github.astrapi69.jdbc.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The class {@link H2ConnectionsExtensions} have convenience methods to create and connect to H2
 * databases
 *
 * @author Asterios Raptis
 */
@UtilityClass
public final class H2ConnectionsExtensions
{

	/** H2-database constants. */
	/** Constant for the drivername from H2-database. */
	public static final String DRIVER_NAME = "org.h2.Driver";

	/** Constant for the urlprefix from H2-database. */
	public static final String URL_PREFIX = "jdbc:h2:";

	/** Constant for the default user from H2-database. */
	public static final String DEFAULT_USER = "sa";

	/** Constant for the default password from H2-database. */
	public static final String DEFAULT_PASSWORD = "";

	/**
	 * Gets the H2 connection.
	 *
	 * @param path
	 *            the path
	 * @param databaseName
	 *            the database name
	 * @param dbuser
	 *            the dbuser
	 * @param dbpasswort
	 *            the dbpasswort
	 * @return the H2 connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String path,
		final @NonNull String databaseName, final @NonNull String dbuser,
		final @NonNull String dbpasswort) throws ClassNotFoundException, SQLException
	{
		String slashIfMissing = !path.endsWith("/") ? "/" : "";
		String directoryPath = path + slashIfMissing;
		final String url = URL_PREFIX + directoryPath + databaseName;
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(url, dbuser, dbpasswort);
	}

	/**
	 * Gets the H2 connection with the given path, database name, the default user and the default
	 * password
	 *
	 * @param path
	 *            the path
	 * @param databaseName
	 *            the database name
	 * @return the H2 connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String path,
		final @NonNull String databaseName) throws ClassNotFoundException, SQLException
	{
		return getConnection(path, databaseName, DEFAULT_USER, DEFAULT_PASSWORD);
	}

}
