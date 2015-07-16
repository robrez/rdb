package com.rdb.core;

import com.rdb.core.statement.Like;
import java.math.BigDecimal;

/**
 *
 * @author rob
 */
public class BigDecimalColumn extends BasicColumn<BigDecimal> {

    public BigDecimalColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
