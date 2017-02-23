package cn.zhuangh7.jiluben.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.needsItem;

/**
 * Created by Zhuangh7 on 2016/10/30.
 */

public class detailAdapter extends ArrayAdapter<needsItem> {
    private int ResourceID;

    public detailAdapter(Context context, int textViewResourceId, List<needsItem> objects) {
        super(context, textViewResourceId, objects);
        ResourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh;
        needsItem item = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(ResourceID, null);
            vh = new ViewHolder();
            vh.name = (TextView) view.findViewById(R.id.needs_name);
            vh.date = (TextView) view.findViewById(R.id.needs_date);
            vh.num = (TextView) view.findViewById(R.id.needs_num);
            vh.color = (LinearLayout) view.findViewById(R.id.detail_change_color);
            view.setTag(vh);
        }else{
            view = convertView;
            vh = (ViewHolder) view.getTag();

        }
        vh.name.setText(item.getName());
        vh.date.setText(item.getTime());
        vh.num.setText(""+item.getNum());
        if(item.getIfup().equals("true")){
            vh.color.setBackgroundColor(getContext().getResources().getColor(R.color.goods));
        }else{
            vh.color.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }
        return view;

    }

    class ViewHolder{
        TextView date;
        TextView name;
        TextView num;
        LinearLayout color;
    }
}
