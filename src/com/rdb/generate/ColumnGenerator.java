package com.rdb.generate;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rob
 */
public class ColumnGenerator {

    private final ColumnDbMetaData cmd;

    ColumnGenerator(ColumnDbMetaData cmd) {
        this.cmd = cmd;
    }

    ColumnDbMetaData getCmd() {
        return cmd;
    }

    String generateSetter() {
        StringBuilder method = new StringBuilder();
        String name = getEntityVariableName();
        String type = getJavaType();
        method.append("    ").append("public void ");
        method.append(getSetterName()).append("(").append(type).append(" ").append(name).append(") {\n");
        method.append("        ").append("this.").append(name).append(" = ").append(name).append(";\n");
        method.append("    ").append("}\n");
        return method.toString();
    }

    String getSetterName() {
        String title = Generator.getCamelCase(cmd.getColName(), true);
        return "set" + title;
    }

    String generateGetter() {
        StringBuilder method = new StringBuilder();
        String name = getEntityVariableName();
        String type = getJavaType();
        method.append("    ").append("public ").append(type).append(" ");
        method.append(getGetterName()).append("() {\n");
        method.append("        ").append("return ").append(name).append(";\n");
        method.append("    ").append("}\n");
        return method.toString();
    }

    String getGetterName() {
        String title = Generator.getCamelCase(cmd.getColName(), true);
        return "get" + title;
    }

    String getDaoMemberName() {
        return Generator.getCamelCase(cmd.getColName(), false);
    }

    String getEntityVariableName() {
        return Generator.getCamelCase(cmd.getColName(), false);
    }

    String generateEntityMember() {
        String name = getEntityVariableName();
        StringBuilder member = new StringBuilder();
        member.append("    ").append("private ");
        member.append(getJavaType()).append(" ").append(name).append(";");
        return member.toString();
    }

    String getJavaType() {
        return getJavaType(cmd.getDataType());
    }

    private String getJavaType(Integer dataType) {
        if (dataType == null) {
            return "Object";
        }
        switch (dataType) {
            case Types.BIGINT:
                return "BigDecimal";
            case Types.SMALLINT:
            case Types.INTEGER:
                return "Integer";
            case Types.VARCHAR:
                return "String";
            case Types.DATE:
                return "Date";
            case Types.TIME:
                return "Date"; //sql.Time
            case Types.TIMESTAMP:
                return "Date"; //sql.Timestamp
            case Types.NUMERIC:
            case Types.DECIMAL:
                if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                        && (cmd.getColSize() != null && cmd.getColSize() < 10)) {
                    //Use an integer if possible
                    return "Integer";
                }
                if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                        && (cmd.getColSize() != null && cmd.getColSize() < 5)) {
                    //Use an integer if possible
                    return "Short";
                }
                return "BigDecimal";
            case Types.DOUBLE:
                return "Double";
            case Types.BOOLEAN:
            case Types.BIT:
                return "Boolean";
            case Types.ARRAY:
                Integer innerType = getInnerType();
                if (innerType == null) {
                    return "List";
                }
                String javatype = getJavaType(innerType);
                if (javatype == null || javatype.length() < 1) {
                    return "List";
                } else {
                    return "List<" + javatype + ">";
                }
        }
        return "Object";
    }

    //For an array, try to determine its elements' type
    private Integer getInnerType() {
        String typename = cmd.getTypeName().toLowerCase();
        if (typename.contains("varchar")
                || typename.contains("text")
                || typename.contains("character varying")) {
            return Types.VARCHAR;
        }
        if (typename.contains("bigint")
                || typename.contains("int8")) {
            return Types.BIGINT;
        }
        if (typename.contains("int")
                || typename.contains("integer")
                || typename.contains("int2")
                || typename.contains("int4")) {
            return Types.INTEGER;
        }
        if (typename.contains("numeric")
                || typename.contains("decimal")) {
            if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                    && (cmd.getColSize() != null && cmd.getColSize() < 10)) {
                //Use an integer if possible
                return Types.INTEGER;
            }
            if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                    && (cmd.getColSize() != null && cmd.getColSize() < 5)) {
                //Use an integer if possible
                return Types.SMALLINT;
            }
            return Types.NUMERIC;
        }
        if (typename.contains("timestamp")
                || typename.contains("timestamptz")) {
            return Types.TIMESTAMP;
        }
        if (typename.contains("time")) {
            return Types.TIME;
        }
        if (typename.contains("date")) {
            return Types.DATE;
        }
        return null;
    }

    String getMetaMember() {
        return Generator.getCamelCase(cmd.getColName(), false);
    }

    String getVsMethod() {
        switch (cmd.getDataType()) {
            case Types.SMALLINT:
            case Types.INTEGER:
                return "getInt";
            case Types.VARCHAR:
                return "getString";
            case Types.DATE:
                return "getDate";
            case Types.TIME:
                return "getTime";
            case Types.TIMESTAMP:
                return "getTimestamp";
            case Types.NUMERIC:
            case Types.DECIMAL:
                if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                        && (cmd.getColSize() != null && cmd.getColSize() < 10)) {
                    //Use an integer if possible
                    return "getInt";
                } else {
                    return "getBigDecimal";
                }
            case Types.BIGINT:
                return "getBigDecimal";
            case Types.DOUBLE:
                return "getDouble";
            case Types.BIT:
            case Types.BOOLEAN:
                return "getBoolean";
            case Types.ARRAY:
                return "getList";
        }
        return "getObject";
    }

    String generateVsMethod(String colName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getVsMethod()).append("(");
        sb.append(colName);
        if (getCmd().getDataType() == Types.ARRAY) {
            String innerType = getJavaType(getInnerType());
            sb.append(", ");
            sb.append(innerType).append(".class");
        }
        sb.append(", null))");
        return sb.toString();
    }

    String generateMetaMember() {
        StringBuilder column = new StringBuilder();
        String type = getMetaColumnType();
        column.append("    ").append("public final ");
        column.append(type).append(" ").append(getEntityVariableName()).append(" = ");
        column.append("new ").append(type).append("(\"").append(cmd.getColName()).append("\");");
        return column.toString();
    }

    String getMetaColumnType() {
        switch (cmd.getDataType()) {
            case Types.ARRAY:
                Integer innerType = getInnerType();
                if (innerType == null) {
                    return "ArrayColumn";
                }
                String javatype = getJavaType(innerType);
                if (javatype == null || javatype.length() < 1) {
                    return "ArrayColumn";
                } else {
                    return "ArrayColumn<" + javatype + ">";
                }
            default:
                return getJavaType() + "Column";
        }
    }

    List<String> getJavaTypeImports() {
        return getJavaTypeImports(cmd.getDataType());
    }

    private List<String> getJavaTypeImports(Integer dataType) {
        if (dataType == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> imports = new ArrayList<>();
        switch (dataType) {
            case Types.BIGINT:
                imports.add("java.math.BigDecimal");
                break;
            case Types.SMALLINT:
            case Types.INTEGER:
                imports.add("java.lang.Integer");
                break;
            case Types.VARCHAR:
                imports.add("java.lang.String");
                break;
            case Types.DATE:
                imports.add("java.util.Date");
                break;
            case Types.TIME:
                imports.add("java.util.Date"); //sql.Time
                break;
            case Types.TIMESTAMP:
                imports.add("java.util.Date"); //sql.Timestamp
                break;
            case Types.NUMERIC:
            case Types.DECIMAL:
                if ((cmd.getDecDigits() == null || cmd.getDecDigits() == 0)
                        && (cmd.getColSize() != null && cmd.getColSize() < 10)) {
                    //Use an integer if possible
                    imports.add("java.lang.Integer");
                } else {
                    imports.add("java.math.BigDecimal");
                }
                break;
            case Types.DOUBLE:
                imports.add("java.lang.Double");
                break;
            case Types.BOOLEAN:
            case Types.BIT:
                imports.add("java.lang.Boolean");
                break;
            case Types.ARRAY:
                imports.add("java.util.List");
                Integer innerType = getInnerType();
                imports.addAll(getJavaTypeImports(innerType));
                break;
        }
        return imports;
    }

}
