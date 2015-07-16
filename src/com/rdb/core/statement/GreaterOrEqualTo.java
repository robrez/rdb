package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 * @param <T>
 */
public class GreaterOrEqualTo<T> implements Expression {

    private static final String OPERATOR = " >= ";
    private final Column<T> column1;
    private Column<T> column2;
    private T value;

    private GreaterOrEqualTo(Column<T> column1, Column<T> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    private GreaterOrEqualTo(Column<T> column, T value) {
        this.column1 = column;
        this.value = value;
    }

    public static <T> GreaterOrEqualTo<T> greaterOrEqualTo(Column<T> firstColumn, Column<T> secondColumn) {
        GreaterOrEqualTo<T> greaterOrEqualTo = new GreaterOrEqualTo<>(firstColumn, secondColumn);
        return greaterOrEqualTo;
    }

    public static <T> GreaterOrEqualTo<T> greaterOrEqualTo(Column<T> column, T value) {
        GreaterOrEqualTo<T> greaterOrEqualTo = new GreaterOrEqualTo<>(column, value);
        return greaterOrEqualTo;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (column2 != null) {
            column1.addToStmt(sb);
            sb.append(OPERATOR);
            column2.addToStmt(sb);
        } else {
            column1.addToStmt(sb);
            sb.append(OPERATOR).append("?");
            sb.addParam(value);
        }
    }
}
