package cn.zhuangh7.jiluben.activity.classes;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangjie.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Created by Zhuangh7 on 2016/10/30.
 */

public class addNeedsDialog extends DialogFragment {
    View view;
    dataBase mdb;
    goodsItem[] goods;
    String select;
    ArrayList<String> goodsString = new ArrayList<String>();
    ArrayList<String> goodsNum = new ArrayList<String>();
    RelativeLayout add;
    TextView add_2;
    int num;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        for(int i = 0;i<25;i++){
            goodsNum.add(""+(i+1));
        }
        //TODO init nums
        view = inflater.inflate(R.layout.selectdialog, null);
        //TODO set layout
        mdb = new dataBase(getActivity());
        goods =  mdb.readGoods();
        for(int i = 0;i<goods.length;i++) {
            goodsString.add(goods[i].getName());
        }
        //TODO init goods
        if(goods.length == 0){
            Toast.makeText(getActivity(), "没有任何货品", Toast.LENGTH_SHORT).show();
            //dismiss();
            return view;
        }

        WheelView wva = (WheelView) view.findViewById(R.id.main_mw);
        //wva.init(getActivity());
        wva.setOffset(1);
        wva.setItems(goodsString);
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                //Log.d("233", "selectedIndex: " + selectedIndex + ", item: " + item);
                select = item;
            }
        });
        //TODO set wheelview


        WheelView wva_2 = (WheelView) view.findViewById(R.id.main_mw_num);
        //wva_2.init(getActivity());
        wva_2.setOffset(1);
        wva_2.setItems(goodsNum);
        wva_2.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                //Log.d("233", "selectedIndex: " + selectedIndex + ", item: " + item);
                num = Integer.parseInt(item);
            }
        });


        //TODO set num wheelview
        add = (RelativeLayout) view.findViewById(R.id.detail_click_add);
        add_2 = (TextView) view.findViewById(R.id.detail_click_add_2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((needsActivity)getActivity()).addNeeds(select,num);
                dismiss();
            }
        });

        add_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((needsActivity)getActivity()).addNeeds(select,num);
                dismiss();
            }
        });
        select = goods[0].getName();
        num = 1;
        return view;

    }
}
