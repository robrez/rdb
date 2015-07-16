package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 */
public class EqualTo<T> implements Expression {

    private static final String OPERATOR = " = ";
    private Column<T> column1;
    private Column<T> column2;
    private T[] values;

    private EqualTo(Column<T> column1, Column<T> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    private EqualTo(Column<T> column, T... values) {
        this.column1 = column;
        this.values = values;
    }

    public static <T> EqualTo<T> equalTo(Column<T> firstColumn, Column<T> secondColumn) {
        EqualTo<T> equalTo = new EqualTo<>(firstColumn, secondColumn);
        return equalTo;
    }

    public static <T> EqualTo<T> equalTo(Column<T> column, T... values) {
        EqualTo<T> equalTo = new EqualTo<>(column, values);
        return equalTo;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (column2 != null) {
            column1.addToStmt(sb);
            sb.append(OPERATOR);
            column2.addToStmt(sb);
        } else {
            if (values != null) {
                if (values.length > 1) {
                    sb.append(" ( ");
                }
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        sb.append(" OR ");
                    }
                    column1.addToStmt(sb);
                    sb.append(OPERATOR).append("?");
                    sb.addParam(values[i]);
                }
                if (values.length > 1) {
                    sb.append(" ) ");
                }
            }
        }
    }
}
