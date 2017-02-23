package cn.zhuangh7.jiluben.activity.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.goodsActivity;

/**
 * Created by Zhuangh7 on 2016/10/27.
 */
public class addGoodsDialog extends DialogFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // LayoutInflater inflater = getActivity().getLayoutInflater();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.addgoodsdialog, null);
        RelativeLayout cancel1 = (RelativeLayout) view.findViewById(R.id.add_goods_dialog_cancel_2);
        TextView cancel2 = (TextView) view.findViewById(R.id.add_goods_dialog_cancel_1);
        cancel2.setClickable(true);
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((goodsActivity)getActivity()).onClickCancel(v);
                dismiss();
            }
        });
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((goodsActivity)getActivity()).onClickCancel(v);
                dismiss();

            }
        });

        RelativeLayout apply1 = (RelativeLayout) view.findViewById(R.id.add_goods_dialog_apply_2);
        TextView apply2 = (TextView) view.findViewById(R.id.add_goods_dialog_apply_1);
        apply1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = a();
                int num = b();
                ((goodsActivity)getActivity()).onClickApply(v,name,num);
                dismiss();
            }
        });
        apply2.setClickable(true);
        apply2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = a();
                int num = b();
                ((goodsActivity)getActivity()).onClickApply(v,name,num);
                dismiss();
            }
        });
        return view;
    }

    public String a(){
        EditText name = (EditText) view.findViewById(R.id.add_goods_dialog_name);
        String n = name.getText().toString();
        if(n == ""&& n.equals("")&&n == null){
            Toast.makeText(getActivity().getApplicationContext(), "多少需要一个名字", Toast.LENGTH_SHORT).show();
        }else{
            return n;
        }
        return null;
    }

    public int b(){
        EditText num = (EditText) view.findViewById(R.id.add_goods_dialog_num);
        if(num.getText().toString().equals("")){
            return 0;
        }else{
            int n = Integer.parseInt(num.getText().toString());
            return n;
        }
    }
}
