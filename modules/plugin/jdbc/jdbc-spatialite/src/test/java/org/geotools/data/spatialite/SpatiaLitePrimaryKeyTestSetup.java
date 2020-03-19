/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.data.spatialite;

import org.geotools.jdbc.JDBCPrimaryKeyTestSetup;

/** @source $URL$ */
public class SpatiaLitePrimaryKeyTestSetup extends JDBCPrimaryKeyTestSetup {

    protected SpatiaLitePrimaryKeyTestSetup() {
        super(new SpatiaLiteTestSetup());
    }

    @Override
    protected void createAutoGeneratedPrimaryKeyTable() throws Exception {
        run("CREATE TABLE auto( fid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        run("SELECT AddGeometryColumn('auto','geom',4326,'POINT',2)");
        run("INSERT INTO auto VALUES (1, 'one', GeomFromText('POINT(1 1)',4326))");
        run("INSERT INTO auto VALUES (2, 'two', GeomFromText('POINT(2 2)',4326))");
        run("INSERT INTO auto VALUES (3, 'three', GeomFromText('POINT(3 3)',4326))");
    }

    @Override
    protected void dropAutoGeneratedPrimaryKeyTable() throws Exception {
        run("DROP TABLE auto");
        run("DELETE FROM geometry_columns WHERE f_table_name = 'auto'");
    }

    @Override
    protected void createMultiColumnPrimaryKeyTable() throws Exception {
        run(
                "CREATE TABLE multi( key1 INTEGER NOT NULL, key2 VARCHAR NOT NULL, name VARCHAR, PRIMARY KEY(key1,key2))");
        run("SELECT AddGeometryColumn('multi','geom',4326,'POINT',2)");
        run("INSERT INTO multi VALUES (1, 'x', 'one', GeomFromText('POINT(1 1)',4326))");
        run("INSERT INTO multi VALUES (2, 'y', 'two', GeomFromText('POINT(2 2)',4326))");
        run("INSERT INTO multi VALUES (3, 'z', 'three', GeomFromText('POINT(3 3)',4326))");
    }

    @Override
    protected void dropMultiColumnPrimaryKeyTable() throws Exception {
        run("DROP TABLE multi");
        run("DELETE FROM geometry_columns WHERE f_table_name = 'multi'");
    }

    @Override
    protected void createNonIncrementingPrimaryKeyTable() throws Exception {
        run("CREATE TABLE noninc ( key int PRIMARY KEY, name VARCHAR)");
        run("SELECT AddGeometryColumn('noninc','geom',4326,'POINT',2)");
        run("INSERT INTO noninc VALUES (1, 'one', NULL)");
        run("INSERT INTO noninc VALUES (2, 'two', NULL)");
        run("INSERT INTO noninc VALUES (3, 'three', NULL)");
    }

    @Override
    protected void dropNonIncrementingPrimaryKeyTable() throws Exception {
        run("DROP TABLE noninc");
        run("DELETE FROM geometry_columns WHERE f_table_name = 'noninc'");
    }

    @Override
    protected void createSequencedPrimaryKeyTable() throws Exception {}

    @Override
    protected void createNonFirstColumnPrimaryKey() throws Exception {
        run("CREATE TABLE nonfirst(name VARCHAR, fid INTEGER PRIMARY KEY AUTOINCREMENT)");
        run("SELECT AddGeometryColumn('nonfirst','geom',4326,'POINT',2)");
        run("INSERT INTO nonfirst VALUES ('one', 1, GeomFromText('POINT(1 1)',4326))");
        run("INSERT INTO nonfirst VALUES ('two', 2, GeomFromText('POINT(2 2)',4326))");
        run("INSERT INTO nonfirst VALUES ('three', 3, GeomFromText('POINT(3 3)',4326))");
    }

    @Override
    protected void dropSequencedPrimaryKeyTable() throws Exception {}

    @Override
    protected void createNullPrimaryKeyTable() throws Exception {
        run("CREATE TABLE nokey (name VARCHAR)");
        run("INSERT INTO nokey VALUES ('one')");
        run("INSERT INTO nokey VALUES ('two')");
        run("INSERT INTO nokey VALUES ('three')");
    }

    @Override
    protected void dropNullPrimaryKeyTable() throws Exception {
        run("DROP TABLE nokey");
    }

    @Override
    protected void createUniqueIndexTable() throws Exception {
        run("CREATE TABLE uniq ( key int, name VARCHAR)");
        run("SELECT AddGeometryColumn('uniq','geom',4326,'POINT',2)");
        run("CREATE UNIQUE INDEX uniq_key_index ON uniq ( key )");
        run("INSERT INTO uniq VALUES (1, 'one', GeomFromText('POINT(1 1)',4326))");
        run("INSERT INTO uniq VALUES (2, 'two', GeomFromText('POINT(1 1)',4326))");
        run("INSERT INTO uniq VALUES (3, 'three', GeomFromText('POINT(1 1)',4326))");
    }

    @Override
    protected void dropUniqueIndexTable() throws Exception {
        run("DROP TABLE uniq");
        run("DELETE FROM geometry_columns WHERE f_table_name = 'uniq'");
    }

    @Override
    protected void dropNonFirstPrimaryKeyTable() throws Exception {
        run("DROP TABLE nonfirst");
        run("DELETE FROM geometry_columns WHERE f_table_name = 'nonfirst'");
    }
}
