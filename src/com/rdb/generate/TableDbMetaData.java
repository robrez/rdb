package com.rdb.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author rob
 */
public class TableDbMetaData {

    private final String name;
    private String catalog;
    private String schema;
    private final List<ColumnDbMetaData> columns = new ArrayList<>();

    public TableDbMetaData(String tableName) {
        this.name = tableName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public List<ColumnDbMetaData> getColumns() {
        List<ColumnDbMetaData> copy = new ArrayList<>();
        copy.addAll(columns);
        Collections.sort(copy, new Comparator<ColumnDbMetaData>() {

            @Override
            public int compare(ColumnDbMetaData o1, ColumnDbMetaData o2) {
                return o1.getColName().compareTo(o2.getColName());
            }
        });
        return copy;
    }

    public List<ColumnDbMetaData> getKeyColumns() {
        List<ColumnDbMetaData> keys = new ArrayList<>();
        for (ColumnDbMetaData col : columns) {
            if (col.isPrimaryKey()) {
                keys.add(col);
            }
        }
        Collections.sort(keys);
        return keys;
    }

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void add(ColumnDbMetaData columnMetaData) {
        columns.add(columnMetaData);
    }
}
