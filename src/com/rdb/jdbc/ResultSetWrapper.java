package com.rdb.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rob
 */
public class ResultSetWrapper implements ValueSource {

    private Integer count = null;
    private ResultSet rs = null;
    private Statement stmt = null;

    public ResultSetWrapper(ResultSet rs, Statement stmt) {
        this.rs = rs;
        this.stmt = stmt;
    }

    public boolean next() {
        try {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void close() {
        count = null;
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException ex) {
            }
        }
    }

    public void setPosition(int position) {
        try {
            if (position > 0) {
                rs.absolute(position);
            } else {
                rs.beforeFirst();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getCount() {
        if (count != null) {
            return count;
        }
        try {
            int position = rs.getRow();
            rs.last();
            count = rs.getRow();
            if (position > 0) {
                rs.absolute(position);
            } else {
                rs.beforeFirst();
            }
            return count;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getString(String column) {
        try {
            return rs.getString(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Boolean getBoolean(String column) {
        try {
            boolean value = rs.getBoolean(column);
            if (rs.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Boolean getBoolean(String column, Boolean defaultValue) {
        try {
            Boolean value = getBoolean(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Integer getInt(String column) {
        try {
            int value = rs.getInt(column);
            if (rs.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Double getDouble(String column) {
        try {
            Double value = rs.getDouble(column);
            if (rs.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Date getDate(String column) {
        try {
            return rs.getDate(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Time getTime(String column) {
        try {
            return rs.getTime(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp(String column) {
        return null;
    }

    @Override
    public Object getObject(String column) {
        try {
            return rs.getObject(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResultSetMetaData getResultSetMetaData() {
        try {
            return rs.getMetaData();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getColumnNames() {
        try {
            ResultSetMetaData rsmd = getResultSetMetaData();
            int colCount = rsmd.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= colCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            return columnNames;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Object getObject(String column, Object defaultValue) {
        try {
            Object value = getObject(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public String getString(String column, String defaultValue) {
        try {
            String value = getString(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Integer getInt(String column, Integer defaultValue) {
        try {
            Integer value = getInt(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Double getDouble(String column, Double defaultValue) {
        try {
            Double value = getDouble(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Date getDate(String column, Date defaultValue) {
        try {
            Date value = getDate(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Time getTime(String column, Time defaultValue) {
        try {
            Time value = getTime(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Date getTime(String column, Date defaultValue) {
        try {
            Time value = getTime(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Timestamp getTimestamp(String column, Timestamp defaultValue) {
        try {
            Timestamp value = getTimestamp(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public Date getTimestamp(String column, Date defaultValue) {
        try {
            Date value = getTimestamp(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public BigDecimal getBigDecimal(String column, BigDecimal defaultValue) {
        try {
            BigDecimal value = getBigDecimal(column);
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    public BigDecimal getBigDecimal(String column) {
        try {
            return rs.getBigDecimal(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Array getArray(String column) {
        try {
            return rs.getArray(column);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> List<T> getList(String column, Class<T> cls) {
        try {
            Array value = rs.getArray(column);
            if (value == null) {
                return null;
            }
            T[] values = (T[]) value.getArray();
            if (values == null) {
                return null;
            }
            return Arrays.asList(values);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> List<T> getList(String column, Class<T> cls, List<T> defaultValue) {
        try {
            Array value = rs.getArray(column);
            if (value == null) {
                return defaultValue;
            }
            T[] values = (T[]) value.getArray();
            if (values == null) {
                return defaultValue;
            }
            return Arrays.asList(values);
        } catch (SQLException ex) {
            return defaultValue;
        }
    }
}
