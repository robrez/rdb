package com.rdb.core;

import com.rdb.core.statement.Like;

/**
 *
 * @author rob
 */
public class StringColumn extends BasicColumn<String> {

    public StringColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(this, value);
    }
}
