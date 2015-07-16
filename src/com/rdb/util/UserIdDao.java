package com.rdb.util;

import com.rdb.core.statement.Delete;
import com.rdb.core.statement.Insert;
import com.rdb.core.statement.Select;
import com.rdb.core.statement.Update;
import com.rdb.jdbc.Db;
import com.rdb.jdbc.ResultSetWrapper;
import com.rdb.jdbc.ValueSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rdb generated dao
 */
public class UserIdDao {

    private final Db db;

    public UserIdDao(Db db) {
        this.db = db;
    }

    public UserId load(Integer useridUid) {
        Select select = new Select();
        select.columns(RUserId.userId.allColumns);
        select.from(RUserId.userId);
        select.where(RUserId.userId.useridUid.equalTo(useridUid));
        ResultSetWrapper rs = db.executeQuery(select);
        UserId entity = null;
        if (rs.next()) {
            entity = load(rs);
        }
        rs.close();
        return entity;
    }

    public List<UserId> loadAll(Select select) {
        ResultSetWrapper rs = db.executeQuery(select);
        List<UserId> list = new ArrayList<>();
        while (rs.next()) {
            list.add(load(rs));
        }
        rs.close();
        return list;
    }

    public static UserId load(ValueSource vs) {
        UserId entity = new UserId();
        entity.setUseridEmail(vs.getString(RUserId.userId.useridEmail.name(), null));
        entity.setUseridName(vs.getString(RUserId.userId.useridName.name(), null));
        entity.setUseridUid(vs.getInt(RUserId.userId.useridUid.name(), null));
        return entity;
    }

    public void save(UserId entity) {
        int rowsAffected = update(entity);
        if (rowsAffected == 0) {
            insert(entity);
        }
    }

    public void insert(UserId entity) {
        Insert insert = new Insert();
        insert.into(RUserId.userId);
        insert.values(
                RUserId.userId.useridEmail.setTo(entity.getUseridEmail()),
                RUserId.userId.useridName.setTo(entity.getUseridName()));
        if (entity.getUseridUid() != null) {
            insert.values(RUserId.userId.useridUid.setTo(entity.getUseridUid()));
        }
        insert.returning(RUserId.userId.useridUid);
        ResultSetWrapper results = db.executeUpdateWithResults(insert);
        if (results.next()) {
            entity.setUseridUid(results.getInt(RUserId.userId.useridUid.name()));
        }
        results.close();
    }

    public Integer update(UserId entity) {
        Update update = new Update();
        update.table(RUserId.userId);
        update.set(
                RUserId.userId.useridEmail.setTo(entity.getUseridEmail()),
                RUserId.userId.useridName.setTo(entity.getUseridName()));
        update.where(RUserId.userId.useridUid.equalTo(entity.getUseridUid()));
        return db.executeUpdate(update);
    }

    public Integer delete(UserId entity) {
        Delete delete = new Delete();
        delete.from(RUserId.userId);
        delete.where(RUserId.userId.useridUid.equalTo(entity.getUseridUid()));
        return db.executeUpdate(delete);
    }
}
