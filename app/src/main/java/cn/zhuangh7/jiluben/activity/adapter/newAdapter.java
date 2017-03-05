package cn.zhuangh7.jiluben.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.dialog.editTextDialog;
import cn.zhuangh7.jiluben.activity.dialog.showPictureDialog;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Using to provid a two kind of layout itemlist

 * Created by Zhuangh7 on 2017/2/27.HUAWEI_SB
 */

public class newAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<items> mlist;
    private needsActivity activity;
    private Context context;

    public newAdapter(Context context,List<items> list,needsActivity activity){
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final items item = mlist.get(position);
        activity.imageManager.setPosition(position);
        Log.d("HUAAWEI SB", "HUSWEI SB");
        Log.i("HUAAWEI SB", "HUSWEI SB");
        //得到item
        if(convertView==null){
            if(item.isIfPic()){
                ViewHolder4Pic viewholder = new ViewHolder4Pic();
                convertView = mInflater.inflate(R.layout.itemlayout_pictures, null);
                //TODO 填充layout
                viewholder.name = (TextView) convertView.findViewById(R.id.item_pic_text_name);
                viewholder.editText = (TextView) convertView.findViewById(R.id.item_pic_text_text);
                viewholder.imageView = (ImageView) convertView.findViewById(R.id.item_pic_pic);
                //viewholder.button_text = (LinearLayout) convertView.findViewById(R.id.item_picture_text_edit);
                viewholder.day = (TextView) convertView.findViewById(R.id.textview_day1);
                //TODO get views
                viewholder.name.setText(needsActivity.transDate(item.getName()));
                viewholder.editText.setText(item.getText());
                viewholder.imageView.setAdjustViewBounds(true);
                viewholder.day.setText(item.getDay()+"日");
                final Bitmap tempbitmap = activity.imageManager.getImageByposition(position);
                if(tempbitmap==null){
                    viewholder.imageView.setImageResource(R.drawable.cancel);
                }else{
                    viewholder.imageView.setImageBitmap(tempbitmap);
                    viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new showPictureDialog(context, item, tempbitmap).show();
                        }
                    });
                }

                //TODO views' data set
                convertView.setTag(viewholder);
            }else{//not a picture item
                ViewHolder4Text viewholder = new ViewHolder4Text();
                convertView = mInflater.inflate(R.layout.itemlayout_text, null);
                viewholder.name = (TextView) convertView.findViewById(R.id.item_text_name);
                viewholder.editText = (TextView) convertView.findViewById(R.id.item_text_text);
                viewholder.day = (TextView) convertView.findViewById(R.id.textview_day2);
                //get view
                viewholder.name.setText(needsActivity.transDate(item.getName()));
                viewholder.editText.setText(item.getText());
                viewholder.day.setText(item.getDay()+"日");
                convertView.setTag(viewholder);
            }
        }else{//convertView !=null
            if (item.isIfPic()) {
                ViewHolder4Pic viewholder = (ViewHolder4Pic) convertView.getTag();
                viewholder.editText.setText(item.getText());
                viewholder.name.setText(needsActivity.transDate(item.getName()));
                viewholder.day.setText(item.getDay()+"日");
                //viewholder.imageView.setImageBitmap(activity.setImageByName(item.getName()));
                final Bitmap tempbitmap = activity.imageManager.getImageByposition(position);
                if(tempbitmap==null){
                    viewholder.imageView.setImageResource(R.drawable.cancel);
                }else{
                    viewholder.imageView.setImageBitmap(tempbitmap);
                    viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new showPictureDialog(context, item, tempbitmap).show();
                        }
                    });
                }
                convertView.setTag(viewholder);
                return convertView;

            }else{//not a picture item
                ViewHolder4Text viewholder = (ViewHolder4Text) convertView.getTag();
                viewholder.name.setText(needsActivity.transDate(item.getName()));
                viewholder.editText.setText(item.getText());
                viewholder.day.setText(item.getDay()+"日");
                convertView.setTag(viewholder);
                return convertView;
            }
        }
        return convertView;
    }

    private class ViewHolder4Text{
        private TextView editText;
        private TextView name;
        private TextView day;
    }
    private class ViewHolder4Pic{
        private TextView editText;
        private TextView name;
        private ImageView imageView;
        private TextView day;
    }



}
