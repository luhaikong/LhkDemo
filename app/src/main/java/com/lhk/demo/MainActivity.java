package com.lhk.demo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lhk.demo.sqlite.Constant;
import com.lhk.demo.sqlite.DBManager;
import com.lhk.demo.sqlite.DBHelper;
import com.lhk.demo.sqlite.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = DBManager.getInstance(this);
    }

    /**
     * 点击按钮创建数据库
     * @param view
     */
    public void createDb(View view) {
        /**
         * getReadableDatabase()、getWritableDatabase()创建或打开数据库
         * 如果数据库不存在则创建数据库，如果数据库存在直接打开数据库
         * 默认情况下两个函数都表示打开或者创建可读可写数据库
         * 如果磁盘已满或者是数据库本身权限等情况下getReadableDatabase()打开的是只读数据库
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        for (int i=1;i<=30;i++){
            String sql = "insert into "+Constant.TableUser.TABLE_NAME+" values("+i+",'zhangsan"+i+"',20)";
            db.execSQL(sql);
        }
        db.close();
    }

    public void click1(View view) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        switch (view.getId()){
            case R.id.btn_insert:
                /**
                 * insert(String table, String nullColumnHack, ContentValues values)
                 * table 表示插入数据表名称
                 * nullColumnHack
                 * values 键为String类型的hashmap集合
                 * 返回值 long 表示插入数据的列数
                 */
                ContentValues values = new ContentValues();
                values.put(Constant.TableUser._ID,3);
                values.put(Constant.TableUser.NAME,"zhansan");
                values.put(Constant.TableUser.AGE,18);
                long result = db.insert(Constant.TableUser.TABLE_NAME,null,values);
                if (result>0){
                    Toast.makeText(MainActivity.this,"插入数据成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"插入数据失败！",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btn_update:
                /**
                 * update(String table, ContentValues values, String whereClause, String[] whereArgs)
                 * table 表示修改数据表名称
                 * values 键为String类型的hashmap集合
                 * whereClause 表示修改条件
                 * whereArgs 表示修改条件的占位符
                 * 返回值 int 表示修改数据的条数
                 */
                ContentValues valuesUpdate = new ContentValues();
                valuesUpdate.put(Constant.TableUser.NAME,"xiaomi");
                //int count = db.update(Constant.TableUser.TABLE_NAME,valuesUpdate,Constant.TableUser._ID+"=3",null);
                int count = db.update(Constant.TableUser.TABLE_NAME,valuesUpdate,Constant.TableUser._ID+"=?",new String[]{"3"});
                if (count>0){
                    Toast.makeText(MainActivity.this,"修改数据成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"修改数据失败！",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btn_delete:
                /**
                 * delete(String table, String whereClause, String[] whereArgs)
                 * table 表示删除数据表的名称
                 * whereClause 表示删除的条件
                 * whereArgs 表示删除条件的占位符
                 */
                int countDel = db.delete(Constant.TableUser.TABLE_NAME,Constant.TableUser._ID+"=?",new String[]{"1"});
                if (countDel>0){
                    Toast.makeText(MainActivity.this,"删除数据成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"删除数据失败！",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btn_query:
                /**
                 * query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
                 * table 表示查询的表名
                 * columns 表示查询表中的字段名称，null：查询所有
                 * selection 表示查询条件 where子句
                 * selectionArgs 表示查询条件占位符
                 * groupBy 表示分组条件 groupBy子句
                 * having 表示筛选条件 having子句
                 * orderBy 表示排序条件 orderBy子句
                 */
                Cursor cursor = db.query(Constant.TableUser.TABLE_NAME,null
                        ,Constant.TableUser._ID+">?",new String[]{"10"}
                        ,null,null
                        ,Constant.TableUser._ID+" desc");
                List<User> list = DBManager.cursorToList(cursor);
                for (User p:list){
                    Log.i("tag",p.getName());
                }
                db.close();
                break;
        }
    }

    public void click(View view) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        switch (view.getId()){
            case R.id.btn_insert:
                String sql = "insert into "+ Constant.TableUser.TABLE_NAME+" values(1,'张三',20)";
                DBManager.execSQL(db,sql);
                String sql2 = "insert into "+ Constant.TableUser.TABLE_NAME+" values(2,'李四',30)";
                DBManager.execSQL(db,sql2);
                db.close();
                break;
            case R.id.btn_update:
                String updateSql = "update "+Constant.TableUser.TABLE_NAME+" set "+Constant.TableUser.NAME+"='xiaoming' where "+Constant.TableUser._ID+"=1";
                DBManager.execSQL(db,updateSql);
                db.close();
                break;
            case R.id.btn_delete:
                String delSql = "delete from "+Constant.TableUser.TABLE_NAME+" where "+Constant.TableUser._ID+"=2";
                DBManager.execSQL(db,delSql);
                db.close();
                break;
            case R.id.btn_query:
                String querySql = "select * from "+Constant.TableUser.TABLE_NAME;
                Cursor cursor = DBManager.selectDataBySql(db,querySql,null);
                List<User> list = DBManager.cursorToList(cursor);
                for (User p:list){
                    Log.i("tag",p.getName());
                }
                db.close();
                break;
            case R.id.btn_tolist:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_insertDatas:
                //1.数据库显示开启事务
                db.beginTransaction();
                for (int i=1;i<100;i++){
                    String sq = "insert into "+Constant.TableUser.TABLE_NAME+" values("+i+",'xiaomu"+i+"',18)";
                    db.execSQL(sq);
                }
                //2.提交当前事务
                db.setTransactionSuccessful();
                //3.关闭事务
                db.endTransaction();
                db.close();
                break;
            case R.id.btn_pager:
                Intent intentp = new Intent(MainActivity.this, ListPagerActivity.class);
                startActivity(intentp);
                break;
        }
    }
}
