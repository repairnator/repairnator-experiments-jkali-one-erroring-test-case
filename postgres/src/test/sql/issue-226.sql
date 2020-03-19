CREATE TABLE issue226 (
  id SERIAL8 NOT NULL PRIMARY KEY
);
CREATE INDEX idxissue226
  ON issue226 USING zombodb (zdb('issue226', ctid), zdb(issue226)) WITH (url='localhost:9200/');

INSERT INTO issue226 (id) VALUES (default);

BEGIN;
INSERT INTO issue226 (id) VALUES (default);
ABORT;

VACUUM issue226;

SELECT
  (SELECT count(*) FROM issue226)                                                  AS issue226,
  (SELECT count(*) FROM issue226 WHERE zdb('issue226', ctid) ==> 'id:*')           AS issue226_all,
  (SELECT count(*) FROM issue226 WHERE zdb('issue226', ctid) ==> 'id:null')        AS issue226_null,
  (SELECT zdb_estimate_count('issue226', 'id:*'))    AS issue226_estimate_all,
  (SELECT zdb_estimate_count('issue226', 'id:null')) AS issue226_estimate_null;

DROP TABLE issue226 CASCADE;