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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.jdbc.ConnectionsExtensions;
import io.github.astrapi69.jdbc.CreationState;

@Log
public class SqliteExtensionsTest
{

	String tableName = "accounts";

	@Test
	void getMemoryConnection() throws SQLException, ClassNotFoundException, IOException
	{
		// create temporary directory for database file ...

		File projectDirectory = PathFinder.getProjectDirectory();
		String path = projectDirectory.getAbsolutePath();
		String databaseName = tableName + ".sqlite";
		File memoryFile = FileFactory.newFile(projectDirectory, ":memory:" + databaseName);
		Connection connection = SqliteExtensions.getMemoryConnection(databaseName);

		// SQL statement for creating a new table
		String sql = createTableStatement();
		ConnectionsExtensions.executeSqlScript(connection, sql, true);
		SqliteExtensions.deleteAllRows(SqliteExtensions.getMemoryConnection(databaseName),
			"accounts");
		insert(SqliteExtensions.getMemoryConnection(databaseName), "Superman", 3000);
		insert(SqliteExtensions.getMemoryConnection(databaseName), "Spiderman", 4000);
		insert(SqliteExtensions.getMemoryConnection(databaseName), "Batman", 5000);
		selectAll(SqliteExtensions.getMemoryConnection(databaseName));
		findBalanceGreaterThan(SqliteExtensions.getMemoryConnection(databaseName), 3900);
		update(SqliteExtensions.getMemoryConnection(databaseName), 3, "Batman", 5500);
		selectAll(SqliteExtensions.getMemoryConnection(databaseName));
		delete(SqliteExtensions.getMemoryConnection(databaseName), 3);
		// clean up
		DeleteFileExtensions.delete(memoryFile);
	}

	public String createTableStatement()
	{
		return "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "	id integer PRIMARY KEY,\n"
			+ "	name text NOT NULL,\n" + "	balance real\n" + ");";
	}

	@Test
	void getFileConnection() throws SQLException, ClassNotFoundException, IOException
	{
		// create temporary directory for database file ...
		File sqliteDir = FileFactory.newDirectory(PathFinder.getSrcTestResourcesDir(), "sqlite");
		String path = sqliteDir.getAbsolutePath();
		String databaseName = tableName + ".sqlite";
		Connection connection = SqliteExtensions.getFileConnection(path, databaseName);

		CreationState creationState = SqliteExtensions.newFileDatabase(path, databaseName);
		assertEquals(creationState, CreationState.ALREADY_EXISTS);

		// SQL statement for creating a new table
		String sql = createTableStatement();
		ConnectionsExtensions.executeSqlScript(connection, sql, true);
		SqliteExtensions.deleteAllRows(SqliteExtensions.getFileConnection(path, databaseName),
			"accounts");
		insert(SqliteExtensions.getFileConnection(path, databaseName), "Superman", 3000);
		insert(SqliteExtensions.getFileConnection(path, databaseName), "Spiderman", 4000);
		insert(SqliteExtensions.getFileConnection(path, databaseName), "Batman", 5000);
		selectAll(SqliteExtensions.getFileConnection(path, databaseName));
		findBalanceGreaterThan(SqliteExtensions.getFileConnection(path, databaseName), 3900);
		update(SqliteExtensions.getFileConnection(path, databaseName), 3, "Batman", 5500);
		selectAll(SqliteExtensions.getFileConnection(path, databaseName));
		delete(SqliteExtensions.getFileConnection(path, databaseName), 3);
		// clean up
		 DeleteFileExtensions.delete(sqliteDir);
	}

	/**
	 * select all rows in the accounts table
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 */
	public void selectAll(Connection connection)
	{
		String sql = "SELECT id, name, balance FROM " + tableName;

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql))
		{
			while (rs.next())
			{
				log.log(Level.INFO,
					rs.getInt("id") + "\n" + rs.getString("name") + "\n" + rs.getDouble("balance"));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Get the account whose balance greater than a specified balance
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 * @param balance
	 *            the balance of the account
	 */
	public void findBalanceGreaterThan(Connection connection, double balance)
	{
		String sql = "SELECT id, name, balance " + "FROM " + tableName + " WHERE balance > ?";

		try (Connection conn = connection; PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setDouble(1, balance);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				log.log(Level.INFO,
					rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("balance"));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Insert a new row into the accounts table
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 * @param name
	 *            the name of the owner from the account
	 * @param balance
	 *            the balance of the account
	 */
	public void insert(Connection connection, String name, double balance)
	{
		String sql = "INSERT INTO " + tableName + "(name,balance) VALUES(?,?)";

		try (Connection conn = connection; PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, name);
			pstmt.setDouble(2, balance);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Update the data of an account specified by the id
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 * @param id
	 *            the id
	 * @param name
	 *            the name of the owner from the account
	 * @param balance
	 *            the balance of the account
	 */
	public void update(Connection connection, int id, String name, double balance)
	{
		String sql = "UPDATE " + tableName + " SET name = ? , " + "balance = ? " + "WHERE id = ?";

		try (Connection conn = connection; PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, name);
			pstmt.setDouble(2, balance);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Delete a account specified by the id
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 * @param id
	 *            the id of the account
	 */
	public void delete(Connection connection, int id)
	{
		String sql = "DELETE FROM " + tableName + " WHERE id = ?";

		try (Connection conn = connection; PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
	}

}
