-- !Ups
CREATE TABLE "User"
(
    "idx"        BIGINT PRIMARY KEY NOT NULL,
    "id"         VARCHAR(12)        NOT NULL,
    "password"   VARCHAR(20)        NOT NULL
);

ALTER TABLE "User"
    ADD CONSTRAINT check_user_id_length CHECK (length("id") >= 6);

ALTER TABLE "User"
    ADD CONSTRAINT check_user_password_length CHECK (length("password") >= 8);

CREATE TABLE "Post"
(
    "idx"      BIGINT PRIMARY KEY NOT NULL,
    "owner"    BIGINT             NOT NULL,
    "contents" VARCHAR(255)       NOT NULL
);

CREATE TABLE "Sequence"
(
    "id"    VARCHAR(20) PRIMARY KEY NOT NULL,
    "value" NUMERIC                 NOT NULL
);

INSERT INTO "Sequence" ("id", "value")
VALUES ('User', 1);
INSERT INTO "Sequence" ("id", "value")
VALUES ('Post', 1);

-- !Downs
ALTER TABLE "User"
    DROP CONSTRAINT check_user_id_length;

ALTER TABLE "User"
    DROP CONSTRAINT check_user_password_length;

DROP TABLE "User";
DROP TABLE "Post";
DROP TABLE "Sequence";
