package com.rdb.core.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class Or implements Expression {

    private final List<Expression> expressions = new ArrayList<>();

    private Or(List<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    public static Or or(Expression... expressions) {
        return new Or(Arrays.asList(expressions));
    }

    public static Or or(List<Expression> expressions) {
        return new Or(expressions);
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
                    sb.append("\n   OR ");
                }
                expressions.get(i).addToStmt(sb);
            }
            if (size > 1) {
                sb.append(" ) ");
            }
        }
    }
}
