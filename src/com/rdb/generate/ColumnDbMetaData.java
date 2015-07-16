package com.rdb.generate;

import java.sql.Types;

/**
 *
 * @author rob
 */
public class ColumnDbMetaData implements Comparable<ColumnDbMetaData> {

    String colName = null;
    Integer dataType = null;
    String typeName = null;
    Integer colSize = null;
    Integer decDigits = null;
    String autoIncrement = null;
    Boolean isPrimaryKey = false;

    public ColumnDbMetaData(String colName) {
        this.colName = colName;
    }

    String getAutoIncrement() {
        return autoIncrement;
    }

    Boolean isAutoIncrement() {
        if (autoIncrement == null) {
            return false;
        }
        return Boolean.parseBoolean(autoIncrement.toLowerCase())
                || autoIncrement.equalsIgnoreCase("yes");
    }

    void setPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    Boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    void setAutoIncrement(String autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    String getColName() {
        return colName;
    }

    void setColName(String colName) {
        this.colName = colName;
    }

    Integer getColSize() {
        return colSize;
    }

    void setColSize(Integer colSize) {
        this.colSize = colSize;
    }

    Integer getDataType() {
        return dataType;
    }

    void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    Integer getDecDigits() {
        return decDigits;
    }

    void setDecDigits(Integer decDigits) {
        this.decDigits = decDigits;
    }

    String getTypeName() {
        return typeName;
    }

    void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int compareTo(ColumnDbMetaData o) {
        if (o == null) {
            return -1;
        }
        return getColName().compareTo(o.getColName());
    }

    @Override
    public String toString() {
        return getColName();
    }

}
