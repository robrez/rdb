package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 */
public class Like implements Expression {

    private Column<String> column1;
    private String[] values;

    private Like(Column<String> column, String... values) {
        this.column1 = column;
        this.values = values;
    }

    public static Like like(Column<String> column, String... values) {
        Like like = new Like(column, values);
        return like;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (values != null) {
            if (values.length > 1) {
                sb.append(" ( ");
            }
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    sb.append(" OR ");
                }
                column1.addToStmt(sb);
                sb.append(" like ?");
                sb.addParam(values[i]);
            }
            if (values.length > 1) {
                sb.append(" ) ");
            }
        }
    }
}
