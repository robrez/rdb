package com.rdb.core.statement;

/**
 *
 * @author rob
 */
public interface InsertExpression extends Expression {

    public void addNameComponenet(StmtBuilder sb);

    public void addValueComponenet(StmtBuilder sb);
}
