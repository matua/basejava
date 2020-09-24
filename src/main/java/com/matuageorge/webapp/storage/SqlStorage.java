// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com

package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.*;
import com.matuageorge.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private Resume resume;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String sectionType = rs.getString("type");
                    String value = rs.getString("value");
                    SectionType sectionTypeKey = SectionType.valueOf(sectionType);

                    switch (sectionTypeKey) {
                        case PERSONAL:
                        case OBJECTIVE:
                            resume.addSection(sectionTypeKey, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            resume.addSection(sectionTypeKey, new ListSection(Arrays.asList(value.split(System.lineSeparator()))));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + sectionType);
                    }
                }
                return resume;
            }
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(r, conn);
                    insertSections(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        sqlHelper.execute("" +
                "SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");

                result.add(get(uuid));
            }
            return null;
        });
        return result;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume r, Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            r.getSections().forEach((sectionType, section) -> {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        try {
                            ps.setString(1, sectionType.toString());
                            ps.setString(2, ((TextSection) section).getContent());
                            ps.setString(3, r.getUuid());
                            ps.addBatch();
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder listSectionItems = new StringBuilder();
                        ((ListSection) section).getItems().forEach(item -> listSectionItems.append(item).append(System.lineSeparator()));
                        try {
                            ps.setString(1, sectionType.toString());
                            ps.setString(2, listSectionItems.toString().trim());
                            ps.setString(3, r.getUuid());
                            ps.addBatch();
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
            });
            ps.executeBatch();
        }
    }
}
