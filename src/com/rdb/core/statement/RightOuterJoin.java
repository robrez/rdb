package com.rdb.core.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class RightOuterJoin implements JoinExpression {

    private final Queryable table;
    private final List<Expression> joinExpressions = new ArrayList<>();

    public RightOuterJoin(Queryable table) {
        this.table = table;
    }

    public RightOuterJoin on(Expression... expressions) {
        joinExpressions.addAll(Arrays.asList(expressions));
        return this;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        sb.append("\n RIGHT OUTER JOIN ");
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
