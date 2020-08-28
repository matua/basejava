package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import com.matuageorge.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private Connection conn;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        try {
            conn = connectionFactory.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try (PreparedStatement ps = executeSql("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private PreparedStatement executeSql(String s) throws SQLException {
        return conn.prepareStatement(s);
    }

    @Override
    public void update(Resume resume) {
        try (PreparedStatement ps = executeSql("UPDATE resume SET full_name=? WHERE uuid=?")) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        try (PreparedStatement ps = executeSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        try (PreparedStatement ps = executeSql("SELECT * FROM resume r WHERE r.uuid = ?")) {
            ps.setString(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new Resume(uuid, rs.getString("full_name"));
            }
        } catch (Exception e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        try (PreparedStatement ps = executeSql("DELETE FROM resume r WHERE r.uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>();
        try (PreparedStatement ps = executeSql("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid ASC")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sortedList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortedList;
    }


    @Override
    public int size() {
        try (PreparedStatement ps = executeSql("SELECT count(*) FROM resume")) {
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new StorageException("Sql request error");
                }
                return Integer.parseInt(rs.getString("count"));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}