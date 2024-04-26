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
package io.github.astrapi69.jdbc.hsqldb;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
class HyperSQLExtensionsTest
{

	String tableName = "accounts";

	@Test
	void getFileConnection() throws SQLException, ClassNotFoundException, IOException
	{
		Statement stmt;
		Connection connection;
		String path;
		String databaseName;
		String dbuser;
		String dbpasswort;
		File databaseDirectory;
		File srcTestResourcesDir = PathFinder.getSrcTestResourcesDir();
		databaseDirectory = PathFinder.getRelativePath(srcTestResourcesDir, "hsql");

		path = databaseDirectory.getAbsolutePath() + "/";

		dbuser = "sa";
		dbpasswort = "";
		databaseName = "accounts";

		connection = HyperSQLExtensions.getFileConnection(path, databaseName, dbuser, dbpasswort);

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
			+ "	id integer PRIMARY KEY,\n" + "	name CHAR(50) NOT NULL,\n" + "	balance bigint\n"
			+ ");";

		stmt = connection.createStatement();
		stmt.executeQuery(sql);

		insert(connection, 1, "Superman", 3000);
		insert(connection, 2, "Spiderman", 4000);
		insert(connection, 3, "Batman", 5000);
		selectAll(connection);
		findBalanceGreaterThan(connection, 3900);
		update(connection, 3, "Batman", 5500);
		selectAll(connection);
		delete(connection, 3);
		DeleteFileExtensions.delete(databaseDirectory);
	}

	@Test
	void getMemoryConnection() throws SQLException, ClassNotFoundException
	{
		System.out.println("Database in Memory:");
		Statement stmt;
		Connection connection;

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
			+ "	id integer PRIMARY KEY,\n" + "	name CHAR(50) NOT NULL,\n" + "	balance bigint\n"
			+ ");";
		connection = HyperSQLExtensions.getMemoryConnection("hsqldb",
			HyperSQLExtensions.DEFAULT_USER, HyperSQLExtensions.DEFAULT_PASSWORD);

		stmt = connection.createStatement();
		stmt.executeQuery(sql);


		insert(connection, 1, "Superman", 3000);
		insert(connection, 2, "Spiderman", 4000);
		insert(connection, 3, "Batman", 5000);
		selectAll(connection);
		findBalanceGreaterThan(connection, 3900);
		update(connection, 3, "Batman", 5500);
		selectAll(connection);
		delete(connection, 3);
	}

	/**
	 * select all rows in the accounts table
	 *
	 * @param connection
	 *            the connection to the sqlite database
	 */
	public void selectAll(final @NonNull Connection connection)
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
	public void findBalanceGreaterThan(final @NonNull Connection connection, double balance)
	{
		String sql = "SELECT id, name, balance " + "FROM " + tableName + " WHERE balance > ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql))
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
	public void insert(final @NonNull Connection connection, int id, String name, double balance)
	{
		String sql = "INSERT INTO " + tableName + "(id,name,balance) VALUES(?,?,?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql))
		{
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setDouble(3, balance);
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
	public void update(final @NonNull Connection connection, int id, String name, double balance)
	{
		String sql = "UPDATE " + tableName + " SET name = ? , " + "balance = ? " + "WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql))
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
	public void delete(final @NonNull Connection connection, int id)
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
