package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 */
public class SetEqualTo<T> implements UpdateExpression, InsertExpression {

    private Column<T> column1;
    private Column<T> column2;
    private T value;

    private SetEqualTo(Column<T> column1, Column<T> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    private SetEqualTo(Column<T> column, T value) {
        this.column1 = column;
        this.value = value;
    }

    public static <T> SetEqualTo<T> setEqualTo(Column<T> firstColumn, Column<T> secondColumn) {
        SetEqualTo<T> setEqualTo = new SetEqualTo<>(firstColumn, secondColumn);
        return setEqualTo;
    }

    public static <T> SetEqualTo<T> setEqualTo(Column<T> column, T value) {
        SetEqualTo<T> setEqualTo = new SetEqualTo<>(column, value);
        return setEqualTo;
    }

    public Column<T> getColumn1() {
        return column1;
    }

    public Column<T> getColumn2() {
        return column2;
    }

    public T getValue() {
        return value;
    }

    /**
     * For updates, add the column, operator and value
     *
     * @param sb
     */
    @Override
    public void addToStmt(StmtBuilder sb) {
        if (column2 != null) {
            column1.addToStmt(sb);
            sb.append(" = ");
            column2.addToStmt(sb);
        } else {
            column1.addToStmt(sb);
            sb.append(" = ?");
            sb.addParam(value);
        }
    }

    /**
     * For inserts, only add the column name
     *
     * @param sb
     */
    @Override
    public void addNameComponenet(StmtBuilder sb) {
        column1.addToStmt(sb);
    }

    /**
     * For inserts, only add the value
     *
     * @param sb
     */
    @Override
    public void addValueComponenet(StmtBuilder sb) {
        if (column2 != null) {
            column2.addToStmt(sb);
        } else {
            sb.append("?");
            sb.addParam(value);
        }
    }
}
