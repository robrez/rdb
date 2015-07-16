package com.rdb.core;

import com.rdb.core.statement.Like;
import java.security.Timestamp;

/**
 *
 * @author rob
 */
public class TimestampColumn extends BasicColumn<Timestamp> {

    public TimestampColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
