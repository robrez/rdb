package com.rdb.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate entity class for given table. TODO PACKAGE NAME AND FIELD PREFIX.
 * TODO this code is quite nasty... need to encapsulate functionality common
 * among all generators
 *
 * @author rob
 */
public class EntityGenerator {

    private final TableDbMetaData tableMetaData;
    private final List<ColumnGenerator> columnMetaData = new ArrayList<>();
    private final GeneratorProps props;

    public EntityGenerator(TableDbMetaData tableMetaData, GeneratorProps props) {
        this.tableMetaData = tableMetaData;
        for (ColumnDbMetaData cmd : tableMetaData.getColumns()) {
            columnMetaData.add(new ColumnGenerator(cmd));
        }
        this.props = props;
    }

    public void generate() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(props.getEntityPackage()).append(";\n");
        builder.append("\n");
        List<String> imports = new ArrayList<>();
        imports.add("java.io.Serializable");
        for (ColumnGenerator cg : columnMetaData) {
            List<String> temp = cg.getJavaTypeImports();
            for (String imp : temp) {
                if (!imp.startsWith("java.lang.") && !imports.contains(imp)) {
                    imports.add(imp);
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
        builder.append(" * @author rdb generated entity\n");
        builder.append(" */\n");
        builder.append("public class ");
        builder.append(getEntityName());
        builder.append(" implements Serializable {\n\n");

        for (ColumnGenerator cg : columnMetaData) {
            builder.append(cg.generateEntityMember()).append("\n");
        }

        for (ColumnGenerator cg : columnMetaData) {
            builder.append("\n");
            builder.append(cg.generateSetter());
            builder.append("\n");
            builder.append(cg.generateGetter());
        }

        builder.append("}");
        writeToFile(builder.toString());
    }

    private String getBaseName() {
        return getCamelCase(tableMetaData.getName(), true);
    }

    private String getEntityName() {
        return getBaseName();
    }

    private String getOutputDirectory() {
        String entityPackage = props.getEntityPackage();
        String outputDir = props.getOutputDirectory();
        String fullFilePath = outputDir + "/" + entityPackage.replace(".", "/") + "/";
        return fullFilePath;
    }

    private String getOutputFile() {
        String entityName = getEntityName();
        String fullFilePath = getOutputDirectory() + entityName + ".java";
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

    private static String getCamelCase(String input, boolean capitalizeFirstLetter) {
        return Generator.getCamelCase(input, capitalizeFirstLetter);
    }
}
