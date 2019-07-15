package com.yyh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class login extends BaseActivity {
private  EditText  id_Edit;
private  EditText password_Edit;
private CheckBox  remenber;
private SharedPreferences pref;
private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //得到 pref
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        //找到对应的编辑框
       id_Edit=(EditText) findViewById(R.id.ID);
       password_Edit=(EditText) findViewById(R.id.password);
        //找到对应的checkbox按钮
        remenber=(CheckBox)findViewById(R.id.remenber);
        //首先我们先进行是否记住密码的初始化
        boolean isRemenber=pref.getBoolean("remenber_password",false);
        if(isRemenber){
        String account=pref.getString("account","");
        String password=pref.getString("password","");

        id_Edit.setText(account);
        password_Edit.setText(password);
        remenber.setChecked(isRemenber);
        }
        //登录界面
        final Button login =(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID;
                String password;
                boolean istrue=true;
           ID=id_Edit.getText().toString();
           password=password_Edit.getText().toString();
           editor=pref.edit();
            //判断是否要进行记住账号密码
               if(remenber.isChecked()){
                 editor.putBoolean("remenber_password",true);
                 editor.putString("account",ID);
                 editor.putString("password",password);
               }
               else{
                   editor.clear();
               }
               //执行输入
               editor.apply();
            //如果密码正确
                if(istrue) {
                    Intent intent = new Intent(com.yyh.login.this, Main2Activity.class);
                    intent.putExtra("trueID", ID);
                    startActivity(intent);
                }
               //如果密码错误
               else{
                    //消息提醒
                    Toast.makeText(login.this,"账号或密码出错",Toast.LENGTH_LONG).show();
                }

            }
        });
        //注册
        Button sign =(Button) findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //找回密码
        Button forget =(Button) findViewById(R.id.login_error);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //看密码
        Button check=(Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            password_Edit.setInputType(InputType.TYPE_CLASS_TEXT);

            }
        });



    }
}
