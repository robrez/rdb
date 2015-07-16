package com.rdb.core;

import com.rdb.core.statement.Alias;

/**
 *
 * @author rob
 */
public class TableAlias implements Alias {

    private final String tableAlias;

    public TableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    @Override
    public String getAlias() {
        return tableAlias;
    }

}
