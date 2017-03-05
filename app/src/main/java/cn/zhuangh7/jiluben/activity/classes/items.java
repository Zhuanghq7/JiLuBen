package cn.zhuangh7.jiluben.activity.classes;

/**
 * Created by Zhuangh7 on 2017/2/27.
 */

public class items {
    String name;
    int shopId;
    String text;

    public void setDate(String[] date) {
        this.date = date;
    }

    public String[] getDate() {

        return date;
    }

    String[] date;

    boolean ifPic;
    boolean ifUpCloud;
    int id;

    public items(int id,String name,int shopId,String text,boolean ifUpCloud,boolean ifPic){
        this.name = name;
        this.shopId = shopId;
        this.ifPic = ifPic;
        this.ifUpCloud = ifUpCloud;
        this.id = id;
        this.text = text;
        date = new String[6];
    }

    public String getName() {
        return name;
    }

    public int getShopId() {
        return shopId;
    }
    public String getText() {
        return text;
    }

    public boolean isIfPic() {
        return ifPic;
    }

    public boolean isIfUpCloud() {
        return ifUpCloud;
    }

    public int getId() {
        return id;
    }
}
