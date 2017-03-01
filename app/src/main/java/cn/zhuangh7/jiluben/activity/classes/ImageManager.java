package cn.zhuangh7.jiluben.activity.classes;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Zhuangh7 on 2017/2/28.HUAWEI_SB
 */
public class ImageManager extends Thread {
    @Override
    public void run() {
        super.run();
        int i = itemsList.size();
        int I = 0;
        if (itemsList.size() >= 1) {

            for (items temp = itemsList.get(--i); i >= 0; ) {
                if (temp.isIfPic()) {
                    Picture newPic = new Picture(i, loadBitmap(temp.getName()));
                    picture[1][I++] = newPic;
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
    }

    private Picture picture[][] = new Picture[3][10];
    private List<items> itemsList;
    Activity activity;
    private int now = 1;
    private int up = 0;
    private int down = 2;
    private int tag = -2;
    private boolean upload = false;
    private boolean download = false;
    private boolean isUp = true;//判断当前listview是向上还是向下滚动
    private int position = -1;

    public ImageManager(List<items> itemsList, Activity activity) {
        this.itemsList = itemsList;
        this.activity = activity;
        picture[0] = new Picture[10];
        picture[2] = new Picture[10];
        picture[1] = new Picture[10];
        //new缓存空间
        //TODO 加载第一批次图片,从后往前加载,tag越小越后
        this.start();
    }

    public Bitmap getImageByposition(int position) {
        if(tag>=0&&tag<10&&picture[now][tag]!=null&&picture[now][tag].getPosition() == position){
            return picture[now][tag].getImage();
        }
        if(tag == -1){
            tag = 0;
        }
        if (tag == -2) {
            //第一次读图
            tag = 0;
            isUp = true;
        } else if (isUp) {
            tag++;
        } else {
            tag--;
        }//应该得到的图片位置
        if (tag <= -1) {
            //向下翻页
            if (download) {
                tag = 9;
                upload = true;
                int temp;
                temp = up;
                up = now;
                now = down;
                down = temp;
                download = false;

            } else {
                //TODO load
                loadImage(picture[now][0].getPosition() + 1, false, down);
                return getImageByposition(position);
            }
        } else if (tag >= 10) {
            //向上翻页
            if (upload) {
                tag = 0;
                download = true;
                int temp = down;
                down = now;
                now = up;
                up = temp;
                upload = false;
            } else {
                //TODO load
                loadImage(picture[now][9].getPosition() - 1, true, up);
                return getImageByposition(position);

            }
        }
        if (picture[now][tag] == null) {
            return null;
        } else {
            //return picture[now][tag].getImage();
            if (picture[now][tag].getPosition() != position) {
                return null;
            } else {
                return picture[now][tag].getImage();
            }
            //return picture[now][tag].getPosition()==position?picture[now][tag].getImage():null;//最后返回now tag位置图片
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
            if (position > this.position) {
                if (isUp) {
                    isUp = false;
                    for (; tag >= 0 && tag < 9; tag--) {
                        if (picture[now][tag] == null || picture[now][tag].getPosition() >= position) {
                            tag++;
                            break;
                        }
                    }
                }
            } else {
                if (!isUp) {
                    isUp = true;
                    for (; tag <= 9 && tag > 0; tag++) {
                        if (picture[now][tag] == null || picture[now][tag].getPosition() <= position) {
                            tag--;
                            break;
                        }
                    }
                }
            }
        }

        this.position = position;
        //实时更新position，创建主动函数加载。
    }
}
