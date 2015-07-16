package com.rdb.core;

import com.rdb.core.statement.Like;

/**
 *
 * @author rob
 */
public class DoubleColumn extends BasicColumn<Double> {

    public DoubleColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
