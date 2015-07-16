package com.rdb.core.statement;

import com.rdb.core.Column;

/**
 *
 * @author rob
 */
public class Sort implements StmtComponent {

    private final Column column;
    private String direction;
    private String nulls;

    public Sort(Column column) {
        this.column = column;
    }

    public Sort asc() {
        //this.direction = "ASCENDING";  //default is asc
        return this;
    }

    public Sort desc() {
        this.direction = "DESC";
        return this;
    }

    public Sort nullsFirst() {
        this.nulls = "FIRST";
        return this;
    }

    public Sort nullsLast() {
        this.nulls = "LAST";
        return this;
    }

    @Override
    public void addToStmt(StmtBuilder sb) {
        column.addToStmt(sb);
        if (direction != null) {
            sb.append(" ").append(direction);
        }
        if (nulls != null) {
            sb.append(" NULLS ").append(nulls);
        }
    }

}
