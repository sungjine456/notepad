-- !Ups
CREATE TABLE "User" (
    "idx"       BIGINT        PRIMARY KEY NOT NULL,
    "id"        VARCHAR(255)               NOT NULL,
    "password" VARCHAR(255)               NOT NULL
);

CREATE TABLE "Post" (
    "idx"       BIGINT              PRIMARY KEY NOT NULL,
    "owner"     BIGINT              NOT NULL,
    "contents"  VARCHAR(255)        NOT NULL
);

CREATE TABLE "Sequence" (
  "id"    VARCHAR(20) PRIMARY KEY NOT NULL,
  "value" NUMERIC                  NOT NULL
);

INSERT INTO "Sequence" ("id", "value") VALUES ('User', 1);
INSERT INTO "Sequence" ("id", "value") VALUES ('Post', 1);

-- !Downs
DROP TABLE "User";
DROP TABLE "Post";
DROP TABLE "Sequence";
