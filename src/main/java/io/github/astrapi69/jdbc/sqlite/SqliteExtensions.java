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
package io.github.astrapi69.jdbc.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.astrapi69.jdbc.ConnectionsExtensions;
import io.github.astrapi69.jdbc.CreationState;
import lombok.NonNull;

/**
 * The class {@link SqliteExtensions} have convenience methods to create and connect to sqllite
 * databases
 *
 * @author Asterios Raptis
 */
public class SqliteExtensions
{

	/** sqllite-database constants. */
	/**
	 * Constant for the driver name from sqlite-database.
	 */
	public static final String DRIVER_NAME = "org.sqlite.JDBC";

	/**
	 * Constant for the url prefix from sqlite-database.
	 */
	public static final String URL_PREFIX = "jdbc:sqlite:";

	/** Constant for the default user from sqlite-database. */
	public static final String DEFAULT_USER = "sa";

	/** Constant for the default password from sqlite-database. */
	public static final String DEFAULT_PASSWORD = "sa";

	/**
	 * Creates a new sqllite database with the given absolute path directory and the given file name
	 * of the database
	 *
	 * @param directoryPath
	 *            the absolute path to the database directory
	 * @param dbFileName
	 *            the database file name
	 * @return the {@link CreationState}
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static CreationState newFileDatabase(String directoryPath, String dbFileName)
		throws SQLException, ClassNotFoundException
	{
		File file = new File(directoryPath, dbFileName);
		if (file.exists())
		{
			return CreationState.ALREADY_EXISTS;
		}
		getFileConnection(directoryPath, dbFileName);
		return CreationState.CREATED;
	}

	/**
	 * Gets the sqllite connection
	 *
	 * @param directoryPath
	 *            the absolute path to the database directory
	 * @param dbFileName
	 *            the database file name
	 * @return the sqllite connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getFileConnection(final @NonNull String directoryPath,
		final @NonNull String dbFileName) throws ClassNotFoundException, SQLException
	{
		String slashIfMissing = !directoryPath.endsWith("/") ? "/" : "";
		String path = directoryPath + slashIfMissing;
		final String url = URL_PREFIX + path + dbFileName;
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(url);
	}

	/**
	 * Gets the sqllite connection
	 *
	 * @param databaseName
	 *            the database name
	 * @return the sqllite connection
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static Connection getMemoryConnection(final @NonNull String databaseName)
		throws ClassNotFoundException, SQLException
	{
		String url = URL_PREFIX + ":" + "memory" + ":" + databaseName;
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(url);
	}

	/**
	 * Delete all rows from the given table
	 * 
	 * @param connection
	 *            the connection
	 * @param tableName
	 *            the table name
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static void deleteAllRows(Connection connection, String tableName) throws SQLException
	{
		String sql = "DELETE from " + tableName + ";";
		ConnectionsExtensions.executeSqlScript(connection, sql, true);
	}

}
