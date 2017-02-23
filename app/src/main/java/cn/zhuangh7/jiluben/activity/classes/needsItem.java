package cn.zhuangh7.jiluben.activity.classes;

/**
 * Created by Zhuangh7 on 2016/10/30.
 */

public class needsItem {
    private int ID;
    private int shopID;
    private int goodID;
    private int num;
    private String name;
    private String ifup;
    private String time;

    public needsItem(int ID,int shopID,int goodID,int num,String name,String ifup,String time){
        this.ID = ID;
        this.shopID = shopID;
        this.goodID = goodID;
        this.num = num;
        this.ifup = ifup;
        this.time = time;
        this.name = name;
    }

    public int getID(){return ID;}

    public int getShopID(){return shopID;}

    public int getGoodID(){return goodID;}

    public int getNum(){
        return num;}

    public String getIfup(){
        return ifup;
    }

    public String getTime() {
        return time;
    }

    public String getName(){
        return name;
    }

}
