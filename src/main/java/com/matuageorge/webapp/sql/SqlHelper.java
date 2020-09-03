package com.matuageorge.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <S> S processSql(String sql, ExecuterType<S> et) {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            return et.execute(statement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}
