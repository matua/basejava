package com.matuageorge.webapp.sql;

import com.matuageorge.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            return transactionCommit(executor, conn);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private <T> T transactionCommit(SqlTransaction<T> executor, Connection conn) throws SQLException {
        try {
            conn.setAutoCommit(false);
            T res = executor.execute(conn);
            conn.commit();
            return res;
        } catch (SQLException e) {
            conn.rollback();
            throw ExceptionUtil.convertException(e);
        }
    }
}