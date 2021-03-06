package cn.zhuangh7.jiluben.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import cn.zhuangh7.jiluben.activity.dialog.editTextDialog;


/**
 * main activity
 * Created by Zhuangh7 on 2016/10/30.
 */

public class needsActivity extends BaseActivity {
    List<needsItem> goods = new ArrayList<needsItem>();
    List<items> itemses = new ArrayList<items>();
    private static final String LOG_TAG = "secondDev";
    public ListView mListView;
    public ImageManager imageManager;
    public detailAdapter mAdapter;
    public newAdapter MAdapter;
    needsItem[] goodss;
    items[] itemss;
    public dataBase mdb;//数据库
    public mainItem shop;
    TextView name;
    TextView pos;
    TextView tel;
    RelativeLayout click_d;
    ImageView click;
    String newPicName;
    ImageView refresh;
    boolean createPicSuccess = false;
    ImageButton morebutton;
    LinearLayout moredata;
    boolean isMoreData = false;
    RelativeLayout morebuttonl;
    RelativeLayout morebuttonl2;
    ImageButton morebutton2;
    static public String today[] = new String[3];


    @Override
    protected void onResume() {
        super.onResume();
        //动态获取一下相机权限
        verifyCameraPermissions(this);
        //动态获取权限
        verifyStoragePermissions(this);

    }

    private void refreshlistview() {
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        int x = mListView.getScrollX();
        int y = mListView.getScrollY();
        MAdapter.notifyDataSetChanged();
        mListView.scrollTo(x, y);//刷新列表
    }

    public void refreshlist() {
        inititems();
        refreshlistview();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init date
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
        int temptag = 0;
        today[0] = "";
        today[1] = "";
        today[2] = "";
        for(int i = 0;temptag<3&&i<timeStamp.length();i++){
            if(timeStamp.charAt(i)!='_'&&timeStamp.charAt(i)!='-'){
                today[temptag] += timeStamp.charAt(i);
            }else{
                temptag++;
            }
        }
        Log.e("TODAY", "" + today[0] + " " + today[1] + " " + today[2]);
        setContentView(R.layout.detallayout);

        mdb = new dataBase(getApplicationContext());
        final Intent intent = getIntent();
        int ID = intent.getExtras().getInt("ID");
        shop = mdb.getShopbyID(ID);

        mListView = (ListView) findViewById(R.id.needs_listview);
        refresh = (ImageView) findViewById(R.id.detail_refresh);
        name = (TextView) findViewById(R.id.shops_title);
        pos = (TextView) findViewById(R.id.shops_pos);
        tel = (TextView) findViewById(R.id.shops_tel);
        morebutton = (ImageButton) findViewById(R.id.more_button);
        morebutton2 = (ImageButton) findViewById(R.id.more_button2);
        morebuttonl2 = (RelativeLayout) findViewById(R.id.more_button_l2);
        moredata = (LinearLayout) findViewById(R.id.more_data);
        morebuttonl = (RelativeLayout) findViewById(R.id.more_button_l);


        name.setText(shop.getName());
        pos.setText(shop.getPosition());
        tel.setText(shop.getTel());
        Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);

        //initgoods();
        inititems();//TODO listview 数据初始化
        inititemsdate();

        imageManager = new ImageManager(itemses, this);//TODO 图片助手初始化

        MAdapter = new newAdapter(needsActivity.this, itemses, this);
        mListView.setAdapter(MAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final items itemTemp = itemses.get(position);
                final editTextDialog dialog = new editTextDialog(needsActivity.this, itemTemp);
                dialog.setOnDeleteOnclickListener(new editTextDialog.onDeleteOnclickListener() {
                    @Override
                    public void onDeleteClick() {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(needsActivity.this);
                        dialogBuilder.setTitle("警告");
                        dialogBuilder.setMessage("确认要删除这一条目吗？");
                        dialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                mdb.deleteText(itemTemp.getName(), shop.getID());
                                Toast.makeText(needsActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                                Log.e("HUAWEI SB", "delete item from shop");
                                refreshlist();
                                imageManager.reLoadPosition(position);//图片位置改变
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();

                    }
                });
                dialog.setOnAcceptOnclickListener(new editTextDialog.onAcceptOnclickListener() {
                    @Override
                    public void onAcceptClick() {
                        mdb.editText(itemTemp.getName(), shop.getID(), dialog.getMainText());
                        Toast.makeText(needsActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        refreshlist();
                        dialog.dismiss();
                    }
                });
                dialog.setOnCancelOnclickListener(new editTextDialog.onCancelOnclickListener() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //TODO set listview
        morebuttonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMoreData) {
                    moreDataShow();
                } else {
                    Animation ani = AnimationUtils.loadAnimation(needsActivity.this, R.anim.convert);
                    ani.setFillAfter(true);
                    morebutton.startAnimation(ani);
                    moredata.setVisibility(View.GONE);
                    isMoreData = false;
                }
            }
        });
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMoreData) {
                    moreDataShow();
                } else {
                    Animation ani = AnimationUtils.loadAnimation(needsActivity.this, R.anim.convert);
                    ani.setFillAfter(true);
                    morebutton.startAnimation(ani);
                    moredata.setVisibility(View.GONE);
                    isMoreData = false;
                }
            }
        });
        morebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMoreData) {
                    moreDataShow();
                } else {
                    moreDataClose();
                }
            }
        });
        morebuttonl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMoreData) {
                    moreDataShow();
                } else {
                    moreDataClose();
                }
            }
        });
//        throw new NullPointerException();

    }

    private void inititemsdate() {
        for (items temp : itemses) {
            String date = temp.getName();
            temp.setDate(arrangeDate(date));
        }
    }
    private String[] arrangeDate(String date){
        int tag = 0;
        String[] tempdate = new String[6];
        tempdate[0] = "";
        tempdate[1] = "";
        tempdate[2] = "";
        tempdate[3] = "";
        tempdate[4] = "";
        tempdate[5] = "";
        for(int i=0;i<date.length()&&tag<6;i++) {
            if(date.charAt(i)!='_'&&date.charAt(i)!='-'){
                tempdate[tag] += date.charAt(i);
            }else{
                tag++;
            }
        }
        return tempdate;
    }
    public void moreDataClose() {
        morebutton.setVisibility(View.INVISIBLE);
        morebuttonl.setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(needsActivity.this, R.anim.convert);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                morebutton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        morebutton.startAnimation(ani);
        moredata.setVisibility(View.GONE);
        isMoreData = false;
    }

    public void moreDataShow() {
        morebutton.setVisibility(View.INVISIBLE);
        RotateAnimation ani = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani.setDuration(200);
        morebutton.startAnimation(ani);//按钮旋转
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                morebuttonl.setVisibility(View.GONE);
                morebutton.setVisibility(View.GONE);//转一圈消失

                moredata.setVisibility(View.INVISIBLE);//显示出来
                moredata.setVisibility(View.VISIBLE);
                Animation anii = AnimationUtils.loadAnimation(needsActivity.this, R.anim.convert);
                morebutton2.setVisibility(View.INVISIBLE);
                isMoreData = true;
                anii.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        morebutton2.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                morebutton2.startAnimation(anii);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void showRefresh() {
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.refresh);
        refresh.setAnimation(ani);
        refresh.setVisibility(View.VISIBLE);

    }

    public void missRefresh() {
        refresh.setAnimation(null);
        refresh.setVisibility(View.GONE);

    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void addNeeds(String good, int num) {
        mdb.addNeeds(shop.getID(), good, num);
        updateGoods();
    }

    private void updateGoods() {
        initgoods();
        mAdapter.notifyDataSetChanged();
    }

    private void initgoods() {
        if (goods != null) {
            goods.clear();
        }
        goodss = mdb.readGoodsfromNeeds(shop.getID());
        Collections.addAll(goods, goodss);
    }

    private void inititems() {
        if (itemses != null) {
            itemses.clear();
        }
        itemss = mdb.readItem(shop.getID());
        Collections.addAll(itemses, itemss);
    }

    public void old_onAddDetail(View view) {
        addNeedsDialog dialog = new addNeedsDialog();
        dialog.show(getFragmentManager(), "NeedsDialog");
    }

    public void onAddDetail_2(View view) {
        //TODO 显示输入dialog

        final EditText editText = new EditText(this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
        inputDialog.setView(editText).setTitle("新的记录");
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = mdb.newText(getName(), shop.getID(), editText.getText().toString());
                if (id >= 0) {
                    refreshlist();//刷新listview
                    Toast.makeText(needsActivity.this, "添加成功,id:" + id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(needsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
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
        final AlertDialog a = tempDialog;
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                a.dismiss();
            }
        });
    }

    public void onAddDetail(View view) {
        //TODO 开始拍照
        Log.d(LOG_TAG, "nothing to show");
        Uri fileUri;
        fileUri = getOutputMediaFileUri();
        if (fileUri != null) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

            startActivityForResult(cameraIntent, 100);
        }

    }

    //返回一个Uri
    private Uri getOutputMediaFileUri() {
        Uri temp = null;
        try {
            temp = Uri.fromFile(getOutputMediaFile());
        } catch (Exception e) {
            //do nothing;
        }
        return temp;
    }

    private String getName() {
        return new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
    }

    public File getFileByName(String name) {
        Environment.getExternalStorageState();
        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "moms");
            Log.d(LOG_TAG, "created mediaStorageDir sucessfully" + mediaStorageDir);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in creating mediaStorageDir:" + mediaStorageDir);
        }
        assert mediaStorageDir != null;
        if (!mediaStorageDir.exists()) {
            return null;
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + name + ".jpg");
        return mediaFile;
    }

    //返回一个文件
    private File getOutputMediaFile() {

        createPicSuccess = false;//在创建文件开始前

        Environment.getExternalStorageState();
        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "moms");
            Log.d(LOG_TAG, "created mediaStorageDir sucessfully" + mediaStorageDir);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in creating mediaStorageDir:" + mediaStorageDir);
        }
        //如果目录不存在那么新建一个
        assert mediaStorageDir != null;
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.d(LOG_TAG, "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());

        newPicName = timeStamp;//存储一下文件名

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");


        return mediaFile;
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
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
        Log.d(LOG_TAG, "onActivityResult: requestCode: " + requestCode
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

//                    if (data.hasExtra("data")) {
//                        Bitmap thumbnail = data.getParcelableExtra("data");
//                        //imageView.setImageBitmap(thumbnail);
//                    }

                } else {
                    Log.d(LOG_TAG, "data IS null, file saved on target position.");
                    createPicSuccess = true;//文件创建成功

                    //最后，先将信息写入数据库
                    createPicSuccess = false;
                    int id = mdb.newPic(newPicName, shop.getID(), null);
                    refreshlist();//
                    Toast.makeText(this, "录入数据库成功，id" + id, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    static public String transDate(String date) {
        String[] sit = new String[6];
        for(int i = 0;i<6;i++){
            sit[i] = "";
        }
        int tag = 0;
        for (int i = 0; i < date.length(); i++) {
            if(date.charAt(i)=='_'||date.charAt(i)=='-'){
                tag++;
                continue;
            }
            sit[tag] += date.charAt(i);
        }
        if(sit[0].equals(today[0])&&sit[1].equals(today[1])&&sit[2].equals(today[2])){
            return "今天  "+sit[3]+"点"+sit[4]+"分"+sit[5]+"秒 ";

        }
        return sit[0]+"年"+sit[1]+"月"+sit[2]+"日  "+sit[3]+"点"+sit[4]+"分"+sit[5]+"秒 ";
    }

}
