package com.rdb.generate;

import com.rdb.jdbc.Db;
import com.rdb.util.Props;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rob
 */
public class Generator {

    private final List<String> tables = new ArrayList<>();
    private final Db db;
    private final GeneratorProps props;

    public static void main(String[] args) {
        Props props = Props.parseCmdLine(args);
        GeneratorProps gp = new GeneratorProps(props);
        gp.isConfigured();
        Db db = Db.connect(gp.getDbHost(), gp.getDbUser(), gp.getDbPw());
        Generator generator = new Generator(db, gp);
        generator.generate();
        db.close();
    }

    public Generator(Db db, GeneratorProps props) {
        this.db = db;
        this.props = props;
        for (String table : props.getTables()) {
            if (!this.tables.contains(table)) {
                this.tables.add(table);
            }
        }
    }

    public void generate() {
        for (String table : tables) {
            TableMetaDataDao dao = new TableMetaDataDao(db);
            TableDbMetaData tmd = dao.load(db, table);
            if (!tmd.getColumns().isEmpty()) {
                DaoGenerator daoGen = new DaoGenerator(tmd, props);
                daoGen.generate();
                EntityGenerator entityGen = new EntityGenerator(tmd, props);
                entityGen.generate();
                MetaGenerator metaGen = new MetaGenerator(tmd, props);
                metaGen.generate();
            }
        }
    }

    static String getCamelCase(String input, boolean capitalizeFirstLetter) {
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
