package com.rdb.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO this code is quite nasty... need to encapsulate functionality common
 * among all generators
 *
 * @author rob
 */
public class MetaGenerator {

    private final TableDbMetaData tableMetaData;
    private final List<ColumnGenerator> columnMetaData = new ArrayList<>();
    private final GeneratorProps props;

    public MetaGenerator(TableDbMetaData tableMetaData, GeneratorProps props) {
        this.tableMetaData = tableMetaData;
        for (ColumnDbMetaData cmd : tableMetaData.getColumns()) {
            columnMetaData.add(new ColumnGenerator(cmd));
        }
        this.props = props;
    }

    public void generate() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(props.getMetaPackage()).append(";\n");
        builder.append("\n");
        builder.append(generateImports());
        builder.append("\n");
        builder.append("/**\n");
        builder.append(" *\n");
        builder.append(" * @author rdb generated meta class\n");
        builder.append(" */\n");
        builder.append("public class ");
        builder.append(getMetaName());
        builder.append(" extends Table");
        builder.append(" {\n\n");

        //Output table
        builder.append(generateTable()).append("\n");

        //Default instance
        builder.append("    public static final ")
                .append(getMetaName())
                .append(" ").append(getInstanceName())
                .append(" = new ").append(getMetaName()).append("();\n");

        for (ColumnGenerator cg : columnMetaData) {
            builder.append(cg.generateMetaMember()).append("\n");
        }
        //Output columns array
        builder.append("    public final Column[] allColumns = new Column[]{\n");
        for (int i = 0; i < columnMetaData.size(); i++) {
            if (i > 0) {
                builder.append(",\n");
            }
            builder.append("        ");
            builder.append(columnMetaData.get(i).getEntityVariableName());
        }
        builder.append("\n    };\n\n");

        //Default constructor
        builder.append("    private ").append(getMetaName()).append("() {\n");
        builder.append("        super(TABLE, null);\n");
        builder.append("        setColumnTableAlias(new TableAlias(getTableMetaData().getName()));\n");
        builder.append("    }\n\n");

        //Alias constructor
        builder.append("    private ").append(getMetaName()).append("(TableAlias tableAlias) {\n");
        builder.append("        super(TABLE, tableAlias);\n");
        builder.append("        setColumnTableAlias(tableAlias);\n");
        builder.append("    }\n\n");

        builder.append("    public static ").append(getMetaName()).append(" as(String tableAlias) {\n");
        builder.append("        ")
                .append(getMetaName())
                .append(" t = new ")
                .append(getMetaName())
                .append("( new TableAlias(tableAlias));\n");
        builder.append("        return t;\n");
        builder.append("    }\n\n");

        builder.append("    private void setColumnTableAlias(TableAlias alias) {\n");
        builder.append("        for (Column c : allColumns) {\n");
        builder.append("            c.setTableAlias(alias);\n");
        builder.append("        }\n");
        builder.append("    }\n");

        builder.append("}");
        writeToFile(builder.toString());
    }

    private String getMetaName() {
        return "R" + getCamelCase(tableMetaData.getName(), true);
    }

    private String getInstanceName() {
        return getCamelCase(tableMetaData.getName(), false);
    }

    private String getOutputDirectory() {
        String metaPackage = props.getMetaPackage();
        String outputDir = props.getOutputDirectory();
        String fullFilePath = outputDir + "/" + metaPackage.replace(".", "/") + "/";
        return fullFilePath;
    }

    private String getOutputFile() {
        String metaName = getMetaName();
        String fullFilePath = getOutputDirectory() + metaName + ".java";
        return fullFilePath;
    }

    private void writeToFile(String content) {
        try {
            File dir = new File(getOutputDirectory());
            dir.mkdirs();
            File file = new File(getOutputFile());
            file.createNewFile();
            file.setReadable(true);
            file.setWritable(true);

            FileWriter fw = new FileWriter(file);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateTable() {
        StringBuilder member = new StringBuilder();
        member.append("    ").append("public static final TableMetaData TABLE = new TableMetaData(");
        if (tableMetaData.getCatalog() == null) {
            member.append("null");
        } else {
            member.append('"').append(tableMetaData.getCatalog()).append('"');
        }
        if (tableMetaData.getSchema() == null) {
            member.append(", null");
        } else {
            member.append(", ").append('"').append(tableMetaData.getSchema()).append('"');
        }
        member.append(", \"").append(tableMetaData.getName()).append('"');
        member.append(");");
        return member.toString();
    }

    private String generateImports() {
        List<String> importList = new ArrayList<>();
        importList.add("com.rdb.core.Column");
        importList.add("com.rdb.core.Table");
        importList.add("com.rdb.core.TableAlias");
        importList.add("com.rdb.core.TableMetaData");
        for (ColumnGenerator cmd : columnMetaData) {
            String imp = "com.rdb.core." + cmd.getMetaColumnType();
            if (cmd.getCmd().getDataType() == Types.ARRAY) {
                int idx = imp.indexOf("<");
                if (idx != -1) {
                    imp = imp.substring(0, idx);
                }
            }
            if (!importList.contains(imp)) {
                importList.add(imp);
            }
            List<String> temp = cmd.getJavaTypeImports();
            for (String inc : temp) {
                if (!inc.startsWith("java.lang.") && !importList.contains(inc)) {
                    importList.add(inc);
                }
            }
        }
        Collections.sort(importList);
        StringBuilder sb = new StringBuilder();
        for (String imp : importList) {
            sb.append("import ")
                    .append(imp).append(";\n");
        }
        return sb.toString();
    }

    private static String getCamelCase(String input, boolean capitalizeFirstLetter) {
        return Generator.getCamelCase(input, capitalizeFirstLetter);
    }
}
