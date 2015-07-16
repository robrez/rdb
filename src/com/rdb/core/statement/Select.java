package com.rdb.core.statement;

import com.rdb.core.Column;
import com.rdb.jdbc.Db;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class Select implements ReadStatement {

    private Queryable table;
    private final List<JoinExpression> joinExpressions = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();
    private final List<Expression> whereExpressions = new ArrayList<>();
    private final List<Sort> sortOrder = new ArrayList<>();

    public Select columns(Column... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public Select from(Queryable table) {
        this.table = table;
        return this;
    }

    public Select where(Expression... expressions) {
        whereExpressions.addAll(Arrays.asList(expressions));
        return this;
    }

    public Select join(JoinExpression... joinExpressions) {
        this.joinExpressions.addAll(Arrays.asList(joinExpressions));
        return this;
    }

    public Select orderBy(Sort... sorts) {
        this.sortOrder.addAll(Arrays.asList(sorts));
        return this;
    }

    @Override
    public PreparedStatement prepare(Db db) {
        return buildStatement().prepare(db);
    }

    private StmtBuilder buildStatement() {
        StmtBuilder sb = new StmtBuilder(StmtType.SELECT);
        sb.append("SELECT\n ");
        sb.setCurrentStmtPart(StmtPart.COLUMN);
        if (columns.isEmpty()) {
            sb.append(" * ");
        } else {
            for (int i = 0; i < columns.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                columns.get(i).addToStmt(sb);
            }
        }
        sb.append("\n FROM ");
        sb.setCurrentStmtPart(StmtPart.TABLE);
        table.addToStmt(sb);
        sb.setCurrentStmtPart(StmtPart.JOIN);
        if (joinExpressions.size() > 0) {
            for (JoinExpression je : joinExpressions) {
                je.addToStmt(sb);
            }
        }
        sb.setCurrentStmtPart(StmtPart.FILTER);
        if (whereExpressions.size() > 0) {
            And expressions = And.and(whereExpressions);
            sb.append("\n WHERE ");
            expressions.addToStmt(sb);
        }
        sb.setCurrentStmtPart(StmtPart.SORT);
        if (sortOrder.size() > 0) {
            sb.append("\n ORDER BY ");
            for (int i = 0; i < sortOrder.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sortOrder.get(i).addToStmt(sb);
            }
        }
        sb.setCurrentStmtPart(StmtPart.GROUP);
        return sb;
    }

    @Override
    public String toString() {
        return buildStatement().toString();
    }

}
