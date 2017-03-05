package cn.zhuangh7.jiluben.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polites.android.GestureImageView;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.detailImageReader;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Using to show pic detail
 * <p>
 * Created by Zhuangh7 on 2017/3/2. HUAWEI_SB
 */

public class showPictureDialog extends Dialog {
    public GestureImageView imageview;
    private items item;
    public Bitmap bitmap;
    private Button quit;
    public boolean isLoad = false;

    public showPictureDialog(Context context, items item,Bitmap bitmap) {
        super(context, R.style.MyDialog);
        this.item = item;
        this.bitmap = bitmap;
        Window win = getWindow();
        assert win != null;
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pic_show);
        //setCanceledOnTouchOutside(false);

        initView();

        initData();

        initEvent();
    }
    public void refresh(Bitmap bitmap){
        this.bitmap = bitmap;
        imageview.setImageBitmap(bitmap);
        isLoad = true;
    }
    private void initView() {
        imageview = (GestureImageView) findViewById(R.id.show_pic_image);
        quit = (Button) findViewById(R.id.show_pic_quit);
    }

    private void initData(){
        imageview.setImageBitmap(bitmap);
        new detailImageReader(item.getName(),this).start();
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoad){
                    Log.e("HUAWEI SB", "isLoad");
                    if(bitmap==null) {
                        Log.e("HUAWEI SB", "null");
                    }
                    bitmap.recycle();
                }
                dismiss();
            }
        });
    }
    private void initEvent(){

    }

}
