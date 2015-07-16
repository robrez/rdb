package com.rdb.core;

import com.rdb.core.statement.Like;
import java.math.BigInteger;

/**
 *
 * @author rob
 */
public class BigIntegerColumn extends BasicColumn<BigInteger> {

    public BigIntegerColumn(String name) {
        super(name);
    }

    public Like like(String value) {
        return Like.like(new StringCast(this), value);
    }
}
