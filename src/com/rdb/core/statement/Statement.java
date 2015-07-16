package com.rdb.core.statement;

import com.rdb.jdbc.Db;
import java.sql.PreparedStatement;

/**
 *
 * @author rob
 */
public interface Statement {

    public PreparedStatement prepare(Db db);
}
