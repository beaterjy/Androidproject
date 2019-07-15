package com.yyh;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends BaseActivity implements View.OnClickListener {
    private Button set;
    private Button record;
    private Button proofread;
    private Button search;
    private Button paint;
    private Button manage;
    private Button system;
    private ImageButton account;
    private TextView account_infor;

   public static boolean isable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //首先让fragement显示的界面
         replaceFragment(new Setting_fragement());
        //对上部栏目的组件进行初始化
        account=(ImageButton)findViewById(R.id.account);
        account_infor=(TextView)findViewById(R.id.account_infor);

        //得到传入的账号信息
        Intent intent=getIntent();
        String ID=intent.getStringExtra("trueID");
        account_infor.setText(ID);
        //对按钮进行注册
        set =(Button) findViewById(R.id.set);
        set.setOnClickListener(this);

        record =(Button) findViewById(R.id.record);
        record.setOnClickListener(this);

        proofread =(Button) findViewById(R.id.Proofread);
        proofread.setOnClickListener(this);

        search =(Button) findViewById(R.id.search);
        search.setOnClickListener(this);

        paint =(Button) findViewById(R.id.paint);
        paint.setOnClickListener(this);

        manage =(Button) findViewById(R.id.manage);
        manage.setOnClickListener(this);

        system =(Button) findViewById(R.id.system);
        system.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set:
                replaceFragment(new Setting_fragement());
                break;

            case R.id.record:
                start_record();
                break;

            case R.id.Proofread:
                replaceFragment(new Setting_fragement());
                break;

            case R.id.search:
                replaceFragment(new Setting_fragement());
                break;

            case R.id.paint:
                replaceFragment(new Setting_fragement());
                break;

            case R.id.manage:
                replaceFragment(new Setting_fragement());
                break;

            case R.id.system:
                replaceFragment(new Setting_fragement());
                break;

            default:
                break;

        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout,fragment);
        transaction.commit();
    }

    public void start_record(){
        //首先判断是否能进行录屏
        if(!isable){
            Toast.makeText(this,"请先进行笔录设置",Toast.LENGTH_LONG).show();

        }else{
            replaceFragment(new collecting_fragement());
        }
    }
}
