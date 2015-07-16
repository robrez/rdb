package com.rdb.jdbc;

import com.rdb.core.statement.ReadStatement;
import com.rdb.core.statement.WriteStatement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rob
 */
public class Db {

    private Connection connection = null;

    public Db(Connection conn) {
        this.connection = conn;
    }

    public static Db connect(String host, String user, String pw) {
        Connection conn = newConnection(host, user, pw);
        return new Db(conn);
    }

    private static Connection newConnection(String host, String user, String pw) {
        Exception throwable = null;
        try {
            Class.forName("org.postgresql.Driver");
            //Class.forName("org.apache.derby.jdbc.AutoloadedDriver");
            return DriverManager.getConnection(host, user, pw);
        } catch (ClassNotFoundException | SQLException ex) {
            throwable = ex;
        }
        if (throwable != null) {
            throw new RuntimeException(throwable);
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * If you start a transaction, finish it
     *
     * @return
     */
    public boolean isInTransaction() {
        try {
            return !connection.getAutoCommit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Begin a transaction or start one if needed
     *
     * @return true if you started the transaction and you need to 'commit' it
     * or 'rollback' as needed
     */
    public boolean startTransaction() {
        if (!isInTransaction()) {
            beginTransaction();
            return true;
        }
        return false;
    }

    private void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseMetaData getDatabaseMetaData() {
        try {
            return getConnection().getMetaData();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResultSetWrapper executeQuery(ReadStatement readStatement) {
        try {
            PreparedStatement stmt = readStatement.prepare(this);
            ResultSet rs = stmt.executeQuery();
            ResultSetWrapper rsw = new ResultSetWrapper(rs, stmt);
            return rsw;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Integer executeUpdate(WriteStatement writeStatement) {
        try {
            Integer results;
            try (PreparedStatement stmt = writeStatement.prepare(this)) {
                results = stmt.executeUpdate();
            }
            return results;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResultSetWrapper executeUpdateWithResults(WriteStatement writeStatement) {
        try {
            PreparedStatement stmt = writeStatement.prepare(this);
            ResultSet rs = stmt.executeQuery();
            ResultSetWrapper rsw = new ResultSetWrapper(rs, stmt);
            return rsw;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
