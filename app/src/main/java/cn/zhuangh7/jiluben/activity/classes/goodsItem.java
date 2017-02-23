package cn.zhuangh7.jiluben.activity.classes;

/**
 * Created by Zhuangh7 on 2016/10/26.
 */
public class goodsItem{
    private String name;
    private int useNum;
    private int ID;

    public goodsItem(String name,int useNum,int ID){
        this.name = name;
        this.useNum = useNum;
        this.ID = ID;
    }

    public String getName(){
        return name;
    }

    public int getuseNum(){
        return useNum;
    }

    public int getID(){
        return ID;
    }
}
