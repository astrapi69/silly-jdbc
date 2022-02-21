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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.Server;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.jdbc.ConnectionsExtensions;

/**
 * The unit test class for the class {@link ConnectionsExtensions}.
 */
public class H2ConnectionsExtensionsTest
{

	/**
	 * Test method for {@link H2ConnectionsExtensions#getConnection(String, String, String, String)}
	 *
	 * @throws ClassNotFoundException
	 *             is thrown if the Class was not found or could not be located.
	 * @throws SQLException
	 *             is thrown if a database access error occurs or this method is called on a closed
	 *             connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetH2Connection() throws ClassNotFoundException, SQLException, IOException
	{
		String path;
		String databaseName;
		String dbuser;
		String dbpasswort;
		Connection connection;
		// create temporary directory for database file ...
		File h2Dir = FileFactory.newDirectory(PathFinder.getSrcTestResourcesDir(), "h2");
		path = h2Dir.getAbsolutePath();
		databaseName = "resourcebundles";
		dbuser = "sa";
		dbpasswort = "";
		Server server = H2Launcher.newServer();
		H2Launcher.start(server);
		connection = H2ConnectionsExtensions.getConnection(path, databaseName, dbuser, dbpasswort);
		assertNotNull(connection);
		H2Launcher.stop(server);
		// clean up
		DeleteFileExtensions.delete(h2Dir);
	}

}
