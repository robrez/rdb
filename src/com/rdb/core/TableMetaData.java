package com.rdb.core;

/**
 *
 * @author rob
 */
public final class TableMetaData {

    private final String catalog;
    private final String schema;
    private final String name;

    public TableMetaData(String catalog, String schema, String name) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
    }

    /**
     * @return the catalog
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }
}
