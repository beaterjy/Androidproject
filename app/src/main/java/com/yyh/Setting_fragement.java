package com.yyh;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Setting_fragement extends Fragment {
    private  Button data;
    private  Button clear;
    private  Button collect;
    private EditText apartment;//部门
    private  EditText test_name;//笔录名称
    private TextView data_set;//日期


    private Spinner spinner_type;
    private Spinner spinner_group;
    private Spinner spinner_menber;
    private Spinner spinner_charge;
    private  int mYear, mMonth, mDay;


    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

         View view=inflater.inflate(R.layout.fragement_setting,container,false);

        return view;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        //拿到传进来的数据 初始化账号信息

        //初始化
        Main2Activity.isable=false;
        //笔录类型
        ArrayList<String> list = new ArrayList<String>();
        list.add("研究生复试");
        list.add("普通采访");
        //为下拉列表定义一个适配器
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        //设置下拉菜单样式。
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type=(Spinner)view.findViewById(R.id.spinner_type);
        spinner_type.setAdapter(ad);

        //笔录组织机构
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("五邑大学-智能制造学部");
        list1.add("五邑大学-化学与环境工程学院");
        //为下拉列表定义一个适配器
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list1);
        //设置下拉菜单样式。
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_group=(Spinner)view.findViewById(R.id.spinner_group);
        spinner_group.setAdapter(ad1);

        //参与人员
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("何国辉、高潮、欧阳伟军");
        list2.add("程先生、刘先生、杨先生");
        //为下拉列表定义一个适配器
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list2);
        //设置下拉菜单样式。
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_menber=(Spinner)view.findViewById(R.id.spinner_menber);
        spinner_menber.setAdapter(ad2);

        //参与人员
        ArrayList<String> list3 = new ArrayList<String>();
        list3.add("何国辉");
        list3.add("杨先生");
        //为下拉列表定义一个适配器
        ArrayAdapter<String> ad3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list3);
        //设置下拉菜单样式。
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_charge=(Spinner)view.findViewById(R.id.spinner_charge);
        spinner_charge.setAdapter(ad3);

        //编辑页面
        data_set=(TextView)view.findViewById(R.id.date_set);
        test_name=(EditText)view.findViewById(R.id.itv_testname);
        apartment=(EditText)view.findViewById(R.id.itv_name);

        //初始化日期为当前日期
        Date d=new Date();
        data_set.setText(d.toString());

        //按钮

        clear=(Button)view.findViewById(R.id.clear);
        collect=(Button)view.findViewById(R.id.collect);
        data=(Button)view.findViewById(R.id.data);

        //笔录按钮消息响应
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //创建一个文件
              String data=data_set.getText().toString();
              String name=test_name.getText().toString();
              String apartmentname=apartment.getText().toString();
              String type=spinner_type.getSelectedItem().toString();
              String group=spinner_group.getSelectedItem().toString();
              String number=spinner_menber.getSelectedItem().toString();
              String chager=spinner_charge.getSelectedItem().toString();



              if(TextUtils.isEmpty(test_name.getText())||TextUtils.isEmpty(apartment.getText())){

                Toast.makeText(getContext(),"编辑框不能为空",Toast.LENGTH_SHORT).show();


              }else{


                  String filename=data+"-"+name;

                 if(/*isFileExists(filename)*/true){
                      //把数据存储到数据库

                    //设置可以切换
                     Main2Activity.isable=true;


                      //然后就是进行fragement之间的传值 还有自动切换
                    collecting_fragement cf=new collecting_fragement();

                     FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                     Bundle bundle=new Bundle();
                     bundle.putString("name",name);
                     bundle.putString("data",data);
                     cf.setArguments(bundle);//将bundle绑定到cf面板中

                     fragmentTransaction.replace(R.id.right_layout,cf);
                     fragmentTransaction.commit();

                  }else{
                     System.out.print("文件已存在\n");
                      Toast.makeText(getContext(),"此文件已存在",Toast.LENGTH_SHORT).show();
                  }
              }



            }
        });


        //清空按钮的消息响应
        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                data_set.setText("");
                test_name.setText("");
                apartment.setText("");
            }
        });


        //日期的消息响应
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new
                        DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {
                        mYear=year;
                        if(monthOfYear<=9){
                            mMonth=monthOfYear+1;
                        }else{
                            mMonth=monthOfYear+1;
                        }
                        if(dayOfMonth<=9){
                            mDay= dayOfMonth;
                        }else{
                            mDay=dayOfMonth;
                        }
                        data_set.setText(mYear+"-"+mMonth+"-"+mDay);


                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setCancelable(true);
                dialog.show();
            }
        });





    }
    private boolean isFileExists(String filename){
        try {
            //存储的地址是
            File f=new File(Environment.getExternalStorageDirectory() + "/msc/iat/"+filename);
            if(f.exists()&&f.isDirectory()){
               return  false;
            }else{
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
