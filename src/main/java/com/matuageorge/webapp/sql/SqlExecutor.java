package com.matuageorge.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T> {
    T execute(PreparedStatement st) throws SQLException;
}