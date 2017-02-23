package cn.zhuangh7.jiluben.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.zhuangh7.jiluben.R;
import cn.zhuangh7.jiluben.activity.classes.addGoodsDialog;
import cn.zhuangh7.jiluben.activity.classes.dataBase;

/**
 * Created by Zhuangh7 on 2016/10/25.
 */
public class addActivity extends Activity{
    private Button cancel;
    private EditText shopName;
    private EditText shopPos;
    private EditText shopTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        cancel = (Button)findViewById(R.id.add_button_cancel);
        //apply = (Button) findViewById(R.id.add_button_apply);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1);
                finish();
            }
        });

    }
    public void onApply(View view){
        shopName = (EditText)findViewById(R.id.add_name);
        shopTel = (EditText) findViewById(R.id.add_tel);
        shopPos = (EditText) findViewById(R.id.add_pos);

        String name = shopName.getText().toString();
        String pos = shopPos.getText().toString();
        String tel = shopTel.getText().toString();
        //Toast.makeText(getApplicationContext(),"get name : "+name+" ; get pos : "+pos+" ; get tel : "+tel,Toast.LENGTH_SHORT).show();

        if(name.equals("")){
            Toast.makeText(getApplicationContext(),"多少写个名字是不是",Toast.LENGTH_LONG).show();
        }else if (name == null) {
            Toast.makeText(getApplicationContext(),"多少写个名字是不是",Toast.LENGTH_LONG).show();
        }else
        {
            dataBase db = new dataBase(getApplicationContext());
            db.addShop(name, pos, tel);
            setResult(0);
            finish();
        }
    }

}
