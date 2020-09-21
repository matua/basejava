// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com

package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.*;
import com.matuageorge.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private Resume resume;
    private Resume resume1;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        sqlHelper.execute(("SELECT * FROM resume WHERE uuid = ?"), ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume = new Resume(uuid, rs.getString("full_name"));
            return null;
        });


        sqlHelper.execute(("SELECT * FROM contact WHERE resume_uuid = ?"), ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return null;
        });

        Map<SectionType, List<String>> listItems = new LinkedHashMap<>();

        sqlHelper.execute(("SELECT * FROM section WHERE resume_uuid = ?"), ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sectionTypeClass = rs.getString("type_class");
                String sectionType = rs.getString("type");
                String value = rs.getString("value");
                SectionType sectionTypeKey = SectionType.valueOf(sectionType);
                switch (sectionTypeClass) {
                    case "TextSection":
                        resume.addSection(sectionTypeKey, new TextSection(value));
                        break;
                    case "ListSection":
                        List<String> listItem = listItems.get(sectionTypeKey);
                        if (listItem == null) {
                            listItems.put(sectionTypeKey, new ArrayList<>(Collections.singletonList(value)));
                        } else {
                            listItems.put(sectionTypeKey, Stream.concat(listItem.stream(), Stream.of(value))
                                    .collect(Collectors.toList()));
                        }
                }
            }
            return null;
        });
        listItems.forEach((sectionType, sectionItems) -> resume.addSection(sectionType, new ListSection(sectionItems)));
        return resume;
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
            sqlHelper.execute("DELETE FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, r.getUuid());
                ps.execute();
                return null;
            });
            sqlHelper.execute("DELETE FROM section WHERE resume_uuid = ?", ps -> {
                ps.setString(1, r.getUuid());
                ps.execute();
                return null;
            });
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
        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.execute("" +
                "SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");

                resumes.computeIfAbsent(uuid, k -> {
                    try {
                        return resume1 = new Resume(uuid, rs.getString("full_name"));
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    return null;
                });
            }
            return null;
        });

        sqlHelper.execute("" +
                "SELECT * FROM contact", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resume_uuid = rs.getString("resume_uuid");
                String type = rs.getString("type");
                String value = rs.getString("value");
                resumes.forEach((uuid, resume) -> {
                    if (uuid.equals(resume_uuid)) {
                        resume.addContact(ContactType.valueOf(type), value);
                    }
                });
            }
            return null;
        });
        Map<SectionType, List<String>> listItems = new LinkedHashMap<>();

        sqlHelper.execute("" +
                "SELECT * FROM section", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sectionTypeClass = rs.getString("type_class");
                String sectionType = rs.getString("type");
                String value = rs.getString("value");
                SectionType sectionTypeKey = SectionType.valueOf(sectionType);
                switch (sectionTypeClass) {
                    case "TextSection":
                        resume1.addSection(sectionTypeKey, new TextSection(value));
                        break;
                    case "ListSection":
                        List<String> listItem = listItems.get(sectionTypeKey);
                        if (listItem == null) {
                            listItems.put(sectionTypeKey, new ArrayList<>(Collections.singletonList(value)));
                        } else {
                            listItems.put(sectionTypeKey, Stream.concat(listItem.stream(), Stream.of(value))
                                    .collect(Collectors.toList()));
                        }
                }
            }
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
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
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid, type_class) VALUES (?,?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                AbstractSection section = e.getValue();
                if (section instanceof ListSection) {
                    for (String item : ((ListSection) section).getItems()) {
                        ps.setString(1, e.getKey().name());
                        ps.setString(2, item);
                        ps.setString(3, r.getUuid());
                        ps.setString(4, section.getClass().getSimpleName());
                        ps.addBatch();
                    }
                } else {
                    ps.setString(1, e.getKey().name());
                    ps.setString(2, section.toString());
                    ps.setString(3, r.getUuid());
                    ps.setString(4, section.getClass().getSimpleName());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }
}
