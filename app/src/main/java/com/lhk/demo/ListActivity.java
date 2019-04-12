package com.lhk.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lhk.demo.sqlite.Constant;
import com.lhk.demo.sqlite.DBManager;
import com.lhk.demo.sqlite.DBHelper;

/**
 * Created by user on 2019/3/28.
 */

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private SQLiteDatabase db;
    private DBHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mHelper = DBManager.getInstance(this);
        listView = findViewById(R.id.listView);

        /**
         * openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags)
         * path 表示当前打开数据库的存放路径
         * factory 游标工厂
         * flags 表示打开数据库的操作模式
         */
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"info.db";
//        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);

        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ Constant.TableUser.TABLE_NAME,null);


        /**
         * SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
         * context 上下文对象
         * layout 表示适配器控件中每一项item的布局id
         * c 表示Cursor数据源
         * from 表示Cursor中数据表字段的数组
         * to 表示字段对应值的控件资源id
         * flags 设置适配器的标记
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item,cursor
                ,new String[]{Constant.TableUser._ID,Constant.TableUser.NAME,Constant.TableUser.AGE}
                ,new int[]{R.id.tv_id,R.id.tv_name,R.id.tv_age}
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

        db.close();
    }

    /**
     * 使用方法：
     * MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
     * listView.setAdapter(myCursorAdapter);
     */
    public static class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * 表示创建适配器控件中每个item对应的view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         * @param viewGroup 当前item的父布局
         * @return 每一项item的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = LayoutInflater.from(context).inflate(R.layout.item,null);
            return view;
        }

        /**
         * 通过newView()方法确定了每个item展示view对象，在bindView()中对布局中的控件进行填充
         * @param view 由newView（）返回的每项view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv_id = view.findViewById(R.id.tv_id);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_age = view.findViewById(R.id.tv_age);

            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant.TableUser._ID))+"");
            tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.TableUser.NAME)));
            tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.TableUser.AGE))+"");
        }
    }
}
