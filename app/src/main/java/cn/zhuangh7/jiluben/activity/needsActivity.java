package cn.zhuangh7.jiluben.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.adapter.detailAdapter;
import cn.zhuangh7.jiluben.activity.adapter.newAdapter;
import cn.zhuangh7.jiluben.activity.classes.ImageManager;
import cn.zhuangh7.jiluben.activity.classes.addNeedsDialog;
import cn.zhuangh7.jiluben.activity.classes.dataBase;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.classes.mainItem;
import cn.zhuangh7.jiluben.activity.classes.needsItem;


/**
 * Created by Zhuangh7 on 2016/10/30.
 */

public class needsActivity extends BaseActivity {
    List<needsItem> goods = new ArrayList<needsItem>();
    List<items> itemses = new ArrayList<items>();
    private static final String LOG_TAG = "secondDev";
    ListView mListView;
    public ImageManager imageManager;
    detailAdapter mAdapter;
    newAdapter MAdapter;
    needsItem[] goodss;
    items[] itemss;
    dataBase mdb;//数据库
    mainItem shop;
    TextView name;
    TextView pos;
    TextView tel;
    RelativeLayout click_d;
    ImageView click;
    String newPicName;
    boolean createPicSuccess = false;

    @Override
    protected void onResume() {
        super.onResume();
        //动态获取一下相机权限
        verifyCameraPermissions(this);
        //动态获取权限
        verifyStoragePermissions(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallayout);
        mListView = (ListView) findViewById(R.id.needs_listview);
        mdb = new dataBase(getApplicationContext());
        Intent intent = getIntent();
        int ID = intent.getExtras().getInt("ID");
        shop = mdb.getShopbyID(ID);
        name = (TextView) findViewById(R.id.shops_title);
        pos = (TextView) findViewById(R.id.shops_pos);
        tel = (TextView) findViewById(R.id.shops_tel);
        name.setText(shop.getName());
        pos.setText(shop.getPosition());
        tel.setText(shop.getTel());
        Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
        click_d = (RelativeLayout) findViewById(R.id.detail_d_2);
        click = (ImageView) findViewById(R.id.detail_d);

        click_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "onClick_d", Toast.LENGTH_SHORT).show();
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "onClick_d_2", Toast.LENGTH_SHORT).show();
            }
        });

        //initgoods();
        inititems();//TODO listview 数据初始化
        imageManager = new ImageManager(itemses, this);
        //mAdapter = new detailAdapter(getApplicationContext(), R.layout.itemlayout_needs,goods);
        MAdapter = new newAdapter(getApplicationContext(), itemses, this);
        //TODO 设置adapter与creator
        mListView.setAdapter(MAdapter);
//        mListView.

        //TODO set listview


    }
    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void addNeeds(String good ,int num){
        mdb.addNeeds(shop.getID(), good, num);
        updateGoods();
    }

    private void updateGoods(){
        initgoods();
        mAdapter.notifyDataSetChanged();
    }

    private void initgoods(){
        if(goods!=null){
            goods.clear();
        }
        goodss = mdb.readGoodsfromNeeds(shop.getID());
        for(int i = 0;i<goodss.length;i++) {
            goods.add(goodss[i]);
        }
    }

    private void inititems(){
        if(itemses!=null){
            itemses.clear();
        }
        itemss = mdb.readItem(shop.getID());
        for(int i = 0 ;i<itemss.length;i++) {
            itemses.add(itemss[i]);
        }
    }
    public void old_onAddDetail(View view) {
        addNeedsDialog dialog = new addNeedsDialog();
        dialog.show(getFragmentManager(), "NeedsDialog");
    }
    public void onAddDetail_2(View view){
        //TODO 显示输入dialog

        final EditText editText = new EditText(this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
        inputDialog.setView(editText).setTitle("新的记录");
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = mdb.newText(getName(), shop.getID(), editText.getText().toString());
                if(id>=0){
                    Toast.makeText(needsActivity.this,"添加成功,id:"+id,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(needsActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog tempDialog = inputDialog.create();
        tempDialog.setView(editText, 0, 0, 0, 0);

        /** 3.自动弹出软键盘 **/
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
        final AlertDialog a =tempDialog;
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                a.dismiss();
            }
        });
    }
    public void onAddDetail(View view){
        //TODO 开始拍照
        Log.d(LOG_TAG, "nothing to show");
        Uri fileUri;
        fileUri = getOutputMediaFileUri();
        if(fileUri!=null){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

            startActivityForResult(cameraIntent, 100);
        }else{
            //do nothing
        }

    }
    //返回一个Uri
    private  Uri getOutputMediaFileUri(){
        Uri temp = null;
        try{
            temp = Uri.fromFile(getOutputMediaFile());
        }catch(Exception e){
            //do nothing;
        }
        return temp;
    }

    private String getName(){
        return new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
    }
    public File getFileByName(String name){
        Environment.getExternalStorageState();
        File mediaStorageDir = null;
        try{
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"moms");
            Log.d(LOG_TAG,"created mediaStorageDir sucessfully"+mediaStorageDir);
        }catch(Exception e)
        {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in creating mediaStorageDir:" + mediaStorageDir);
        }
        if(!mediaStorageDir.exists()){
            return null;
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + name + ".jpg");
        return mediaFile;
    }
    //返回一个文件
    private File getOutputMediaFile(){

        createPicSuccess = false;//在创建文件开始前

        Environment.getExternalStorageState();
        File mediaStorageDir = null;
        try{
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"moms");
            Log.d(LOG_TAG,"created mediaStorageDir sucessfully"+mediaStorageDir);
        }catch(Exception e)
        {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in creating mediaStorageDir:" + mediaStorageDir);
        }
        //如果目录不存在那么新建一个
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdir())
            {
                Log.d(LOG_TAG,"failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());

        newPicName = timeStamp;//存储一下文件名

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator+ "IMG_" + timeStamp + ".jpg");


        return mediaFile;
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
    // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
    // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static void verifyCameraPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG,"onActivityResult: requestCode: " + requestCode
                + ", resultCode: " + requestCode + ", data: " + data);
        if (requestCode == 100) {
            Log.d(LOG_TAG, "CAPTURE_IMAGE");

            if (RESULT_OK == resultCode) {
                Log.d(LOG_TAG, "RESULT_OK");

                // Check if the result includes a thumbnail Bitmap
                if (data != null) {
                    // 没有指定特定存储路径的时候
                    Log.d(LOG_TAG,
                            "data is NOT null, file on default position.");

                    // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
                    // Image captured and saved to fileUri specified in the
                    // Intent
                    Toast.makeText(this, "Image saved to:\n" + data.getData(),
                            Toast.LENGTH_LONG).show();

                    if (data.hasExtra("data")) {
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        //imageView.setImageBitmap(thumbnail);
                    }

                } else {

                    Log.d(LOG_TAG,"data IS null, file saved on target position.");
                    createPicSuccess = true;//文件创建成功

                    //最后，先将信息写入数据库
                    if(createPicSuccess){
                        createPicSuccess = false;
                        int id = mdb.newPic(newPicName, shop.getID(), null);
                        Toast.makeText(this, "录入数据库成功，id" + id,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public Bitmap setImageByName(String name){


        File imageFile = getFileByName(name);

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getPath(), factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width3 = dm.widthPixels-(int)(26*density1);
        int height3 = dm.heightPixels;


        // Determine how much to scale down the image
        int scaleFactor = imageWidth/width3;

        // Decode the image file into a Bitmap sized to fill the
        // View

        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;
        factoryOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath(),
                factoryOptions);

        //image.setImageBitmap(bitmap);

        return bitmap;
    }

    public double getXY(String name){
        File imageFile = getFileByName(name);

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getPath(), factoryOptions);

        double imageWidth = factoryOptions.outWidth;
        double imageHeight = factoryOptions.outHeight;
        if(imageWidth!=0){
            return imageHeight/imageWidth;
        }else{
            return 0;
        }
    }
    public void changeImageView(ImageView imageView,String name){

    }


}
