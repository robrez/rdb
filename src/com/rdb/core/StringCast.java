package com.rdb.core;

import com.rdb.core.statement.StmtBuilder;

/**
 * Cast a column to varchar
 *
 * @author rob
 */
public class StringCast extends BasicColumn<String> {

    public StringCast(Column column) {
        super(column.name());
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        sb.append(name()).append("::varchar");
    }
}
