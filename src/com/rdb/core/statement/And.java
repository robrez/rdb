package com.rdb.core.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class And implements Expression {

    private final List<Expression> expressions = new ArrayList<>();

    private And(List<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    public static And and(Expression... expressions) {
        return new And(Arrays.asList(expressions));
    }

    public static And and(List<Expression> expressions) {
        return new And(expressions);
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        if (!expressions.isEmpty()) {
            final int size = expressions.size();
            if (size > 1) {
                sb.append(" ( ");
            }
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sb.append("\n   AND ");
                }
                expressions.get(i).addToStmt(sb);
            }
            if (size > 1) {
                sb.append(" ) ");
            }
        }
    }
}
