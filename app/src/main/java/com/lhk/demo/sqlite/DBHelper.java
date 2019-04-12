package com.lhk.demo.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2019/3/27.
 */

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context){
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    /**
     * 构造函数
     * @param context 上下文对象
     * @param name 表示创建数据库名称
     * @param factory 游标工厂
     * @param version 表示创建数据库的版本 >=1
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+Constant.TableUser.TABLE_NAME+"("+Constant.TableUser._ID+" Integer primary key" +
                ","+Constant.TableUser.NAME+" varchar(10)" +
                ","+Constant.TableUser.AGE+" Integer)";
        db.execSQL(sql);

        String sql1 = "create table "+Constant.TableJob.TABLE_NAME+"("+Constant.TableJob._ID+" Integer primary key" +
                ","+Constant.TableJob.JOB+" varchar(10))";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 使用for实现跨版本升级数据库
        for (int ver = oldVersion; ver < newVersion; ver++) {
            switch (ver) {

                default:
                    break;
            }
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }
}
