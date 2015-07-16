package com.rdb.util;

import com.rdb.core.statement.Delete;
import com.rdb.core.statement.Insert;
import com.rdb.core.statement.Select;
import com.rdb.core.statement.Update;
import com.rdb.jdbc.Db;
import com.rdb.jdbc.ResultSetWrapper;
import com.rdb.jdbc.ValueSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rdb generated dao
 */
public class UseridExtDao {

    private final Db db;

    public UseridExtDao(Db db) {
        this.db = db;
    }

    public UseridExt load(Integer userextUserUid) {
        Select select = new Select();
        select.columns(RUseridExt.useridExt.allColumns);
        select.from(RUseridExt.useridExt);
        select.where(RUseridExt.useridExt.userextUserUid.equalTo(userextUserUid));
        ResultSetWrapper rs = db.executeQuery(select);
        UseridExt entity = null;
        if (rs.next()) {
            entity = load(rs);
        }
        rs.close();
        return entity;
    }

    public List<UseridExt> loadAll(Select select) {
        ResultSetWrapper rs = db.executeQuery(select);
        List<UseridExt> list = new ArrayList<>();
        while (rs.next()) {
            list.add(load(rs));
        }
        rs.close();
        return list;
    }

    public static UseridExt load(ValueSource vs) {
        UseridExt entity = new UseridExt();
        entity.setUserextActive(vs.getBoolean(RUseridExt.useridExt.userextActive.name(), null));
        entity.setUserextBigdec(vs.getBigDecimal(RUseridExt.useridExt.userextBigdec.name(), null));
        entity.setUserextBigint(vs.getBigDecimal(RUseridExt.useridExt.userextBigint.name(), null));
        entity.setUserextBignum(vs.getInt(RUseridExt.useridExt.userextBignum.name(), null));
        entity.setUserextDt(vs.getDate(RUseridExt.useridExt.userextDt.name(), null));
        entity.setUserextDtarr(vs.getList(RUseridExt.useridExt.userextDtarr.name(), Date.class, null));
        entity.setUserextIntarray(vs.getList(RUseridExt.useridExt.userextIntarray.name(), Integer.class, null));
        entity.setUserextNumarray(vs.getList(RUseridExt.useridExt.userextNumarray.name(), BigDecimal.class, null));
        entity.setUserextSmallnum(vs.getInt(RUseridExt.useridExt.userextSmallnum.name(), null));
        entity.setUserextStrarray(vs.getList(RUseridExt.useridExt.userextStrarray.name(), String.class, null));
        entity.setUserextTime(vs.getTime(RUseridExt.useridExt.userextTime.name(), null));
        entity.setUserextTimearr(vs.getList(RUseridExt.useridExt.userextTimearr.name(), Date.class, null));
        entity.setUserextTs(vs.getTimestamp(RUseridExt.useridExt.userextTs.name(), null));
        entity.setUserextTsarr(vs.getList(RUseridExt.useridExt.userextTsarr.name(), Date.class, null));
        entity.setUserextUserUid(vs.getInt(RUseridExt.useridExt.userextUserUid.name(), null));
        return entity;
    }

    public void save(UseridExt entity) {
        int rowsAffected = update(entity);
        if (rowsAffected == 0) {
            insert(entity);
        }
    }

    public void insert(UseridExt entity) {
        Insert insert = new Insert();
        insert.into(RUseridExt.useridExt);
        insert.values(
                RUseridExt.useridExt.userextActive.setTo(entity.getUserextActive()),
                RUseridExt.useridExt.userextBigdec.setTo(entity.getUserextBigdec()),
                RUseridExt.useridExt.userextBigint.setTo(entity.getUserextBigint()),
                RUseridExt.useridExt.userextBignum.setTo(entity.getUserextBignum()),
                RUseridExt.useridExt.userextDt.setTo(entity.getUserextDt()),
                RUseridExt.useridExt.userextDtarr.setTo(entity.getUserextDtarr()),
                RUseridExt.useridExt.userextIntarray.setTo(entity.getUserextIntarray()),
                RUseridExt.useridExt.userextNumarray.setTo(entity.getUserextNumarray()),
                RUseridExt.useridExt.userextSmallnum.setTo(entity.getUserextSmallnum()),
                RUseridExt.useridExt.userextStrarray.setTo(entity.getUserextStrarray()),
                RUseridExt.useridExt.userextTime.setTo(entity.getUserextTime()),
                RUseridExt.useridExt.userextTimearr.setTo(entity.getUserextTimearr()),
                RUseridExt.useridExt.userextTs.setTo(entity.getUserextTs()),
                RUseridExt.useridExt.userextTsarr.setTo(entity.getUserextTsarr()));
        if (entity.getUserextUserUid() != null) {
            insert.values(RUseridExt.useridExt.userextUserUid.setTo(entity.getUserextUserUid()));
        }
        insert.returning(RUseridExt.useridExt.userextUserUid);
        ResultSetWrapper results = db.executeUpdateWithResults(insert);
        if (results.next()) {
            entity.setUserextUserUid(results.getInt(RUseridExt.useridExt.userextUserUid.name()));
        }
        results.close();
    }

    public Integer update(UseridExt entity) {
        Update update = new Update();
        update.table(RUseridExt.useridExt);
        update.set(
                RUseridExt.useridExt.userextActive.setTo(entity.getUserextActive()),
                RUseridExt.useridExt.userextBigdec.setTo(entity.getUserextBigdec()),
                RUseridExt.useridExt.userextBigint.setTo(entity.getUserextBigint()),
                RUseridExt.useridExt.userextBignum.setTo(entity.getUserextBignum()),
                RUseridExt.useridExt.userextDt.setTo(entity.getUserextDt()),
                RUseridExt.useridExt.userextDtarr.setTo(entity.getUserextDtarr()),
                RUseridExt.useridExt.userextIntarray.setTo(entity.getUserextIntarray()),
                RUseridExt.useridExt.userextNumarray.setTo(entity.getUserextNumarray()),
                RUseridExt.useridExt.userextSmallnum.setTo(entity.getUserextSmallnum()),
                RUseridExt.useridExt.userextStrarray.setTo(entity.getUserextStrarray()),
                RUseridExt.useridExt.userextTime.setTo(entity.getUserextTime()),
                RUseridExt.useridExt.userextTimearr.setTo(entity.getUserextTimearr()),
                RUseridExt.useridExt.userextTs.setTo(entity.getUserextTs()),
                RUseridExt.useridExt.userextTsarr.setTo(entity.getUserextTsarr()));
        update.where(RUseridExt.useridExt.userextUserUid.equalTo(entity.getUserextUserUid()));
        return db.executeUpdate(update);
    }

    public Integer delete(UseridExt entity) {
        Delete delete = new Delete();
        delete.from(RUseridExt.useridExt);
        delete.where(RUseridExt.useridExt.userextUserUid.equalTo(entity.getUserextUserUid()));
        return db.executeUpdate(delete);
    }
}
