package cn.zhuangh7.jiluben.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Created by Zhuangh7 on 2017/2/27.
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
                viewholder.imageView = (ImageView) convertView.findViewById(R.id.item_pic_pic);
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
                //viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName(),viewholder.imageView));
                viewholder.imageView.setAdjustViewBounds(true);
                //老方法用的这个   viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName()));
                viewholder.imageView.setImageBitmap(activity.imageManager.getImageByposition(position));
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
                viewholder.imageView.setImageBitmap(activity.imageManager.getImageByposition(position));
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

    class ViewHolder4Text{
        private TextView editText;
        private TextView name;
    }
    class ViewHolder4Pic{
        private TextView editText;
        private TextView name;
        private ImageView imageView;
    }
}
