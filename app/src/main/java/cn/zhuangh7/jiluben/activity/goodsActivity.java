package cn.zhuangh7.jiluben.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.adapter.goodsAdapter;
import cn.zhuangh7.jiluben.activity.classes.addGoodsDialog;
import cn.zhuangh7.jiluben.activity.classes.dataBase;
import cn.zhuangh7.jiluben.activity.classes.goodsItem;

/**
 * Created by Zhuangh7 on 2016/10/26.
 */
public class goodsActivity extends BaseActivity {
    private SwipeMenuListView mListview;
    private goodsAdapter mAdapter;
    private List<goodsItem> goods = new ArrayList<goodsItem>();
    private ImageButton breturn;
    private ImageButton add;
    private dataBase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);

        mListview = (SwipeMenuListView) findViewById(R.id.listView_goods);
        mdb = new dataBase(getApplicationContext());
        initgoods();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
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
        mAdapter = new goodsAdapter(goodsActivity.this, R.layout.itemlayout_goods,goods);
        mListview.setAdapter(mAdapter);
        mListview.setMenuCreator(creator);
        mListview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        //mdb.deleteGoods(goods.get(position).getID());
                        dialog_ifdelete(position);
                        //TODO ask delete dialog
                        break;
                }
                return false;
            }
        });
        //TODO init listview
        add = (ImageButton) findViewById(R.id.goods_add);

    }
    private void dialog_ifdelete(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(goodsActivity.this);
        builder.setTitle("确定删除这个货品吗？");
        builder.setMessage("与其相关联得货单等信息将同时删除");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mdb.deleteGoods(goods.get(pos).getID());
                updateListView();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
        //TODO show the ifdelete dialog
    }

    public void onClickReturn(View view){
        finish();
        //TODO return to the mainActivity
    }

    public void onAdd(View view){
        addGoodsDialog dialog = new addGoodsDialog();
        dialog.show(getFragmentManager(), "GoodsDialog");
        //TODO open the add dial
    }

    public void onClickCancel(View view){
        //Toast.makeText(getApplicationContext(), "onClickCancel", Toast.LENGTH_SHORT).show();
        //do nothing here
    }

    public void onClickApply(View view,String name,int num){
        //Toast.makeText(getApplicationContext(), "onClickApply", Toast.LENGTH_SHORT).show();
        int r = mdb.addGoods(name, num);
        if(r == -1){
            Toast.makeText(getApplicationContext(), "添加失败，可能出现了重名", Toast.LENGTH_SHORT).show();
            //here problem
        }else if(r == -1) {
            Toast.makeText(getApplicationContext(), "添加失败，名字不能空着的呀", Toast.LENGTH_SHORT).show();
        }else
        {
            updateListView();
            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
        }
        //TODO add data to database
    }
    public void updateListView(){
        initgoods();
        mAdapter.notifyDataSetChanged();
        //TODO the same as refresh listview
    }
    private void initgoods(){
        goods.clear();
        goodsItem[] result;
        result = mdb.readGoods();
        for(int i = 0;i<result.length;i++){
            goods.add(result[i]);
        }
        //TODO clear goods and reload the data
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
