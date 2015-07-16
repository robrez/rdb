/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rdb.core.statement;

import com.rdb.core.Column;
import com.rdb.core.statement.StmtBuilder;

/**
 *
 * @author rob
 */
public class NotEqualTo<T> implements Expression {

    private Column<T> column1;
    private Column<T> column2;
    private T[] values;

    private NotEqualTo(Column<T> column1, Column<T> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    private NotEqualTo(Column<T> column, T... values) {
        this.column1 = column;
        this.values = values;
    }

    public static <T> NotEqualTo<T> notEqualTo(Column<T> firstColumn, Column<T> secondColumn) {
        NotEqualTo<T> notEqualTo = new NotEqualTo<>(firstColumn, secondColumn);
        return notEqualTo;
    }

    public static <T> NotEqualTo<T> notEqualTo(Column<T> column, T... values) {
        NotEqualTo<T> notEqualTo = new NotEqualTo<>(column, values);
        return notEqualTo;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (column2 != null) {
            column1.addToStmt(sb);
            sb.append(" != ");
            column2.addToStmt(sb);
        } else {
            if (values != null) {
                if (values.length > 1) {
                    sb.append(" ( ");
                }
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        sb.append(" AND ");
                    }
                    column1.addToStmt(sb);
                    sb.append(" != ?");
                    sb.addParam(values[i]);
                }
                if (values.length > 1) {
                    sb.append(" ) ");
                }
            }
        }
    }
}
