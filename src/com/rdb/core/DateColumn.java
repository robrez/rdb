package com.rdb.core;

import com.rdb.core.statement.Like;
import java.util.Date;

/**
 *
 * @author rob
 */
public class DateColumn extends BasicColumn<Date> {

    public DateColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
