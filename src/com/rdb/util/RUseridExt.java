package com.rdb.util;

import com.rdb.core.ArrayColumn;
import com.rdb.core.BigDecimalColumn;
import com.rdb.core.BooleanColumn;
import com.rdb.core.Column;
import com.rdb.core.DateColumn;
import com.rdb.core.IntegerColumn;
import com.rdb.core.Table;
import com.rdb.core.TableAlias;
import com.rdb.core.TableMetaData;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rdb generated meta class
 */
public class RUseridExt extends Table {

    public static final TableMetaData TABLE = new TableMetaData(null, "public", "userid_ext");
    public static final RUseridExt useridExt = new RUseridExt();
    public final BooleanColumn userextActive = new BooleanColumn("userext_active");
    public final BigDecimalColumn userextBigdec = new BigDecimalColumn("userext_bigdec");
    public final BigDecimalColumn userextBigint = new BigDecimalColumn("userext_bigint");
    public final IntegerColumn userextBignum = new IntegerColumn("userext_bignum");
    public final DateColumn userextDt = new DateColumn("userext_dt");
    public final ArrayColumn<Date> userextDtarr = new ArrayColumn<Date>("userext_dtarr");
    public final ArrayColumn<Integer> userextIntarray = new ArrayColumn<Integer>("userext_intarray");
    public final ArrayColumn<BigDecimal> userextNumarray = new ArrayColumn<BigDecimal>("userext_numarray");
    public final IntegerColumn userextSmallnum = new IntegerColumn("userext_smallnum");
    public final ArrayColumn<String> userextStrarray = new ArrayColumn<String>("userext_strarray");
    public final DateColumn userextTime = new DateColumn("userext_time");
    public final ArrayColumn<Date> userextTimearr = new ArrayColumn<Date>("userext_timearr");
    public final DateColumn userextTs = new DateColumn("userext_ts");
    public final ArrayColumn<Date> userextTsarr = new ArrayColumn<Date>("userext_tsarr");
    public final IntegerColumn userextUserUid = new IntegerColumn("userext_user_uid");
    public final Column[] allColumns = new Column[]{
        userextActive,
        userextBigdec,
        userextBigint,
        userextBignum,
        userextDt,
        userextDtarr,
        userextIntarray,
        userextNumarray,
        userextSmallnum,
        userextStrarray,
        userextTime,
        userextTimearr,
        userextTs,
        userextTsarr,
        userextUserUid
    };

    private RUseridExt() {
        super(TABLE, null);
        setColumnTableAlias(new TableAlias(getTableMetaData().getName()));
    }

    private RUseridExt(TableAlias tableAlias) {
        super(TABLE, tableAlias);
        setColumnTableAlias(tableAlias);
    }

    public static RUseridExt as(String tableAlias) {
        RUseridExt t = new RUseridExt( new TableAlias(tableAlias));
        return t;
    }

    private void setColumnTableAlias(TableAlias alias) {
        for (Column c : allColumns) {
            c.setTableAlias(alias);
        }
    }
}