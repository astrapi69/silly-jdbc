package de.alpharogroup.jdbc;

import lombok.experimental.UtilityClass;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DatabaseMetadataExtensions {

    private final static DatabaseMetaDataCallback getTableNamesDatabaseMetaDataCallback = dbmd -> {
        ResultSet rs = dbmd.getTables(dbmd.getUserName(), null, null, new String[]{"TABLE"});
        List<String> tables = new ArrayList<>();
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        return tables;
    };

    public static Object getTableNames(DataSource dataSource) throws MetaDataAccessException {
        return JdbcUtils.extractDatabaseMetaData(dataSource, getTableNamesDatabaseMetaDataCallback);
    }
    
}
