CREATE TABLE issue244_exact (
   id serial8,
   exact_field text
);
CREATE INDEX idxissue244 ON issue244_exact USING zombodb (zdb('issue244_exact', ctid), zdb(issue244_exact)) WITH (url='localhost:9200/');
INSERT INTO issue244_exact(exact_field) VALUES ('\');
INSERT INTO issue244_exact(exact_field) VALUES ('foo');

SELECT * FROM issue244_exact WHERE zdb('issue244_exact', ctid) ==> '((exact_field:"\\") or (exact_field:"foo")) or ((exact_field:''\\'') or (exact_field:''foo''))' order by id;

DROP TABLE issue244_exact;

CREATE TABLE issue244_phrase (
   id serial8,
   phrase_field phrase
);
CREATE INDEX idxissue244 ON issue244_phrase USING zombodb (zdb('issue244_phrase', ctid), zdb(issue244_phrase)) WITH (url='localhost:9200/');
INSERT INTO issue244_phrase(phrase_field) VALUES ('\');
INSERT INTO issue244_phrase(phrase_field) VALUES ('foo');

SELECT * FROM issue244_phrase WHERE zdb('issue244_phrase', ctid) ==> '((phrase_field:"\\") or (phrase_field:"foo")) or ((phrase_field:''\\'') or (phrase_field:''foo''))' order by id;

DROP TABLE issue244_phrase;