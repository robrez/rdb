package com.rdb.generate;

import com.rdb.util.Props;

/**
 *
 * @author rob
 */
public class GeneratorProps {

    private static final String DBHOST = "host";
    private static final String DBUSER = "user";
    private static final String DBPW = "pw";
    private static final String OUTPUTDIRECTORY = "output-directory";
    private static final String DAOPACKAGE = "dao-package";
    private static final String ENTITYPACKAGE = "entity-package";
    private static final String METAPACKAGE = "meta-package";
    private static final String APACKAGE = "package";
    private final Props props;

    public GeneratorProps(Props props) {
        this.props = props;
    }

    private static String usage() {
        StringBuilder sb = new StringBuilder();
        sb.append(DBHOST).append("=").append("<db host>");
        sb.append("\n");
        sb.append(DBUSER).append("=").append("<db user>");
        sb.append("\n");
        sb.append(DBPW).append("=").append("<db pw>");
        sb.append("\n");
        sb.append(OUTPUTDIRECTORY).append("=").append("<directory for file output>");
        sb.append("\n");
        sb.append(APACKAGE).append("=").append("<package for generated classes>");
        sb.append("\n");
        sb.append(DAOPACKAGE).append("=").append("<package for generated dao classes>");
        sb.append("\n");
        sb.append(METAPACKAGE).append("=").append("<package for generated meta/query classes>");
        sb.append("\n");
        sb.append(ENTITYPACKAGE).append("=").append("<package for generated entity classes>");
        return sb.toString();
    }

    public final String[] getTables() {
        return props.getStringArray("tables");
    }

    public final String getOutputDirectory() {
        return props.getString(OUTPUTDIRECTORY, ".");
    }

    public final String getDaoPackage() {
        return props.getString(DAOPACKAGE, getPackage());
    }

    public final String getEntityPackage() {
        return props.getString(ENTITYPACKAGE, getPackage());
    }

    public final String getMetaPackage() {
        return props.getString(METAPACKAGE, getPackage());
    }

    public final String getPackage() {
        return props.getString(APACKAGE);
    }

    public final String getDbHost() {
        return props.getString(DBHOST);
    }

    public final String getDbUser() {
        return props.getString(DBUSER);
    }

    public final String getDbPw() {
        return props.getString(DBPW);
    }

    public boolean isConfigured() throws RuntimeException {
        if (getDbHost() == null) {
            throw new RuntimeException("No db host provided.\n" + usage());
        }
        if (getDbUser() == null) {
            throw new RuntimeException("No db user provided.\n" + usage());
        }
        if (getTables().length == 0) {
            throw new RuntimeException("No tables provided.\n" + usage());
        }
        if (getMetaPackage() == null || getMetaPackage().length() == 0) {
            throw new RuntimeException("Meta package not provided.\n" + usage());
        }
        if (getEntityPackage() == null || getEntityPackage().length() == 0) {
            throw new RuntimeException("Entity package not provided.\n" + usage());
        }
        if (getDaoPackage() == null || getDaoPackage().length() == 0) {
            throw new RuntimeException("Dao package not provided.\n" + usage());
        }
        return true;
    }
}
