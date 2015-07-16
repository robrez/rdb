package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 */
public class LessOrEqualTo<T> implements Expression {

    private static final String OPERATOR = " <= ";
    private Column<T> column1;
    private Column<T> column2;
    private T value;

    private LessOrEqualTo(Column<T> column1, Column<T> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    private LessOrEqualTo(Column<T> column, T value) {
        this.column1 = column;
        this.value = value;
    }

    public static <T> LessOrEqualTo<T> lessOrEqualTo(Column<T> firstColumn, Column<T> secondColumn) {
        LessOrEqualTo<T> lessOrEqualTo = new LessOrEqualTo<>(firstColumn, secondColumn);
        return lessOrEqualTo;
    }

    public static <T> LessOrEqualTo<T> lessOrEqualTo(Column<T> column, T value) {
        LessOrEqualTo<T> lessOrEqualTo = new LessOrEqualTo<>(column, value);
        return lessOrEqualTo;
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
