package com.rdb.core;

import com.rdb.core.statement.StmtComponent;

/**
 *
 * @author rob
 * @param <T>
 */
public interface Column<T> extends StmtComponent {

    public String name();

    public String nameOrAlias();

    public void setTableAlias(TableAlias alias);

    public Column<T> as(String alias);

}
