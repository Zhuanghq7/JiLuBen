package cn.zhuangh7.jiluben.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.mainItem;
import cn.zhuangh7.jiluben.activity.mainActivity;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Created by Zhuangh7 on 2016/10/23.
 */
public class mainAdapter extends ArrayAdapter<mainItem>{
    private int ResourceID;
    private boolean canClick = true;
    public mainAdapter(Context context,  int textViewResourceId, List<mainItem> objects) {
        super(context,textViewResourceId, objects);
        ResourceID = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        mainItem item = getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(ResourceID,null);
            viewHolder = new ViewHolder();
            viewHolder.shopname = (TextView)view.findViewById(R.id.item_name);
            viewHolder.shoppos = (TextView)view.findViewById(R.id.item_position);
            viewHolder.shoptel = (TextView)view.findViewById(R.id.item_phone);
            viewHolder.right = (ImageButton) view.findViewById(R.id.item_right);
            viewHolder.click = (RelativeLayout) view.findViewById(R.id.item_click);
            viewHolder.right.setBackgroundResource(R.drawable.right);
            viewHolder.listen = (ImageView) view.findViewById(R.id.listen);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        if(item.ifHas()){
            viewHolder.listen.setVisibility(View.VISIBLE);
        }else{
            viewHolder.listen.setVisibility(View.GONE);
        }
        viewHolder.shopname.setText(item.getName());
        viewHolder.shoppos.setText(item.getPosition());
        viewHolder.shoptel.setText(item.getTel());
        viewHolder.click.setClickable(true);
        viewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canClick){
                    mainItem mitem = getItem(position);
                    Intent intent = new Intent();
                    intent.setClass(getContext(),needsActivity.class);
                    intent.putExtra("ID", mitem.getID());
                    getContext().startActivity(intent);
                }
            }
        });
        viewHolder.right.setClickable(true);
        viewHolder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canClick){
                    Intent intent = new Intent();
                    mainItem mitem = getItem(position);
                    intent.setClass(getContext(),needsActivity.class);
                    intent.putExtra("ID", mitem.getID());
                    getContext().startActivity(intent);
                }
            }
        });
        Linkify.addLinks(viewHolder.shoptel, Linkify.PHONE_NUMBERS);
        return view;
    }


    class ViewHolder{
        TextView shopname;
        TextView shoppos;
        TextView shoptel;
        ImageButton right;
        RelativeLayout click;
        ImageView listen;
    }
}
