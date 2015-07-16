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
 * generate a dao TODO this code is quite nasty... need to encapsulate
 * functionality common among all generators
 *
 * @author rob
 */
public class DaoGenerator {

    private final TableDbMetaData tableMetaData;
    private final List<ColumnGenerator> columns = new ArrayList<>();
    private final List<ColumnGenerator> keyColumns = new ArrayList<>();
    private final GeneratorProps props;

    public DaoGenerator(TableDbMetaData tableMetaData, GeneratorProps props) {
        this.tableMetaData = tableMetaData;
        for (ColumnDbMetaData cmd : tableMetaData.getColumns()) {
            columns.add(new ColumnGenerator(cmd));
        }
        for (ColumnDbMetaData cmd : tableMetaData.getKeyColumns()) {
            keyColumns.add(new ColumnGenerator(cmd));
        }
        this.props = props;
    }

    public void generate() {
        String daoName = getDaoName();
        StringBuilder builder = new StringBuilder();
        //package
        builder.append("package ").append(props.getDaoPackage()).append(";\n");
        builder.append("\n");
        //imports
        List<String> imports = new ArrayList<>();
        imports.add("com.rdb.jdbc.Db");
        imports.add("com.rdb.jdbc.ResultSetWrapper");
        imports.add("com.rdb.jdbc.ValueSource");
        imports.add("com.rdb.core.statement.Delete");
        imports.add("com.rdb.core.statement.Insert");
        imports.add("com.rdb.core.statement.Select");
        imports.add("com.rdb.core.statement.Update");
        imports.add("java.util.List");
        imports.add("java.util.ArrayList");
        for (ColumnGenerator cg : columns) {
            if (cg.getCmd().getDataType() == Types.ARRAY) {
                List<String> temp = cg.getJavaTypeImports();
                for (String imp : temp) {
                    if (!imp.startsWith("java.lang.") && !imports.contains(imp)) {
                        imports.add(imp);
                    }
                }
            }
        }
        Collections.sort(imports);
        for (String imp : imports) {
            builder.append("import ").append(imp).append(";\n");
        }

        builder.append("\n");
        builder.append("/**\n");
        builder.append(" *\n");
        builder.append(" * @author rdb generated dao\n");
        builder.append(" */\n");
        builder.append("public class ");
        builder.append(daoName);
        builder.append(" {\n\n");

        //Output members
        builder.append("    private final Db db;\n");
        builder.append("\n");
        //Output constructor
        builder.append("    public ").append(daoName).append("(Db db) {\n");
        builder.append("        this.db = db;\n");
        builder.append("    }\n");

        builder.append("\n");
        builder.append(generateLoad());
        builder.append("\n");
        builder.append(generateLoadAll());
        builder.append("\n");
        builder.append(generateStaticLoad());
        builder.append("\n");
        builder.append(generateSave());
        builder.append("\n");
        builder.append(generateInsert());
        builder.append("\n");
        builder.append(generateUpdate());
        builder.append("\n");
        builder.append(generateDelete());

        builder.append("}");
        writeToFile(builder.toString());
    }

    private String getOutputDirectory() {
        String daoPackage = props.getDaoPackage();
        String outputDir = props.getOutputDirectory();
        String fullFilePath = outputDir + "/" + daoPackage.replace(".", "/") + "/";
        return fullFilePath;
    }

    private String getOutputFile() {
        String daoName = getDaoName();
        String fullFilePath = getOutputDirectory() + daoName + ".java";
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

    private String getMetaName() {
        return "R" + getCamelCase(tableMetaData.getName(), true);
    }

    private String getDefaultMetaInstanceName() {
        return getMetaName() + "." + getCamelCase(tableMetaData.getName(), false);
    }

    private String getDaoName() {
        return getCamelCase(tableMetaData.getName(), true) + "Dao";
    }

    private String getEntityName() {
        return getCamelCase(tableMetaData.getName(), true);
    }

    private String generateLoad() {
        StringBuilder sb = new StringBuilder();
        sb.append("    public ").append(getEntityName()).append(" load");
        sb.append("(");
        for (int i = 0; i < keyColumns.size(); i++) {
            ColumnGenerator cg = keyColumns.get(i);
            String varName = cg.getEntityVariableName();
            String type = cg.getJavaType();
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(type).append(" ").append(varName);
        }
        sb.append(") {\n");
        sb.append("        Select select = new Select();\n");
        sb.append("        select.columns(")
                .append(getDefaultMetaInstanceName())
                .append(".allColumns);\n");
        sb.append("        select.from(")
                .append(getDefaultMetaInstanceName()).append(");\n");
        sb.append("        select.where(");
        for (int i = 0; i < keyColumns.size(); i++) {
            ColumnGenerator cg = keyColumns.get(i);
            String varName = cg.getEntityVariableName();
            if (i > 0) {
                sb.append(",\n            ");
            }
            sb.append(getColumnMetaMember(cg));
            sb.append(".equalTo(").append(varName).append(")");
        }
        sb.append(");\n");
        sb.append("        ResultSetWrapper rs = db.executeQuery(select);\n");
        sb.append("        ").append(getEntityName()).append(" entity = null;\n");
        sb.append("        if (rs.next()) {\n");
        sb.append("            entity = load(rs);\n");
        sb.append("        }\n");
        sb.append("        rs.close();\n");
        sb.append("        return entity;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateStaticLoad() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public static ").append(entityName).append(" load");
        sb.append("(ValueSource vs) {\n");
        sb.append("        ").append(entityName);
        sb.append(" entity = new ").append(entityName).append("();\n");
        for (ColumnGenerator cg : columns) {
            String setterName = cg.getSetterName();
            sb.append("        entity.").append(setterName);
            sb.append("(vs.");
            String colName = getColumnMetaMember(cg) + ".name()";
            sb.append(cg.generateVsMethod(colName)).append(";\n");
        }
        sb.append("        return entity;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateSave() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public void save");
        sb.append("(").append(entityName).append(" ").append("entity").append(") {\n");
        sb.append("        int rowsAffected = update(entity);\n");
        sb.append("        if (rowsAffected == 0) {\n");
        sb.append("            insert(entity);\n");
        sb.append("        }\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateUpdate() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public Integer update");
        sb.append("(").append(entityName).append(" ").append("entity").append(") {\n");
        sb.append("        Update update = new Update();\n");
        sb.append("        update.table(").append(getDefaultMetaInstanceName()).append(");\n");
        sb.append("        update.set(\n");
        int addedCount = 0;
        for (ColumnGenerator cg : columns) {
            if (cg.getCmd().isPrimaryKey()) {
                continue;
            }
            if (addedCount > 0) {
                sb.append(",\n");
            }
            sb.append("                ");
            addedCount++;
            String getterName = cg.getGetterName() + "()";
            sb.append(getColumnMetaMember(cg));
            sb.append(".setTo(");
            sb.append("entity").append(".").append(getterName);
            sb.append(")");
        }
        sb.append(");\n");
        sb.append("        update.where(");
        for (int i = 0; i < keyColumns.size(); i++) {
            ColumnGenerator cg = keyColumns.get(i);
            String getterName = cg.getGetterName() + "()";
            if (i > 0) {
                sb.append(",\n                ");
            }
            sb.append(getColumnMetaMember(cg));
            sb.append(".equalTo(");
            sb.append("entity").append(".").append(getterName);
            sb.append(")");
        }
        sb.append(");\n");
        sb.append("        return db.executeUpdate(update);\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateInsert() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public void insert");
        sb.append("(").append(entityName).append(" ").append("entity").append(") {\n");
        sb.append("        Insert insert = new Insert();\n");
        sb.append("        insert.into(").append(getDefaultMetaInstanceName()).append(");\n");
        sb.append("        insert.values(\n");
        int addedCount = 0;
        for (ColumnGenerator cg : columns) {
            if (cg.getCmd().isPrimaryKey()) {
                continue;
            }
            if (addedCount > 0) {
                sb.append(",\n");
            }
            sb.append("                ");
            addedCount++;
            String getterName = "entity." + cg.getGetterName() + "()";
            sb.append(getColumnMetaMember(cg));
            sb.append(".setTo(").append(getterName).append(")");
        }
        sb.append(");\n");
        for (ColumnGenerator cg : keyColumns) {
            String getterName = "entity." + cg.getGetterName() + "()";
            sb.append("        if (").append(getterName).append(" != null) {\n");
            sb.append("            insert.values(");
            sb.append(getColumnMetaMember(cg));
            sb.append(".setTo(").append(getterName).append("));\n");
            sb.append("        }\n");
        }
        for (ColumnGenerator cg : keyColumns) {
            sb.append("        insert.returning(")
                    .append(getColumnMetaMember(cg))
                    .append(");\n");
        }
        sb.append("        ResultSetWrapper results = db.executeUpdateWithResults(insert);\n");
        sb.append("        if (results.next()) {\n");
        for (ColumnGenerator cg : keyColumns) {
            sb.append("            entity.");
            sb.append(cg.getSetterName()).append("(");
            sb.append("results.").append(cg.getVsMethod()).append("(");
            sb.append(getColumnMetaMember(cg));
            sb.append(".name()));\n");
        }
        sb.append("        }\n");
        sb.append("        results.close();\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String getColumnMetaMember(ColumnGenerator cmd) {
        return getDefaultMetaInstanceName() + "." + cmd.getMetaMember();
    }

    private String generateDelete() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public Integer delete");
        sb.append("(").append(entityName).append(" ").append("entity").append(") {\n");
        sb.append("        Delete delete = new Delete();\n");
        sb.append("        delete.from(").append(getDefaultMetaInstanceName()).append(");\n");
        sb.append("        delete.where(");
        for (int i = 0; i < keyColumns.size(); i++) {
            ColumnGenerator cg = keyColumns.get(i);
            String getterName = cg.getGetterName() + "()";
            if (i > 0) {
                sb.append(",\n                ");
            }
            sb.append(getColumnMetaMember(cg));
            sb.append(".equalTo(");
            sb.append("entity").append(".").append(getterName);
            sb.append(")");
        }
        sb.append(");\n");
        sb.append("        return db.executeUpdate(delete);\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateLoadAll() {
        String entityName = getEntityName();
        StringBuilder sb = new StringBuilder();
        sb.append("    public List<").append(entityName).append("> loadAll(Select select) {\n");
        sb.append("        ResultSetWrapper rs = db.executeQuery(select);\n");
        sb.append("        List<").append(entityName).append("> list = new ArrayList<>();\n");
        sb.append("        while (rs.next()) {\n");
        sb.append("            list.add(load(rs));\n");
        sb.append("        }\n");
        sb.append("        rs.close();\n");
        sb.append("        return list;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String getCamelCase(String input, boolean capitalizeFirstLetter) {
        StringBuilder sb = new StringBuilder();
        boolean needCap = capitalizeFirstLetter;
        for (char c : input.toCharArray()) {
            if (needCap && Character.isLetter(c)) {
                sb.append(Character.toString(c).toUpperCase());
                needCap = false;
                continue;
            }
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                needCap = true;
                //throw it out
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
