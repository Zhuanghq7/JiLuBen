package cn.zhuangh7.jiluben.activity.classes;

import android.graphics.Bitmap;

/**
 * Created by Zhuangh7 on 2017/2/28.
 */

public class Picture {
    private int position;
    private int id;
    private Bitmap image;

    public Picture(int position,Bitmap image,int id){
        this.position = position;
        this.image = image;
    }
    public int getPosition() {
        return position;
    }

    public int getID(){
        return id;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setPosition(int id) {
        this.position = id;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
