package com.rdb.core;

import com.rdb.core.statement.Alias;
import com.rdb.core.statement.ColumnAlias;
import com.rdb.core.statement.SetEqualTo;
import com.rdb.core.statement.StmtBuilder;
import java.util.List;

/**
 * Array columns are wierd and are implemented as lists. They cannot use the
 * typical methods that other datatypes can use
 *
 * @author rob
 * @param <T>
 */
public class ArrayColumn<T> implements Column<List<T>> {

    private final String name;
    private TableAlias tableAlias;
    private Alias columnAlias;

    public ArrayColumn(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String nameOrAlias() {
        if (columnAlias != null) {
            return columnAlias.getAlias();
        }
        return name;
    }

    @Override
    public void setTableAlias(TableAlias alias) {
        if (this.tableAlias != null) {
            throw new RuntimeException("Table Alias Cannot Be Specified More than Once!");
        }
        this.tableAlias = alias;
    }

    @Override
    public Column<List<T>> as(String alias) {
        this.columnAlias = new ColumnAlias(alias);
        return this;
    }

    public SetEqualTo<List<T>> setTo(Column<List<T>> anotherColumn) {
        return SetEqualTo.setEqualTo(this, anotherColumn);
    }

    public SetEqualTo<List<T>> setTo(List<T> value) {
        return SetEqualTo.setEqualTo(this, value);
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (tableAlias != null && sb.shouldIncludeTableAlias()) {
            sb.append(tableAlias.getAlias());
            sb.append(".");
        }
        sb.append(name);
        if (columnAlias != null && sb.shouldIncludeAlias()) {
            sb.append(" AS ");
            sb.append(columnAlias.getAlias());
        }
    }

}
