package com.lhk.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lhk.demo.sqlite.Constant;
import com.lhk.demo.sqlite.DBManager;
import com.lhk.demo.sqlite.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019/3/28.
 * select * from person limit 0,15; //当前页码第一条数据的下标，每页显示的数据条目
 */

public class ListPagerActivity extends AppCompatActivity {

    private ListView listView;
    private SQLiteDatabase db;
    private int total = 0;
    private int pageSize = 10;
    private int pageNum;//表示总页码
    private int currentPage = 1;//当前页码
    private List<Person> totalList = new ArrayList<>();//表示数据源
    private MyAdapter adapter;
    private boolean isDivPage;//是否分页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listView);

        /**
         * openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags)
         * path 表示当前打开数据库的存放路径
         * factory 游标工厂
         * flags 表示打开数据库的操作模式
         */
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"info.db";
        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        //获取数据表数据总条目
        total = DBManager.getDataTotal(db,Constant.TABLE_NAME);
        //根据总条目与每页展示数据条目 获得总页数
        pageNum = (int) Math.ceil(total/(double)pageSize);
        if (currentPage==1){
            totalList = DBManager.getListByCurrentPage(db,Constant.TABLE_NAME,currentPage,pageSize);
        }
        adapter = new MyAdapter(this,totalList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE==scrollState){
                    if (currentPage<pageNum){
                        currentPage++;
                        totalList.addAll(DBManager.getListByCurrentPage(db,Constant.TABLE_NAME,currentPage,pageSize));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int i2) {
                isDivPage = ((firstVisibleItem+visibleItemCount)==total);
            }
        });
    }


    public static class MyAdapter extends BaseAdapter{

        private Context context;
        private List<Person> list;

        public MyAdapter(Context context, List<Person> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.item,null);
            TextView tv_id = view.findViewById(R.id.tv_id);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_age = view.findViewById(R.id.tv_age);

            tv_id.setText(list.get(i).get_id()+"");
            tv_name.setText(list.get(i).getName());
            tv_age.setText(list.get(i).getAge()+"");
            return view;
        }
    }
}
