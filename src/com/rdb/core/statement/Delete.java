package com.rdb.core.statement;

import com.rdb.core.Table;
import com.rdb.jdbc.Db;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A delete statement
 *
 * @author rob
 */
public class Delete implements WriteStatement {

    private final List<Expression> whereExpressions = new ArrayList<>();
    private Table table;

    public Delete from(Table table) {
        this.table = table;
        return this;
    }

    public Delete where(Expression... whereExpressions) {
        this.whereExpressions.addAll(Arrays.asList(whereExpressions));
        return this;
    }

    @Override
    public PreparedStatement prepare(Db db) {
        return buildStatement().prepare(db);
    }

    private StmtBuilder buildStatement() {
        StmtBuilder sb = new StmtBuilder(StmtType.DELETE);
        sb.append("DELETE FROM ");
        table.addToStmt(sb);
        if (whereExpressions.size() > 0) {
            And expressions = And.and(whereExpressions);
            sb.append(" WHERE ");
            expressions.addToStmt(sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return buildStatement().toString();
    }
}
