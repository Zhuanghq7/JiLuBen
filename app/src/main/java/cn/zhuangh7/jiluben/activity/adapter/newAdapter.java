package cn.zhuangh7.jiluben.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Using to provid a two kind of layout itemlist

 * Created by Zhuangh7 on 2017/2/27.HUAWEI_SB
 */

public class newAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<items> mlist;
    private needsActivity activity;

    public newAdapter(Context context,List<items> list,needsActivity activity){
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mlist = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        items item = mlist.get(position);
        if (item.isIfPic()) {
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("HUAWEI", "SB");
        Log.e("HUAWEI", "SB");
        items item = mlist.get(position);
        activity.imageManager.setPosition(position);
        //得到item
        if(convertView==null){
            if(item.isIfPic()){
                ViewHolder4Pic viewholder = new ViewHolder4Pic();
                convertView = mInflater.inflate(R.layout.itemlayout_pictures, null);
                viewholder.name = (TextView) convertView.findViewById(R.id.item_pic_text_name);
                viewholder.editText = (TextView) convertView.findViewById(R.id.item_pic_text_text);
                //viewholder.name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                viewholder.imageView = (ImageView) convertView.findViewById(R.id.item_pic_pic);
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
                //viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName(),viewholder.imageView));
                viewholder.imageView.setAdjustViewBounds(true);
                //老方法用的这个   viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName()));
                Bitmap tempbitmap = activity.imageManager.getImageByposition(position);
                if(tempbitmap==null){
                    viewholder.imageView.setImageResource(R.drawable.cancel);
                }else{
                    viewholder.imageView.setImageBitmap(tempbitmap);
                }
                convertView.setTag(viewholder);
            }else{
                ViewHolder4Text viewholder = new ViewHolder4Text();
                convertView = mInflater.inflate(R.layout.itemlayout_text, null);
                viewholder.name = (TextView) convertView.findViewById(R.id.item_text_name);
                viewholder.editText = (TextView) convertView.findViewById(R.id.item_text_text);
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
                convertView.setTag(viewholder);
            }
        }else{
            if (item.isIfPic()) {
                ViewHolder4Pic viewholder = (ViewHolder4Pic) convertView.getTag();
                viewholder.editText.setText(item.getText());
                viewholder.name.setText(item.getName());
                //viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName()));
                Bitmap tempbitmap = activity.imageManager.getImageByposition(position);
                if(tempbitmap==null){
                    viewholder.imageView.setImageResource(R.drawable.cancel);
                }else{
                    viewholder.imageView.setImageBitmap(tempbitmap);
                }
                convertView.setTag(viewholder);
                return convertView;

            }else{
                ViewHolder4Text viewholder = (ViewHolder4Text) convertView.getTag();
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
                convertView.setTag(viewholder);
                return convertView;
            }
        }
        return convertView;
    }

    private class ViewHolder4Text{
        private TextView editText;
        private TextView name;
    }
    private class ViewHolder4Pic{
        private TextView editText;
        private TextView name;
        private ImageView imageView;
    }



}
