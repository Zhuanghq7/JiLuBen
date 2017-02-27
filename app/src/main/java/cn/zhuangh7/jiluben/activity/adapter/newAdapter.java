package cn.zhuangh7.jiluben.activity.adapter;

import android.app.Activity;
import android.content.Context;
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
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mlist.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        items item = mlist.get(position);
        if(convertView==null){
            if(item.isIfPic()){
                ViewHolder4Pic viewholder = new ViewHolder4Pic();
                convertView = mInflater.inflate(R.layout.itemlayout_pictures, null);
                viewholder.name = (TextView) convertView.findViewById(R.id.item_pic_text_name);
                viewholder.editText = (TextView) convertView.findViewById(R.id.item_pic_text_text);
                viewholder.imageView = (ImageView) convertView.findViewById(R.id.item_pic_pic);
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
                viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName(),viewholder.imageView));
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

            }else{
                ViewHolder4Text viewholder = (ViewHolder4Text) convertView.getTag();
                viewholder.name.setText(item.getName());
                viewholder.editText.setText(item.getText());
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
        private ImageView imageView;
        private TextView name;
    }
}
