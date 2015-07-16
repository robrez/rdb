package com.rdb.core.statement;

import com.rdb.jdbc.Db;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rob
 */
public class StmtBuilder {

    private final StringBuilder sql = new StringBuilder();
    private final List parameters = new ArrayList();
    private final StmtType stmtType;
    private StmtPart currentStmtPart;

    public StmtBuilder(StmtType stmtType) {
        this.stmtType = stmtType;
    }

    public StmtType getStmtType() {
        return stmtType;
    }

    StmtPart getCurrentStmtPart() {
        return currentStmtPart;
    }

    void setCurrentStmtPart(StmtPart currentStmtComponentType) {
        this.currentStmtPart = currentStmtComponentType;
    }

    /**
     * Creators of StmtBuilder objects should update which portion of the
     * statement is currently being built. Specific contributors will behave
     * differently depending on which part of the statement they are being used
     * in. For example, a Column typically contributes an alias if one exists
     * "select foo as bar from foobar" however, the same column should not
     * contribute its alias when being used in a filter. we don't want: "where
     * foo as bar = 'value'"
     *
     * @return
     * @see Select
     */
    public boolean shouldIncludeAlias() {
        if (getStmtType() != StmtType.SELECT) {
            return false;
        }
        if (getCurrentStmtPart() == null) {
            return true;
        }
        switch (getCurrentStmtPart()) {
            case FILTER:
            case SORT:
            case GROUP:
                return false;
            case COLUMN:
            case TABLE:
            default:
                return true;
        }
    }

    /**
     * Only select statements should ever include the table alias / prefix... We
     * don't want to produce "insert into foo as footable (footable.bar) values
     * ..."
     *
     * @return
     */
    public boolean shouldIncludeTableAlias() {
        return getStmtType() == StmtType.SELECT;
    }

    public StmtBuilder addParams(Object... values) {
        parameters.addAll(Arrays.asList(values));
        return this;
    }

    public StmtBuilder addParam(Object value) {
        parameters.add(value);
        return this;
    }

    public StmtBuilder append(String sql) {
        this.sql.append(sql);
        return this;
    }

    public StmtBuilder appendAll(StmtBuilder anotherStatementBuilder) {
        this.sql.append(anotherStatementBuilder.sql);
        this.parameters.addAll(anotherStatementBuilder.parameters);
        return this;
    }

    public PreparedStatement prepare(Db db) {
        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(sql.toString());
            ParameterMetaData pmd = stmt.getParameterMetaData();
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                Integer pType = pmd.getParameterType(i + 1);
                if (param == null) {
                    stmt.setNull(i + 1, pType);
                } else {
                    stmt.setObject(i + 1, param, pType);
                }
            }

            return stmt;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        int paramIdx = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '?' && paramIdx < parameters.size()) {
                Object p = parameters.get(paramIdx);
                if (p != null) {
                    sb.append("'").append(p.toString()).append("'");
                } else {
                    sb.append("NULL");
                }
                paramIdx++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
