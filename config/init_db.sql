DROP TABLE IF EXISTS resume, contact, section;


CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

CREATE TABLE section
(
    id          SERIAL PRIMARY KEY,
    type        character varying NOT NULL,
    value       character varying NOT NULL,
    resume_uuid character(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, type);