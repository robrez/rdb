package com.rdb.core;

import com.rdb.core.statement.Queryable;
import com.rdb.core.statement.StmtBuilder;
import com.rdb.core.statement.Writable;

/**
 *
 * @author rob
 */
public abstract class Table implements Queryable, Writable {

    private final TableMetaData tableMetaData;
    private final TableAlias alias;

    protected Table(TableMetaData tmd, TableAlias a) {
        this.tableMetaData = tmd;
        this.alias = a;
    }

    protected final TableMetaData getTableMetaData() {
        return this.tableMetaData;
    }

    protected final TableAlias getTableAlias() {
        return this.alias;
    }

    @Override
    public final void addToStmt(StmtBuilder sb) {
        sb.append(getTableMetaData().getName());
        if (getTableAlias() != null && sb.shouldIncludeAlias()) {
            sb.append(" AS ");
            sb.append(getTableAlias().getAlias());
        }
    }
}
