package cn.zhuangh7.jiluben.activity.classes;

/**
 * Created by Zhuangh7 on 2016/10/23.
 */
public class mainItem {
    private String name;
    private String position;
    private String tel;
    private int ID;
    private boolean hasGoods;

    public mainItem(String name,String position,String tel,int ID,boolean hasGoods){
        this.name = name;
        this.position = position;
        this.tel = tel;
        this.ID = ID;
        this.hasGoods = hasGoods;
    }

    public String getName(){
        return name;
    }

    public String getPosition(){
        return position;
    }

    public String getTel(){
        return tel;
    }

    public int getID(){
        return ID;
    }

    public boolean ifHas(){
        return hasGoods;
    }
}
