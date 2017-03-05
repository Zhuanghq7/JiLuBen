package cn.zhuangh7.jiluben.activity.classes;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import cn.zhuangh7.jiluben.activity.dialog.showPictureDialog;

/**
 * Using to provid a two kind of layout itemlist
 * <p>
 * Created by Zhuangh7 on 2017/3/5.HUAWEI_SB
 */

public class detailImageReader extends Thread {
    MyHandle myHandle = new MyHandle();
    private String name;
    private showPictureDialog dialog;
    private Bitmap bitmap;

    public detailImageReader(String name, showPictureDialog dialog) {
        this.name = name;
        this.dialog = dialog;
    }

    private File getFileByName(String name) {
        Environment.getExternalStorageState();
        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "moms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert mediaStorageDir != null;
        if (!mediaStorageDir.exists()) {
            return null;
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + name + ".jpg");
        return mediaFile;
    }


    @Override
    public void run() {
        super.run();
        bitmap = loadBitmap(name);
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("fun", 1);
        msg.setData(b);
        myHandle.sendMessage(msg);
    }

    private Bitmap loadBitmap(String name) {
            File imageFile = getFileByName(name);
            Bitmap bbitmap = BitmapFactory.decodeFile(imageFile.getPath());
            return bbitmap;

    }
    private void loadSuccess(){
        dialog.refresh(bitmap);
        Toast.makeText(dialog.getContext(), "载入完整图片成功", Toast.LENGTH_SHORT).show();
    }

    private class MyHandle extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int fun = b.getInt("fun");
            switch (fun) {
                case 1:
                    loadSuccess();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }
}
