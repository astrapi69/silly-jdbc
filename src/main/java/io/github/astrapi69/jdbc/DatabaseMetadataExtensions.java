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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.experimental.UtilityClass;

import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

/**
 * The class {@link DatabaseMetadataExtensions} holds algorithms for provide data from the
 * DatabaseMetaData
 */
@UtilityClass
public class DatabaseMetadataExtensions
{

	/** The Constant Callback for get the Table Names from the DatabaseMetaData */
	private final static DatabaseMetaDataCallback getTableNamesDatabaseMetaDataCallback = dbmd -> {
		List<String> tables = new ArrayList<>();
		try (
			ResultSet rs = dbmd.getTables(dbmd.getUserName(), null, null, new String[] { "TABLE" }))
		{
			while (rs.next())
			{
				tables.add(rs.getString(3));
			}
		}
		return tables;
	};

	/**
	 * Gets the table names from the given {@link DataSource}
	 *
	 * @param dataSource
	 *            the data source
	 * @return the table names
	 * @throws MetaDataAccessException
	 *             is thrown if something went wrong during JDBC meta-data lookup
	 */
	public static Object getTableNames(DataSource dataSource) throws MetaDataAccessException
	{
		return JdbcUtils.extractDatabaseMetaData(dataSource, getTableNamesDatabaseMetaDataCallback);
	}

}
