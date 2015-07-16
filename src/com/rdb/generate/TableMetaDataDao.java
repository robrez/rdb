package com.rdb.generate;

import com.rdb.jdbc.Db;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rob
 */
public class TableMetaDataDao {

    private final Db db;

    public TableMetaDataDao(Db db) {
        this.db = db;
    }

    public TableDbMetaData load(Db db, String table) {
        try {
            TableDbMetaData tableMetaData = new TableDbMetaData(table);
            DatabaseMetaData dbmd = db.getDatabaseMetaData();
            Set<String> primaryKeys = getPrimaryKeys(table);
            try (ResultSet rs = dbmd.getColumns(null, null, table, null)) {
                while (rs.next()) {
                    if (rs.getRow() == 1) {
                        tableMetaData.setCatalog(rs.getString("TABLE_CAT"));
                        tableMetaData.setSchema(rs.getString("TABLE_SCHEM"));
                    }
                    ColumnDbMetaData cmd = new ColumnDbMetaData(rs.getString("COLUMN_NAME"));
                    cmd.setTypeName(rs.getString("TYPE_NAME"));
                    cmd.setColSize(rs.getInt("COLUMN_SIZE"));
                    cmd.setDataType(rs.getInt("DATA_TYPE"));
                    cmd.setDecDigits(rs.getInt("DECIMAL_DIGITS"));
                    cmd.setAutoIncrement(rs.getString("IS_AUTOINCREMENT"));
                    if (primaryKeys.contains(cmd.getColName())) {
                        cmd.setPrimaryKey(Boolean.TRUE);
                    }
                    tableMetaData.add(cmd);
                }
            }
            return tableMetaData;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Set<String> getPrimaryKeys(String table) {
        try {
            Set<String> primaryKeys;
            try (ResultSet pkRs = db.getDatabaseMetaData().getPrimaryKeys(null, null, table)) {
                primaryKeys = new HashSet<>();
                while (pkRs.next()) {
                    primaryKeys.add(pkRs.getString("COLUMN_NAME"));
                }
            }
            return primaryKeys;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
