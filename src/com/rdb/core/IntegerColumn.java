package com.rdb.core;

import com.rdb.core.statement.Like;

/**
 *
 * @author rob
 */
public class IntegerColumn extends BasicColumn<Integer> {

    public IntegerColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
