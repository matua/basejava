package com.matuageorge.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecuterType<S> {
    S execute(PreparedStatement statement) throws SQLException;
}
