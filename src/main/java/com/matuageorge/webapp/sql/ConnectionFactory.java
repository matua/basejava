package com.matuageorge.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
