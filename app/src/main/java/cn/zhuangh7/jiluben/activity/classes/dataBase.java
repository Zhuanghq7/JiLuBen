package cn.zhuangh7.jiluben.activity.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhuangh7 on 2016/10/25.
 */
public class dataBase {
    private SQLiteDatabase mDateBase;
    private mDateBase dbHelper;
    private Context context;
    private Boolean isInit = false;
    public dataBase(Context context) {
        this.context = context;
        init();
    }
    private void init(){
        dbHelper = new mDateBase(context,"JILUBEN.db",null,1);
        mDateBase = dbHelper.getWritableDatabase();
        isInit = true;
    }

    public int newPic(String name,int shopId,String text){
        //TODO 新的图片，默认p_cloud = 0;p_nopic = 0;p_text = null 成功返回ID\-1 text可为NULL
        if(isInit){
            if (name.equals("")) {
                return -2;
            }
            mDateBase.execSQL("insert into Pictures(shop_id,p_name,p_text,p_cloud,p_nopic)values(?,?,?,?,?)",new Object[]{shopId,name,text,0,0});
            Cursor cursor = mDateBase.rawQuery("select ID from Pictures where p_name = ? and shop_id = ?", new String[]{name,""+shopId});
            while(cursor.moveToNext()){
                int ID = cursor.getInt(0);
                return ID;
            }
        }
        return -1;
    }
    public boolean deletePic(String name,int shopId){
        if(isInit){
            if(name.equals("")){
                return false;
            }
            mDateBase.execSQL("delete from Pictures where p_name = ? and shop_id = ?",new Object[]{name,shopId});
            return true;
        }
        //TODO 删除照片，同时删除text
        return false;
    }
    public boolean editPic(String name,int shopId,String text){
        //TODO 修改照片对应信息
        if(isInit){
            if(name.equals("")){
                return false;
            }
            mDateBase.execSQL("update Pictures set p_text = ? where p_name = ? and shop_id = ?",new Object[]{text,name,shopId});
            return true;
        }
        return false;
    }


    public int newText(String name,int shopId,String text){
        //TODO 新的text text不能为null 返回id
        if(isInit){
            if (name.equals("")||text.equals("")) {
                return -2;
            }
            mDateBase.execSQL("insert into Pictures(shop_id,p_name,p_text,p_cloud,p_nopic)values(?,?,?,?,?)",new Object[]{shopId,name,text,0,1});
            Cursor cursor = mDateBase.rawQuery("select ID from Pictures where p_name = ? and shop_id = ?", new String[]{name,""+shopId});
            while(cursor.moveToNext()){
                int ID = cursor.getInt(0);
                return ID;
            }
        }
        return -1;
    }
    public boolean editText(String name,int shopId,String text) {
        return editPic(name, shopId, text);
    }

    public boolean deleteText(String name,int shopId){
        //TODO 懒得写了
        if(isInit){
            mDateBase.execSQL("delete from Pictures where p_name = ? and shop_id = ?", new Object[]{name, shopId});
            return true;
        }
        return false;
    }

    public boolean upclould(String name,int shop_Id){
        //TODO 上传字段设置为1
        if(isInit){
            try {
                mDateBase.execSQL("update Pictures set p_cloud = ? where p_name = ? and shop_id = ?", new Object[]{1, name, shop_Id});
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean ifPic(String name,int shop_Id){
        //TODO 是否是图片
        if(isInit){
            try{
                Cursor cursor = mDateBase.rawQuery("select p_nopic from Pictures where p_name = ? and shop_id = ?", new String[]{name, ""+shop_Id});
                while(cursor.moveToNext()){
                    int ifpic = cursor.getInt(0);
                    if(ifpic == 1){
                        return false;
                    }else{
                        return true;
                    }
                }
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }
    //还需要一波读取
    public items[] readItem(int shopId){
        //TODO 读取数据库所有数据

        if (isInit) {
            items[] itemses;
            int tag = 0;
            items item;
            Cursor cursor = mDateBase.rawQuery("select ID,p_name,p_text,p_cloud,p_nopic from Pictures where shop_id = ?", new String[]{""+shopId});
            try {
                itemses = new items[cursor.getCount()];
                while (cursor.moveToNext()) {
                    item = new items(cursor.getInt(0), cursor.getString(1), shopId, cursor.getString(2), (cursor.getInt(3) == 1 ? true : false), (cursor.getInt(4) == 1 ? true : false));
                    itemses[tag++] = item;
                }
                return itemses;
            }catch (Exception e){
                return null;
            }

        }
        return null;
    }

    public int addShop(String name,String pos,String tel){
        if(isInit){
            if (name.equals("")) {
                return -2;
            }
            mDateBase.execSQL("insert into Shop(name,pos,tel)values(?,?,?)",new Object[]{name,pos,tel});
            Cursor cursor = mDateBase.rawQuery("select ID from Shop where name = ? and pos = ? and tel = ?", new String[]{name, pos, tel});
            while(cursor.moveToNext()){
                int ID = cursor.getInt(0);
                return ID;
            }
        }
        return -1;
    }

    public boolean upNeeds(int ID){
        if(isInit){
            mDateBase.execSQL("update Needs set ifup = 'true' where ID = ?", new Object[]{ID});
            return true;
        }
        return false;
    }
    public boolean deleteNeeds(int ID){
        if(isInit){
            mDateBase.execSQL("delete from Needs where ID = ?",new Object[]{ID});
            return true;
        }
        return false;
    }
    public int addNeeds(int shop_id,String goods_name,int num){
        if (isInit) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm ");
            Date curDate = new Date(System.currentTimeMillis());
            //获取当前时间
            String str = formatter.format(curDate);
            Log.d("233",str);
            mDateBase.execSQL("insert into Needs(shop_id,good_id,num,ifup,ifcplt) values(?,(select ID from Goods where Goods.name = ?),?,'false',?)",new Object[]{shop_id,goods_name,num,str});
            //TODO add needs
            Cursor cursorID = mDateBase.rawQuery("select ID from Needs where shop_id = ? and good_id = (select ID from Goods where Goods.name = ?) and ifcplt = ? and num = ?", new String[]{""+shop_id, goods_name, str, ""+num});
            while (cursorID.moveToNext()) {
                int ID = cursorID.getInt(0);
                return ID;
            }
        }
        return -1;
    }

    public int addGoods(String name,int defaultNum){
        if(isInit){
            Cursor cursorB = mDateBase.rawQuery("select ID from Goods where name = ?",new String[]{name});
            if(cursorB.getCount()!=0){
                return -1;
            }else{
                mDateBase.execSQL("insert into Goods(name,num)values(?,?)",new Object[]{name,defaultNum});
                Cursor cursor = mDateBase.rawQuery("select ID from Goods where name = ?",new String[]{name});
                cursor.moveToNext();
                int ID = cursor.getInt(0);
                return ID;
            }

        }
        return -1;
    }
    public needsItem[] readGoodsfromNeeds(int shopID){
        needsItem[] result;
        Cursor cursor = mDateBase.rawQuery("select Needs.ID,shop_id,good_id,Needs.num,Goods.name,ifup,ifcplt from Needs,Goods where Goods.ID = Needs.good_id and shop_id = ?", new String[]{"" + shopID});
        int num = cursor.getCount();
        result = new needsItem[num];
        int tag = 0;
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            int shop_ID = cursor.getInt(1);
            int good_ID = cursor.getInt(2);
            int nnum = cursor.getInt(3);
            String name = cursor.getString(4);
            String ifup = cursor.getString(5);
            String time = cursor.getString(6);
            result[tag++] = new needsItem(ID, shop_ID, good_ID, nnum,name, ifup, time);
        }
        return result;
    }
    public goodsItem[] readGoods(){
        goodsItem[] result;
        Cursor cursor = mDateBase.query("Goods", new String[]{"ID", "name", "num"}, null, null, null, null, null, null);
        int num = cursor.getCount();
        result = new goodsItem[num];
        int tag = 0;
        while(cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String name = cursor.getString(1);
            int numm = cursor.getInt(2);
            result[tag++] = new goodsItem(name, numm, ID);
        }
        return result;
    }
    public boolean ifShopHasNeeds(int id){
        Cursor cursor = mDateBase.rawQuery("select * from Needs where shop_id = ?",new String[]{""+id});
        if(cursor.getCount()!=0){
            return true;
        }
        else{
            return false;
        }
    }
    public mainItem getShopbyID(int ID) {
        mainItem result;
        Cursor cursor = mDateBase.rawQuery("select name,pos,tel from Shop where ID = ?", new String[]{""+ID});
        int num = cursor.getCount();
        //result = new mainItem();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String pos = cursor.getString(1);
            String tel = cursor.getString(2);

            result = new mainItem(name, pos, tel, ID,ifShopHasNeeds(ID));
            return result;
        }
        return null;
    }
    public mainItem[] readShop(){
        mainItem[] result;
        Cursor cursor = mDateBase.query("Shop", new String[]{"ID","name","pos","tel"}, null, null, null, null, null, null);
        int num = cursor.getCount();
        result = new mainItem[num];
        int tag = 0;
        while(cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String name = cursor.getString(1);
            String pos = cursor.getString(2);
            String tel = cursor.getString(3);
            result[tag++] = new mainItem(name,pos,tel,ID,ifShopHasNeeds(ID));
        }
        return result;
    }

    public void deleteData(){
        mDateBase.execSQL("drop table if exists Shop");
        mDateBase.execSQL("drop table if exists Goods");
        mDateBase.execSQL("drop table if exists Needs");
    }
    //TODO DON'T USE

    public boolean deleteShop(String name,String pos,String tel){
        if(isInit){
            mDateBase.execSQL("delete from Shop where Shop.name = ? and Shop.pos = ? and Shop.tel = ?",new Object[]{name,pos,tel});
            return true;
        }
        return false;
    }

    public boolean deleteShop(int ID){
        if(isInit){
            mDateBase.execSQL("delete from Shop where Shop.ID = ?",new Object[]{ID});
            mDateBase.execSQL("delete from Needs where shop_id = ?",new Object[]{ID});
            return true;
        }
        return false;
    }

    public boolean deleteGoods(int ID){
        if (isInit) {
            mDateBase.execSQL("delete from Goods where Goods.ID = ?",new Object[]{ID});
            mDateBase.execSQL("delete from Needs where good_id = ?", new Object[]{ID});
            return true;
        }
        return false;
    }
    public boolean updateShop(int ID,String name,String pos,String tel){
        if(isInit){
            String sql = "update Shop set";
            if(ID == -1){
                return false;
            }else{
                if(name!=null){
                    sql+=" name = "+name+",";
                }
                if(pos!=null){
                    sql+=" pos = "+pos+",";
                }
                if(tel!=null){
                    sql+=" tel = "+tel;
                }
                if(name==null && pos ==null && tel ==null){
                    return false;
                }
                else{
                    sql+=" where ID = "+ID;
                    mDateBase.execSQL(sql);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateGoods(int ID,String name,int num){
        if(isInit){
            if(name.equals(""))
                return false;
            String sql = "update Goods set";
            if(name!=null){
                Cursor cursorB = mDateBase.rawQuery("select ID from Goods where name = ?",new String[]{name});
                if(cursorB.getCount()!=0){
                    return false;
                }
            }

            if(ID == -1){
                return false;
            }else{
                if(name != null){
                    sql+=" name = '"+name+"'";
                }
                if(num!=-2){
                    sql+=" num = "+num;
                }
                sql+=" where ID = "+ID;
                mDateBase.execSQL(sql);
                return true;
            }

        }
        return false;
    }

}
