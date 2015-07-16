package com.rdb.jdbc;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rob
 */
public interface ValueSource {

    public Object getObject(String column);

    public Object getObject(String column, Object defaultValue);

    public String getString(String column);

    public String getString(String column, String defaultValue);

    public Boolean getBoolean(String column);

    public Boolean getBoolean(String column, Boolean defaultValue);

    public Integer getInt(String column);

    public Integer getInt(String column, Integer defaultValue);

    public Double getDouble(String column);

    public Double getDouble(String column, Double defaultValue);

    public BigDecimal getBigDecimal(String column);

    public BigDecimal getBigDecimal(String column, BigDecimal defaultValue);

    public Date getDate(String column);

    public Date getDate(String column, Date defaultValue);

    public Time getTime(String column);

    public Time getTime(String column, Time defaultValue);

    public Date getTime(String column, Date defaultValue);

    public Timestamp getTimestamp(String column);

    public Timestamp getTimestamp(String column, Timestamp defaultValue);

    public Date getTimestamp(String column, Date defaultValue);

    public Array getArray(String column);

    public <T> List<T> getList(String column, Class<T> cls);

    public <T> List<T> getList(String column, Class<T> cls, List<T> defaultValue);

    public List<String> getColumnNames();
}
