package cn.zhuangh7.jiluben.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.adapter.mainAdapter;
import cn.zhuangh7.jiluben.activity.classes.mDateBase;
import cn.zhuangh7.jiluben.activity.classes.mainItem;
import cn.zhuangh7.jiluben.activity.classes.dataBase;

/**
 * Created by Zhuangh7 on 2016/10/23.
 */
public class mainActivity extends BaseActivity {
    private SwipeMenuListView mListView;
    private List<mainItem> shops = new ArrayList<mainItem>();

    private dataBase mdb;
    private mainAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void clickadd(View view){
        Intent intent = new Intent();
        intent.setClass(mainActivity.this, addActivity.class);
        startActivityForResult(intent,0);
    }

    public void clickgoods(View view){
        Intent intent = new Intent();
        intent.setClass(mainActivity.this,goodsActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                /*// create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);*/
                // TODO donot use open item

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(128));
                // set a icon
                //deleteItem.setIcon(R.drawable.add);
                deleteItem.setTitle("删除");
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(18);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // TODO　use some garbage
        mdb = new dataBase(getApplicationContext());
        // TODO create database
        mListView = (SwipeMenuListView) findViewById(R.id.listView_main);
        initShops();
        // TODO get shops from database
        mAdapter = new mainAdapter(mainActivity.this,R.layout.itemlayout_main,shops);
        mListView.setAdapter(mAdapter);
        mListView.setMenuCreator(creator);
        // TODO add garbage to my listview

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        dialog_ifdelete(position);
                        break;
                }
                return false;
            }
        });
        Log.e("HUAWEI", "SB");

    }

    private void initShops(){
        //mainItem mitem = new mainItem("TEXT_NAME","TEXT_POSITION","18340861800");
        if(shops!=null){
            shops.clear();
        }
        mainItem[] shop = mdb.readShop();
        if(shop.length!=0){
            hideNoShops();
        }else{
            showNoShops();
        }
        for(int i = 0;i<shop.length;i++){
            shops.add(shop[i]);
        }
    }

    private void hideNoShops(){
        TextView item = (TextView)findViewById(R.id.textView_noShops);
        item.setVisibility(View.GONE);
    }
    private void showNoShops(){
        TextView item = (TextView)findViewById(R.id.textView_noShops);
        item.setVisibility(View.VISIBLE);
    }
    private void dialog_ifdelete(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity.this);
        builder.setTitle("确定删除这家店铺吗？");
        builder.setMessage("与其相关联得货单等信息将同时删除");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mdb.deleteShop(shops.get(pos).getID());
                refreshList();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        switch(requestCode){
            case 0: {
                if (resultCode == 0) {
                    Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                }
                if (resultCode == 2) {
                    Toast.makeText(getApplicationContext(), "添加失败，赶紧问你儿子", Toast.LENGTH_SHORT).show();
                }
                if (requestCode == 1) {
                    Toast.makeText(getApplicationContext(), "放弃添加", Toast.LENGTH_SHORT).show();
                }
            }
            refreshList();
        }
    }

    private void refreshList(){
        initShops();
        mAdapter.notifyDataSetChanged();
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
