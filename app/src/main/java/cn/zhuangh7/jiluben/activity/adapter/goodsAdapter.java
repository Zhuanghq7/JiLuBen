package cn.zhuangh7.jiluben.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.dataBase;
import cn.zhuangh7.jiluben.activity.classes.goodsItem;
import cn.zhuangh7.jiluben.activity.goodsActivity;

/**
 * Created by Zhuangh7 on 2016/10/26.
 */
public class goodsAdapter extends ArrayAdapter {
    private int ResourceID;
    public goodsAdapter(Context context, int resource,List<goodsItem> objects) {
        super(context, resource,objects);
        ResourceID = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final viewHolder vh;
        goodsItem item = (goodsItem) getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(ResourceID, null);
            vh = new viewHolder();
            vh.name = (TextView) view.findViewById(R.id.item_goods_name);
            vh.editButton = (RelativeLayout) view.findViewById(R.id.item_goods_edit);
            vh.editButton_2 = (ImageButton) view.findViewById(R.id.item_goods_edit_2);
            vh.editName = (EditText) view.findViewById(R.id.item_goods_edit_name);
            vh.editApply = (RelativeLayout) view.findViewById(R.id.item_goods_edit_apply);
            vh.editApply_2 = (ImageButton) view.findViewById(R.id.item_goods_edit_apply_2);
            vh.editCancel = (RelativeLayout) view.findViewById(R.id.item_goods_edit_cancel);
            vh.editCancel_2 = (ImageButton) view.findViewById(R.id.item_goods_edit_cancel_2);
            vh.editLayout = (LinearLayout) view.findViewById(R.id.item_goods_editlayout_buttons);
            view.setTag(vh);
        }else{
            view = convertView;
            vh = (viewHolder) convertView.getTag();
        }
        vh.name.setText(item.getName());
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.editName.setVisibility(View.VISIBLE);
                vh.name.setVisibility(View.GONE);
                vh.editButton_2.setVisibility(View.GONE);
                vh.editButton.setVisibility(View.GONE);
                vh.editLayout.setVisibility(View.VISIBLE);
                vh.editName.setHint(vh.name.getText());
                //Toast.makeText(getContext(), "change view success", Toast.LENGTH_SHORT).show();
            }
        });
        vh.editButton_2.setClickable(true);
        vh.editButton_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View veiw){
                vh.editName.setVisibility(View.VISIBLE);
                vh.name.setVisibility(View.GONE);
                vh.editButton_2.setVisibility(View.GONE);
                vh.editButton.setVisibility(View.GONE);
                vh.editLayout.setVisibility(View.VISIBLE);
                vh.editName.setHint(vh.name.getText());
                //Toast.makeText(getContext(), "change view success", Toast.LENGTH_SHORT).show();
            }
        });
        vh.editCancel_2.setClickable(true);
        vh.editCancel.setClickable(true);
        vh.editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.editName.setVisibility(View.GONE);
                vh.name.setVisibility(View.VISIBLE);
                vh.editButton_2.setVisibility(View.VISIBLE);
                vh.editButton.setVisibility(View.VISIBLE);
                vh.editLayout.setVisibility(View.GONE);
                vh.editName.setHint(vh.name.getText());
                //Toast.makeText(getContext(), "change view success", Toast.LENGTH_SHORT).show();
            }
        });
        vh.editCancel_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.editName.setVisibility(View.GONE);
                vh.name.setVisibility(View.VISIBLE);
                vh.editButton_2.setVisibility(View.VISIBLE);
                vh.editButton.setVisibility(View.VISIBLE);
                vh.editLayout.setVisibility(View.GONE);
                //Toast.makeText(getContext(), "change view success", Toast.LENGTH_SHORT).show();
            }
        });
        vh.editApply_2.setClickable(true);
        vh.editApply_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsItem temp = (goodsItem) getItem(position);
                dataBase mdb = new dataBase(getContext());
                boolean result = mdb.updateGoods(temp.getID(), vh.editName.getText().toString(),-2);
                //-2 means don't change
                if(result){
                    ((goodsActivity)getContext()).updateListView();
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
                vh.editName.setVisibility(View.GONE);
                vh.name.setVisibility(View.VISIBLE);
                vh.editButton_2.setVisibility(View.VISIBLE);
                vh.editButton.setVisibility(View.VISIBLE);
                vh.editLayout.setVisibility(View.GONE);
            }
        });
        vh.editApply.setClickable(true);
        vh.editApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsItem temp = (goodsItem) getItem(position);
                dataBase mdb = new dataBase(getContext());
                boolean result = mdb.updateGoods(temp.getID(), vh.editName.getText().toString(),-2);
                //-2 means don't change

                if(result){
                    ((goodsActivity)getContext()).updateListView();
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT);
                }
                vh.editName.setVisibility(View.GONE);
                vh.name.setVisibility(View.VISIBLE);
                vh.editButton_2.setVisibility(View.VISIBLE);
                vh.editButton.setVisibility(View.VISIBLE);
                vh.editLayout.setVisibility(View.GONE);
            }
        });
        if (vh.editName.getVisibility() == View.VISIBLE) {
            vh.editName.requestFocus();
        }
        return view;
    }



    class viewHolder{
        TextView name;
        RelativeLayout editButton;
        ImageButton editButton_2;
        EditText editName;
        RelativeLayout editApply;
        ImageButton editApply_2;
        RelativeLayout editCancel;
        ImageButton editCancel_2;
        LinearLayout editLayout;
    }
}
