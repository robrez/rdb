package com.rdb.core.statement;

import com.rdb.core.Table;
import com.rdb.jdbc.Db;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An udpate statement
 *
 * @author rob
 */
public class Update implements WriteStatement {

    private Table table;
    private final List<UpdateExpression> updateExpressions = new ArrayList<>();
    private final List<Expression> whereExpressions = new ArrayList<>();

    public Update table(Table table) {
        this.table = table;
        return this;
    }

    public Update set(UpdateExpression... updateExpressions) {
        this.updateExpressions.addAll(Arrays.asList(updateExpressions));
        return this;
    }

    public Update where(Expression... whereExpressions) {
        this.whereExpressions.addAll(Arrays.asList(whereExpressions));
        return this;
    }

    @Override
    public PreparedStatement prepare(Db db) {
        return buildStatement().prepare(db);
    }

    private StmtBuilder buildStatement() {
        StmtBuilder sb = new StmtBuilder(StmtType.UPDATE);
        sb.append("UPDATE ");
        table.addToStmt(sb);
        sb.append(" SET ");
        for (int i = 0; i < updateExpressions.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            updateExpressions.get(i).addToStmt(sb);
        }
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
