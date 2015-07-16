package com.rdb.util;

import com.rdb.jdbc.ValueSource;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Time;
import java.util.*;

/**
 *
 * @author rob
 */
public class Props implements ValueSource {

    private final Map<String, Object> props = new HashMap<>();

    /**
     * Parse cmdLine args supplied as k-v pairs, each separated by "=". ie:
     * key=value
     *
     * @param args
     * @return
     */
    public static Props parseCmdLine(String[] args) {
        Props p = new Props();
        for (String arg : args) {
            int idx = arg.indexOf('=');
            if (idx != -1) {
                p.setString(arg.substring(0, idx), arg.substring(idx + 1));
            }
        }
        return p;
    }

    public void setObject(String property, Object value) {
        props.put(property, value);
    }

    public void setString(String property, String value) {
        props.put(property, value);
    }

    public void setBoolean(String property, Boolean value) {
        props.put(property, value);
    }

    public void setInt(String property, Integer value) {
        props.put(property, value);
    }

    @Override
    public Object getObject(String property) {
        return props.get(property);
    }

    @Override
    public Object getObject(String property, Object defaultValue) {
        Object prop = getObject(property);
        if (prop == null) {
            return defaultValue;
        } else {
            return prop;
        }
    }

    @Override
    public String getString(String property) {
        Object prop = getObject(property);
        if (prop == null) {
            return null;
        } else {
            return prop.toString();
        }
    }

    @Override
    public String getString(String property, String defaultValue) {
        String prop = getString(property);
        if (prop == null) {
            return defaultValue;
        } else {
            return prop;
        }
    }

    @Override
    public Boolean getBoolean(String property) {
        Object prop = getObject(property);
        if (prop == null) {
            return null;
        } else {
            return Boolean.parseBoolean(prop.toString());
        }
    }

    @Override
    public Boolean getBoolean(String property, Boolean defaultValue) {
        Boolean prop = getBoolean(property);
        if (prop == null) {
            return defaultValue;
        } else {
            return prop;
        }
    }

    @Override
    public Integer getInt(String property) {
        Object prop = getObject(property);
        if (prop == null) {
            return null;
        } else {
            return Integer.parseInt(prop.toString());
        }
    }

    @Override
    public Integer getInt(String property, Integer defaultValue) {
        Integer prop = getInt(property);
        if (prop == null) {
            return defaultValue;
        } else {
            return prop;
        }
    }

    @Override
    public Double getDouble(String property) {
        Object prop = getObject(property);
        if (prop == null) {
            return null;
        } else {
            return Double.parseDouble(prop.toString());
        }
    }

    @Override
    public Double getDouble(String property, Double defaultValue) {
        Double prop = getDouble(property);
        if (prop == null) {
            return defaultValue;
        } else {
            return prop;
        }
    }

    @Override
    public Date getDate(String property) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getDate(String property, Date defaultValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getStringArray(String property) {
        String propStr = getString(property);
        if (propStr == null) {
            return new String[0];
        } else {
            return propStr.split(",");
        }
    }

    @Override
    public List<String> getColumnNames() {
        return new ArrayList<>(props.keySet());
    }

    @Override
    public BigDecimal getBigDecimal(String column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getBigDecimal(String column, BigDecimal defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Time getTime(String column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Time getTime(String column, Time defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getTime(String column, Date defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.sql.Timestamp getTimestamp(String column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.sql.Timestamp getTimestamp(String column, java.sql.Timestamp defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getTimestamp(String column, Date defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Array getArray(String column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> List<T> getList(String column, Class<T> cls) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> List<T> getList(String column, Class<T> cls, List<T> defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
