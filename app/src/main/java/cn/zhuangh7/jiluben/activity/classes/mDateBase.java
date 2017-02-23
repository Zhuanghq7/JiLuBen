package cn.zhuangh7.jiluben.activity.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Zhuangh7 on 2016/10/23.
 */
public class mDateBase extends SQLiteOpenHelper {
    public static final String CREATE_SHOP = "create table Shop("
            +"ID integer primary key autoincrement,"
            +"name text,"
            +"pos text,"
            +"tel text)";
    public static final String CREATE_GOODS = "create table Goods("
            +"ID integer primary key autoincrement,"
            +"name text)";
    public static final String CREATE_NEEDS = "create table Needs("
            +"ID integer primary key autoincrement,"
            +"shop_id integer references Shop(ID),"
            +"good_id integer references Goods(ID),"
            +"num integer,"
            +"ifup text not null,"
            +"ifcplt text not null)";
    private Context mContext;

    public mDateBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SHOP);
        sqLiteDatabase.execSQL(CREATE_GOODS);
        sqLiteDatabase.execSQL(CREATE_NEEDS);
        sqLiteDatabase.execSQL("alter table Goods add 'num' integer default 0 ");
        Toast.makeText(mContext,"创建数据库成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("alter table Goods add 'num' integer default 0 ");
        Toast.makeText(mContext,"升级数据库至版本2成功",Toast.LENGTH_LONG).show();
    }
}
