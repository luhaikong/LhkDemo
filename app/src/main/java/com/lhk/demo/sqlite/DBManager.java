package com.lhk.demo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019/3/27.
 * 对数据库操作的工具类
 */

public class DBManager {
    private static MySqLiteHelper helper;
    public static MySqLiteHelper getInstance(Context context){
        if (helper==null){
            synchronized (MySqLiteHelper.class){
                if (helper==null){
                    helper = new MySqLiteHelper(context);
                }
            }
        }
        return helper;
    }

    /**
     * 获取数据总条目
     * @param db
     * @param tableName
     * @return 数据总条目
     */
    public static int getDataTotal(SQLiteDatabase db,String tableName){
        int total = 0;
        if (db!=null){
            Cursor cursor = db.rawQuery("select * from "+tableName,null);
            total = cursor.getCount();//获取cursor中的数据总数
        }
        return total;
    }

    /**
     * 根据当前页码查询获取该页码对应的数据集合
     * @param db
     * @param tableName
     * @param currentPage
     * @return
     */
    public static List<Person> getListByCurrentPage(SQLiteDatabase db,String tableName,int currentPage,int pageSize){
        Cursor cursor = null;
        int index = (currentPage-1)*pageSize;
        if (db!=null){
            String sql = "select * from "+tableName+" limit ?,?";
            cursor = db.rawQuery(sql,new String[]{index+"",pageSize+""});
        }
        return cursorToList(cursor);
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql 查询的sql语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                cursor = db.rawQuery(sql,selectionArgs);
            }
        }
        return cursor;
    }

    /**
     * 将查询的cursor队对象转换成list集合
     * @param cursor
     * @return
     */
    public static List<Person> cursorToList(Cursor cursor){
        List<Person> list = new ArrayList<>();
        //cursor.moveToNext() 如果返回true表示下一条记录存在，否则表示游标中数据读取完毕
        while (cursor.moveToNext()){
            //getColumnIndex 根据参数中指定的字段名称获取字段下标
            int columnIndex = cursor.getColumnIndex(Constant._ID);
            //getInt 根据参数中指定的字段下标获取对应int类型的数据
            int _id = cursor.getInt(columnIndex);

            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));

            Person person = new Person();
            person.set_id(_id);
            person.setName(name);
            person.setAge(age);

            list.add(person);
        }
        return list;
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db
     * @param sql
     */
    public static void execSQL(SQLiteDatabase db,String sql){
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }
}
