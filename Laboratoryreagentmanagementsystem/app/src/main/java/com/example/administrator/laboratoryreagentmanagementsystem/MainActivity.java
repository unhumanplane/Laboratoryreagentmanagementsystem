package com.example.administrator.laboratoryreagentmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button btn_insert;
    private Button btn_query;
    private Button btn_qk;
    private Button btn_delete;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private int i = 1;
    private EditText name;//品名
    private EditText number;//数量
    private EditText company;//品牌
    private EditText retenloca;//存放地点
    private EditText casid;//CASID
    private EditText specification;//规格型号
    private EditText price;//单价
    private EditText storagetim;//入库时间
    private TextView aa;
    public class MyDBOpenHelper extends SQLiteOpenHelper {
        public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {super(context, "my.db1", null, 1); }//初始化数据库
        @Override
        //数据库第一次创建时被调用
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE person1(personid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),sex VARCHAR(20),szbm VARCHAR(20),gz VARCHAR(20))");//建表person1  personid为主类， name为varchara20的列
        }
        //软件版本号发生改变时调用,即如果修改db的版本号，那么下次启动就会调用onUpgrade()里的方法，往表中再插入一个字段
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE person1 ADD phone VARCHAR(12) NULL");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db1", null, 1);
        bindViews();
    }
    private void bindViews() {
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        company = (EditText) findViewById(R.id.company);
        retenloca = (EditText) findViewById(R.id.retenloca);
        casid = (EditText) findViewById(R.id.casid);
        specification = (EditText) findViewById(R.id.specification);
        price = (EditText) findViewById(R.id.price);
        storagetim = (EditText) findViewById(R.id.storagetim);

        aa = (TextView)findViewById(R.id.textView);
        btn_insert = (Button) findViewById(R.id.btn_insert);//插入数据
        btn_query = (Button) findViewById(R.id.btn_query);//查询
        btn_delete = (Button) findViewById(R.id.btn_delete);//删除
        btn_qk = (Button) findViewById(R.id.button_qk);//清空
        btn_query.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_qk.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        db = myDBHelper.getWritableDatabase();
      //TODO
        String  name1 = name.getText().toString();
        String sex1 = sex.getText().toString();
        String szbm1 = szbm.getText().toString();
        String gz1 = gz.getText().toString();

        switch (v.getId()) {
            case R.id.btn_insert://输入
                ContentValues values1 = new ContentValues();
                values1.put("personid",idh1);
                values1.put("name",data1);
                values1.put("sex",sex1);
                values1.put("szbm",szbm1);
                values1.put("gz",gz1);

                //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
                db.insert("person1", null, values1);
                Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query://查询
                sb = new StringBuilder();//建立字符串
                //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
                //指定查询结果的排序方式
                Cursor cursor = db.query("person1", null, null, null, null, null, null);//新建光标接口
                if (cursor.moveToFirst()) {
                    do {
                        int pid = cursor.getInt(cursor.getColumnIndex("personid"));//获得id
                        String a1 = cursor.getString(cursor.getColumnIndex("name"));//获得名字
                        String a2 = cursor.getString(cursor.getColumnIndex("sex"));//获得名字
                        String a3 = cursor.getString(cursor.getColumnIndex("szbm"));//获得名字
                        String a4 = cursor.getString(cursor.getColumnIndex("gz"));//获得名字
                        sb.append(  pid +" "+a1+" "+a2+" "+a3+" "+a4 + "\n");//整理输出
                    } while (cursor.moveToNext());//直到没有下一个了
                }
                cursor.close();//关闭接口
                aa.setText(sb.toString());
                break;
            case R.id.btn_delete://删除
                SQLiteDatabase db = myDBHelper.getReadableDatabase();
                Cursor cursor2 =  db.rawQuery("SELECT COUNT (*) FROM person1",null);
                cursor2.moveToFirst();
                long result = cursor2.getLong(0);
                String er= String.valueOf(result);
                cursor2.close();
                //参数依次是表名，where条件与约束
                db.delete("person1", "personid = ?", new String[]{String.valueOf(idh1)});
                Toast.makeText(mContext, "删除"+idh1, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_qk://清空
                SQLiteDatabase db1 = myDBHelper.getReadableDatabase();
                Cursor cursor3 =  db1.rawQuery("SELECT COUNT (*) FROM person",null);
                cursor3.moveToFirst();
                long result1 = cursor3.getLong(0);
                String er1= String.valueOf(result1);
                cursor3.close();

                //参数依次是表名，where条件与约束

                db1.delete("person1",null, null);
                Toast.makeText(mContext, "清空"+er1, Toast.LENGTH_SHORT).show();
                break;

        }
    }

}