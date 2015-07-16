package com.rdb.core.statement;

import com.rdb.core.Column;
import com.rdb.core.Table;
import com.rdb.jdbc.Db;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An insert statement
 *
 * @author rob
 */
public class Insert implements WriteStatement {

    private Table table;
    private final List<InsertExpression> insertExpressions = new ArrayList<>();
    private final List<Column> returnColumns = new ArrayList<>();

    public Insert into(Table table) {
        this.table = table;
        return this;
    }

    public Insert values(InsertExpression... insertExpressions) {
        this.insertExpressions.addAll(Arrays.asList(insertExpressions));
        return this;
    }

    public Insert returning(Column... returnColumns) {
        this.returnColumns.addAll(Arrays.asList(returnColumns));
        return this;
    }

    @Override
    public PreparedStatement prepare(Db db) {
        return buildStatement().prepare(db);
    }

    private StmtBuilder buildStatement() {
        StmtBuilder part1 = new StmtBuilder(StmtType.INSERT);
        part1.append("INSERT INTO ");
        table.addToStmt(part1);
        part1.append(" (");
        StmtBuilder part2 = new StmtBuilder(StmtType.INSERT);
        for (int i = 0; i < insertExpressions.size(); i++) {
            if (i > 0) {
                part1.append(", ");
                part2.append(", ");
            }
            InsertExpression expr = insertExpressions.get(i);
            expr.addNameComponenet(part1);
            expr.addValueComponenet(part2);
        }
        part1.append(" ) ");
        part1.append(" VALUES ( ");
        part1.appendAll(part2);
        part1.append(" ) ");
        if (!returnColumns.isEmpty()) {
            part1.append(" RETURNING ");
            for (int i = 0; i < returnColumns.size(); i++) {
                if (i > 0) {
                    part1.append(", ");
                }
                returnColumns.get(i).addToStmt(part1);
            }
        }
        return part1;
    }

    @Override
    public String toString() {
        return buildStatement().toString();
    }
}
