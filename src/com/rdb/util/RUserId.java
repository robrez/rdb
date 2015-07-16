package com.rdb.util;

import com.rdb.core.Column;
import com.rdb.core.IntegerColumn;
import com.rdb.core.StringColumn;
import com.rdb.core.Table;
import com.rdb.core.TableAlias;
import com.rdb.core.TableMetaData;

/**
 *
 * @author rdb generated meta class
 */
public class RUserId extends Table {

    public static final TableMetaData TABLE = new TableMetaData(null, "public", "user_id");
    public static final RUserId userId = new RUserId();
    public final StringColumn useridEmail = new StringColumn("userid_email");
    public final StringColumn useridName = new StringColumn("userid_name");
    public final IntegerColumn useridUid = new IntegerColumn("userid_uid");
    public final Column[] allColumns = new Column[]{
        useridEmail,
        useridName,
        useridUid
    };

    private RUserId() {
        super(TABLE, null);
        setColumnTableAlias(new TableAlias(getTableMetaData().getName()));
    }

    private RUserId(TableAlias tableAlias) {
        super(TABLE, tableAlias);
        setColumnTableAlias(tableAlias);
    }

    public static RUserId as(String tableAlias) {
        RUserId t = new RUserId( new TableAlias(tableAlias));
        return t;
    }

    private void setColumnTableAlias(TableAlias alias) {
        for (Column c : allColumns) {
            c.setTableAlias(alias);
        }
    }
}