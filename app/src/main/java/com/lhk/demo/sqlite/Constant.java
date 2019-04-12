package com.lhk.demo.sqlite;

/**
 * Created by user on 2019/3/27.
 */

public class Constant {
    public static final String DATABASE_NAME = "info.db";
    public static final int DATABASE_VERSION = 1;

    public static class TableUser{
        public static final String TABLE_NAME = "user";
        public static final String _ID="_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
    }

    public static class TableJob{
        public static final String TABLE_NAME = "job";
        public static final String _ID="_id";
        public static final String JOB = "job";
    }

}
