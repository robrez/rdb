package com.rdb.core;

import com.rdb.core.statement.Like;

/**
 *
 * @author rob
 */
public class BooleanColumn extends BasicColumn<Boolean> {

    public BooleanColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
