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

import java.io.File;
import java.util.List;

import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * some interesting work
 * Created by Zhuangh7 on 2017/2/28.HUAWEI_SB
 */
public class ImageManager extends Thread {
    MyHandle myHandle = new MyHandle();
    int fun = 1;
    boolean refresh = false;
    private Picture picture[][] = new Picture[3][10];
    private List<items> itemsList;
    needsActivity activity;
    private int now = 1;
    private int up = 0;
    private int down = 2;
    private boolean upload = false;
    private boolean download = false;
    private boolean isUp = true;//判断当前listview是向上还是向下滚动
    private int position = -1;
    private boolean load = false;
    public boolean isrun = false;
    private boolean ifloading = false;
    private int position4load;
    private boolean isUp4load;
    private int load4load;

    private void showRefreshMsg() {
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("fun", 2);
        msg.setData(b);
        myHandle.sendMessage(msg);
    }

    private void endRefreshMsg() {
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("fun", 3);
        msg.setData(b);
        myHandle.sendMessage(msg);
    }

    @Override
    public void run() {
        super.run();
        ifloading = true;
        showRefreshMsg();
        int i = itemsList.size();
        int I = 0;
        if (itemsList.size() >= 1) {

            for (items temp = itemsList.get(--i); i >= 0; ) {
                if (temp.isIfPic()) {
                    Picture newPic = new Picture(i, loadBitmap(temp.getName()));
                    picture[1][I++] = newPic;

                    //TODO send refresh message
                    if (refresh) {
                        refresh = false;
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("fun", 1);
                        msg.setData(b);
                        myHandle.sendMessage(msg);
                    }
                    if (I == 10) {
                        break;
                    }
                }
                if (i >= 1) {
                    temp = itemsList.get(--i);
                } else {
                    i = -1;
                }
            }
            //nowUse = 1;
        }
        ifloading = false;
        endRefreshMsg();
        while (isrun) {
            if (load) {
                ifloading = true;
                Log.e("HUAWEI SB", "more load" + isUp4load);
                showRefreshMsg();
                loadImage(position4load, isUp4load, load4load);
                //TODO send refresh message
                if (refresh) {
                    refresh = false;
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putInt("fun", 1);
                    msg.setData(b);
                    myHandle.sendMessage(msg);
                }

                endRefreshMsg();
                Log.e("HUAWEI SB", "more load success");
                load = false;//stop load
                ifloading = false;
            }
        }
    }


    public ImageManager(List<items> itemsList, needsActivity activity) {
        this.itemsList = itemsList;
        this.activity = activity;
        picture[0] = new Picture[10];
        picture[2] = new Picture[10];
        picture[1] = new Picture[10];
        //new缓存空间
        //TODO 加载第一批次图片,从后往前加载,tag越小越后
        isrun = true;
        this.start();
    }

    private void goUp() {
        Log.e("HUAWEI SB", "exchange now to up");
        download = true;
        int temp = down;
        down = now;
        now = up;
        up = temp;
        upload = false;
    }

    private void goDown() {
//找到了转移阵地
        Log.e("HUAWEI SB", "exchange now to down");
        upload = true;
        int temp;
        temp = up;
        up = now;
        now = down;
        down = temp;
        download = false;
    }

    public Bitmap getImageByposition(int position) {
        Log.e("HUAWEI SB", "" + isUp);
        int tempI = 0;
        for (; tempI < 10; tempI++) {
            if (picture[now][tempI] != null && picture[now][tempI].getPosition() == position) {
                return picture[now][tempI].getImage();
            }
        }//TODO 在目前列表中寻找
        if (upload) {
            boolean iffind = false;
            for (tempI = 0; tempI < 10; tempI++) {
                if (picture[up][tempI] != null && picture[up][tempI].getPosition() == position) {
                    iffind = true;
                    break;
                }
            }
            if (iffind) {
                goUp();
                return picture[now][tempI].getImage();
            }
        }//TODO 如果上列表已经加载则寻找
        if (download) {
            boolean iffind = false;
            for (tempI = 0; tempI < 10; tempI++) {
                if (picture[down][tempI] != null && picture[down][tempI].getPosition() == position) {
                    iffind = true;
                    break;
                }
            }
            if (iffind) {
                goDown();
                return picture[now][tempI].getImage();
            }
        }
        if(ifloading){
                refresh = true;
                Log.e("HUAWEI SB", "null 4");
                return null;
            //TODO 上下列表找寻完毕都没有，且正在加载，等待加载完成。
        }
        if (isUp) {
            if (picture[now][9] == null) {
                refresh = true;
                Log.e("HUAWEI SB", "null 2");
                return null;
                //TODO 说明当前列表未满，却请求加载，说明image还未载入内存；
            } else {
                Log.e("HUAWEI SB", "more load 1");
                refresh = true;
                moreload(picture[now][9].getPosition() - 1, true, up);

                //return getImageByposition(position);
                Log.e("HUAWEI SB", "null 3");

                return null;
            }//TODO 如果上滚动 且全列表都没有，则说明需要更多加载
        } else {
            if (picture[now][0] == null) {
                refresh = true;
                Log.e("HUAWEI SB", "null 5");
                return null;
                //TODO 说明当前列表未满，却请求加载，说明image还未载入内存；
            } else {
                refresh = true;
                moreload(picture[now][0].getPosition() + 1, false, down);

                Log.e("HUAWEI SB", "null 6");
                return null;
            }
        }

    }

    private void moreload(int position, boolean isUp, int load) {
        if (!ifloading) {
            position4load = position;
            isUp4load = isUp;
            load4load = load;
            if(isUp){
                goUp();
            }else{
                goDown();
            }
            this.load = true;
        }
    }

    private void loadImage(int position, boolean isUp, int load) {
        int tag;
        int i = position;
        if (isUp) {
            tag = 0;
        } else {
            tag = 9;
        }
        for (; i >= 0 && i <= itemsList.size() - 1; ) {
            if (tag > 9 || tag < 0) {
                break;
            }
            if (itemsList.get(i).isIfPic()) {
                picture[load][tag] = new Picture(i, loadBitmap(itemsList.get(i).getName()));
                if (isUp) {
                    tag++;
                } else {
                    tag--;
                }
            }
            if (!isUp) {
                i++;
            } else {
                i--;
            }
        }
        if (isUp) {
            upload = true;
        } else {
            download = true;
        }
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

    private Bitmap loadBitmap(String name) {

        try {
            File imageFile = getFileByName(name);

            BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

            factoryOptions.inJustDecodeBounds = true;
            assert imageFile != null;
            BitmapFactory.decodeFile(imageFile.getPath(), factoryOptions);

            int imageWidth = factoryOptions.outWidth;
            int imageHeight = factoryOptions.outHeight;
            //获取屏幕大小
            Resources resources = activity.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float density1 = dm.density;
            int width3 = dm.widthPixels - (int) (26 * density1);
            //int height3 = dm.heightPixels;


            // Determine how much to scale down the image
            int scaleFactor = imageWidth / width3;

            factoryOptions.outWidth = width3;
            factoryOptions.outHeight = imageHeight * width3 / imageWidth;

            // Decode the image file into a Bitmap sized to fill the
            // View

            factoryOptions.inJustDecodeBounds = false;
            factoryOptions.inSampleSize = scaleFactor;
            factoryOptions.inPreferredConfig = null;

            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath(),
                    factoryOptions);

            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width3, imageHeight * width3 / imageWidth, true);
            if (newBitmap.equals(bitmap)) {
                return bitmap;
            } else {
                bitmap.recycle();
                return newBitmap;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void setPosition(int position) {
        if (this.position != -1) {
            if (position == this.position) {

            } else if (position > this.position) {
                if (isUp) {
                    isUp = false;
                }
            } else {
                if (!isUp) {
                    isUp = true;
                }
            }
        }
        this.position = position;
        //实时更新position，创建主动函数加载。
    }

    class MyHandle extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int fun = b.getInt("fun");
            switch (fun) {
                case 1:
                    activity.mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
                    int x = activity.mListView.getScrollX();
                    int y = activity.mListView.getScrollY();
                    activity.MAdapter.notifyDataSetChanged();
                    activity.mListView.scrollTo(x, y);
                    Log.e("111", "refresh");
                    break;
                case 2:
                    activity.showRefresh();
                    break;
                case 3:
                    activity.missRefresh();
                    break;
                default:
                    break;
            }
        }
    }
}
