package com.rdb.core;

import com.rdb.core.statement.Alias;
import com.rdb.core.statement.ColumnAlias;
import com.rdb.core.statement.EqualTo;
import com.rdb.core.statement.GreaterOrEqualTo;
import com.rdb.core.statement.LessOrEqualTo;
import com.rdb.core.statement.NotEqualTo;
import com.rdb.core.statement.SetEqualTo;
import com.rdb.core.statement.Sort;
import com.rdb.core.statement.StmtBuilder;

/**
 *
 * @author rob
 * @param <T>
 */
public abstract class BasicColumn<T> implements Column<T> {

    private final String name;
    private TableAlias tableAlias;
    private Alias columnAlias;

    protected BasicColumn(String name) {
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
    public Column<T> as(String alias) {
        this.columnAlias = new ColumnAlias(alias);
        return this;
    }

    public SetEqualTo<T> setTo(Column<T> anotherColumn) {
        return SetEqualTo.setEqualTo(this, anotherColumn);
    }

    public SetEqualTo<T> setTo(T value) {
        return SetEqualTo.setEqualTo(this, value);
    }

    public EqualTo<T> equalTo(Column<T> anotherColumn) {
        return EqualTo.equalTo(this, anotherColumn);
    }

    public EqualTo<T> equalTo(T... values) {
        return EqualTo.equalTo(this, values);
    }

    public EqualTo<T> equalTo(T value) {
        return EqualTo.equalTo(this, value);
    }

    public NotEqualTo<T> notEqualTo(Column<T> anotherColumn) {
        return NotEqualTo.notEqualTo(this, anotherColumn);
    }

    public NotEqualTo<T> notEqualTo(T... values) {
        return NotEqualTo.notEqualTo(this, values);
    }

    public NotEqualTo<T> notEqualTo(T value) {
        return NotEqualTo.notEqualTo(this, value);
    }

    public LessOrEqualTo<T> lessOrEqualTo(Column<T> anotherColumn) {
        return LessOrEqualTo.lessOrEqualTo(this, anotherColumn);
    }

    public LessOrEqualTo<T> lessOrEqualTo(T value) {
        return LessOrEqualTo.lessOrEqualTo(this, value);
    }

    public GreaterOrEqualTo<T> greaterOrEqualTo(Column<T> anotherColumn) {
        return GreaterOrEqualTo.greaterOrEqualTo(this, anotherColumn);
    }

    public GreaterOrEqualTo<T> geraterOrEqualTo(T value) {
        return GreaterOrEqualTo.greaterOrEqualTo(this, value);
    }

    public Sort asc() {
        return new Sort(this).asc();
    }

    public Sort desc() {
        return new Sort(this).desc();
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
