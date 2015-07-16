package com.rdb.core.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class InnerJoin implements JoinExpression {

    private final Queryable table;
    private final List<Expression> joinExpressions = new ArrayList<>();

    public InnerJoin(Queryable table) {
        this.table = table;
    }

    public InnerJoin on(Expression... expressions) {
        joinExpressions.addAll(Arrays.asList(expressions));
        return this;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        sb.append("\n INNER JOIN ");
        table.addToStmt(sb);
        if (joinExpressions.size() > 0) {
            StmtPart sv = sb.getCurrentStmtPart();
            sb.setCurrentStmtPart(StmtPart.FILTER);
            sb.append("\n   ON ");
            And expressions = And.and(joinExpressions);
            expressions.addToStmt(sb);
            sb.setCurrentStmtPart(sv);
        }
    }
}
