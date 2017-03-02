package cn.zhuangh7.jiluben.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.items;
import cn.zhuangh7.jiluben.activity.needsActivity;

/**
 * Using to provid a two kind of layout itemlist
 * <p>
 * Created by Zhuangh7 on 2017/3/2.HUAWEI_SB
 */

public class editTextDialog extends Dialog {
    private TextView date;
    private TextView main;
    private RelativeLayout delete1;
    private TextView delete2;
    private RelativeLayout cancel1;
    private TextView cancel2;
    private RelativeLayout accept1;
    private TextView accept2;
    private onDeleteOnclickListener deleteOnclickListener;
    private onCancelOnclickListener cancelOnclickListener;
    private onAcceptOnclickListener acceptOnclickListener;
    private items item;

    public editTextDialog(Context context, items item) {
        super(context, R.style.MyDialog);
        this.item = item;
    }

    public editTextDialog setOnDeleteOnclickListener(onDeleteOnclickListener onDeleteOnclickListener){
        this.deleteOnclickListener = onDeleteOnclickListener;
        return this;
    }

    public editTextDialog setOnCancelOnclickListener(onCancelOnclickListener onCancelOnclickListener) {
        this.cancelOnclickListener = onCancelOnclickListener;
        return this;
    }

    public editTextDialog setOnAcceptOnclickListener(onAcceptOnclickListener onAcceptOnclickListener) {
        this.acceptOnclickListener = onAcceptOnclickListener;
        return this;
    }

    public interface onDeleteOnclickListener {
        public void onDeleteClick();
    }

    public interface onCancelOnclickListener {
        public void onCancelClick();
    }

    public interface onAcceptOnclickListener{
        public void onAcceptClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_text_edit);
        //setCanceledOnTouchOutside(false);

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        date = (TextView) findViewById(R.id.detail_text_date);
        main = (EditText) findViewById(R.id.detail_text_main);
        delete1 = (RelativeLayout) findViewById(R.id.detail_text_delete_1);
        delete2 = (TextView) findViewById(R.id.detail_text_delete_2);
        accept1 = (RelativeLayout) findViewById(R.id.detail_text_accept_1);
        accept2 = (TextView) findViewById(R.id.detail_text_accept_2);
        cancel1 = (RelativeLayout) findViewById(R.id.detail_text_cancel_1);
        cancel2 = (TextView) findViewById(R.id.detail_text_cancel_2);
    }

    private void initData(){
        if(item==null){
            date.setText("ERROR!");
            main.setText("数据错误！！！");
        }else{
            date.setText(needsActivity.transDate(item.getName()));
            main.setText(item.getText());
        }
    }
    private void initEvent(){
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteOnclickListener!=null){
                    deleteOnclickListener.onDeleteClick();
                }
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteOnclickListener!=null){
                    deleteOnclickListener.onDeleteClick();
                }
            }
        });
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelOnclickListener!=null){
                    cancelOnclickListener.onCancelClick();
                }
            }
        });
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelOnclickListener!=null){
                    cancelOnclickListener.onCancelClick();
                }
            }
        });
        accept1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptOnclickListener!=null){
                    acceptOnclickListener.onAcceptClick();
                }
            }
        });
        accept2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptOnclickListener!=null){
                    acceptOnclickListener.onAcceptClick();
                }
            }
        });
    }
    public String getMainText(){
        return main.getText().toString();
    }
}
