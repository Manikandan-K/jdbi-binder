package com.github.rkmk.binder;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;
import java.util.Map;

public class BaseTest {
    protected static DBI dbi= getDBI();

    protected DBI getDbi() {
        return getDBI();
    }

    private static DBI getDBI() {
//        String db = System.getProperty("db");
//        if (db.equals("postgres"))
            return new DBI("jdbc:postgresql://localhost:5432/jdbi_binder_test", "postgres", "");
//        else if(db.equals("mysql")) {
//            return new DBI("jdbc:mysql://localhost/jdbi_binder_test", "root", "");
//        }
//        return null;
    }

    protected static Handle handle = dbi.open();

    @BeforeClass
    public static void createDefaultTables() {
        handle.execute("create table movie (movie_id integer not null, movie_name varchar(100) not null, ratings numeric, director_id integer, director_name varchar(100));");
    }


    @Before
    public void clear() {
        handle.execute("delete from movie");
    }

    protected List<Map<String, Object>> select(String tableName) {
        return handle.select("select * from "+ tableName);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        handle.execute("drop table movie");
    }

}
