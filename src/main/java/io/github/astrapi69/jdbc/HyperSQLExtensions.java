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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.NonNull;

/**
 * The class {@link HyperSQLExtensions} have convenience methods to connect to HyperSQL databases
 *
 * @author Asterios Raptis
 */
public class HyperSQLExtensions
{

	/** HyperSQL-database constants. */
	/** Constant for the drivername from HyperSQL-database. */
	public static final String DRIVER_NAME = "org.hsqldb.jdbc.JDBCDriver";
	/** Constant for the urlprefix from HyperSQL-database. */
	public static final String URL_PREFIX = "jdbc:hsqldb";
	/** Constant for the default user from HyperSQL-database. */
	public static final String DEFAULT_USER = "SA";
	/** Constant for the default password from HyperSQL-database. */
	public static final String DEFAULT_PASSWORD = "";
	/** Constant for the default password from HyperSQL-database. */
	public static final String CATALOG_TYPE_FILE = "file";

	/**
	 * Gets the HyperSQL connection to a file catalog
	 *
	 * @param directoryPath
	 *            the absolute path to the database directory
	 * @param dbFileName
	 *            the database file name
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @return the HyperSQL connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getConnection(final @NonNull String directoryPath,
		final @NonNull String dbFileName, final @NonNull String dbUser,
		final @NonNull String dbPassword) throws ClassNotFoundException, SQLException
	{
		final String url = URL_PREFIX + ":" + CATALOG_TYPE_FILE + ":" + directoryPath + dbFileName;
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(url, dbUser, dbPassword);
	}
}
