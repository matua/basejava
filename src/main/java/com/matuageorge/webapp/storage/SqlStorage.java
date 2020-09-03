package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import com.matuageorge.webapp.sql.ConnectionFactory;
import com.matuageorge.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.processSql("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        helper.processSql("UPDATE resume SET full_name = ? WHERE uuid = ?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.processSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)", statement -> {
            try {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            } catch (SQLException e) {
                throw new ExistStorageException(e.getSQLState());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.processSql("SELECT * FROM resume r WHERE r.uuid = ?", statement -> {
            try {
                statement.setString(1, uuid);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    return new Resume(uuid, rs.getString("full_name"));
                }
            } catch (Exception e) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public void delete(String uuid) {
        helper.processSql("DELETE FROM resume r WHERE r.uuid = ?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.processSql("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid ASC", statement -> {
            List<Resume> sortedList = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    sortedList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sortedList;
        });
    }


    @Override
    public int size() {
        return helper.processSql("SELECT count(*) FROM resume", statement -> {
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    throw new StorageException("Sql request error");
                }
                return Integer.parseInt(rs.getString("count"));
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        });
    }
}