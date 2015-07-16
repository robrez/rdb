package com.rdb.core.statement;

/**
 *
 * @author rob
 */
public class ColumnAlias implements Alias {

    private final String alias;

    public ColumnAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

}
