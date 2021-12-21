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

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The class {@link ConnectionsExtensions} have convenience methods to create and connect to mysql,
 * H2 or postgresql databases.
 *
 * @author Asterios Raptis
 */
@UtilityClass
public final class ConnectionsExtensions
{

	/**
	 * Execute the sql script in the given BufferedReader from a file.
	 *
	 * @param bufferedReader
	 *            a BufferedReader from a script file.
	 * @param connection
	 *            the connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static void executeSqlScript(final @NonNull BufferedReader bufferedReader,
		final @NonNull Connection connection) throws IOException, SQLException
	{
		executeSqlScript(bufferedReader, connection, false);
	}

	/**
	 * Execute the sql script in the given BufferedReader from a file.
	 *
	 * @param bufferedReader
	 *            a BufferedReader from a script file.
	 * @param connection
	 *            the connection
	 * @param log
	 *            the flag if it will be logged.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static void executeSqlScript(final @NonNull BufferedReader bufferedReader,
		final @NonNull Connection connection, final boolean log) throws IOException, SQLException
	{
		final StringBuilder sb = new StringBuilder();
		String s;
		while ((s = bufferedReader.readLine()) != null)
		{
			sb.append(s);
		}
		bufferedReader.close();
		final String sqlScript = sb.toString();
		executeSqlScript(connection, sqlScript, log);
	}

	/**
	 * Execute the sql script given as String object.
	 *
	 * @param sqlScript
	 *            The sql script as String object.
	 * @param connection
	 *            the connection
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static void executeSqlScript(final @NonNull Connection connection,
		final @NonNull String sqlScript) throws SQLException
	{
		executeSqlScript(connection, sqlScript, false);
	}

	/**
	 * Execute the sql script given as String object.
	 *
	 * @param sqlScript
	 *            The sql script as String object.
	 * @param connection
	 *            the connection
	 * @param log
	 *            the flag if it will be logged.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 */
	public static void executeSqlScript(final @NonNull Connection connection,
		final @NonNull String sqlScript, final boolean log) throws SQLException
	{
		final String[] inst = sqlScript.split(";");
		try (Statement st = connection.createStatement())
		{
			if (log)
			{
				for (final String inst1 : inst)
				{
					if (!inst1.trim().equals(""))
					{
						st.executeUpdate(inst1);
					}
				}
			}
			else
			{
				for (final String inst1 : inst)
				{
					if (!inst1.trim().equals(""))
					{
						st.executeUpdate(inst1);
					}
				}
			}
		}
	}

}
