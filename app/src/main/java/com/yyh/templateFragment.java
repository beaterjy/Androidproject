package com.yyh;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ldoublem.loadingviewlib.view.LVGearsTwo;
import com.yyh.Utils.NormalUtils;
import com.yyh.Utils.WordIO;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class templateFragment extends Fragment implements View.OnClickListener{

    private Button addTemplate;
    private NiceSpinner titleSpinner;
    private NiceSpinner answerSpinner;
    private Map<String, String> titleMap;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> titlePosition = new ArrayList<>();

    public templateFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTemplate = view.findViewById(R.id.addTemplate);
        titleSpinner = view.findViewById(R.id.titleSpinner);
        answerSpinner = view.findViewById(R.id.answerSpinner);
        addTemplate.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template, container, false);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 666){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                //将模板复制到模板文件夹里
                copyTemplate(uri);
                WordIO io = new WordIO(NormalUtils.getPath(getContext(), uri));
                titleMap = io.getTitle();
                for (String key : titleMap.keySet()) {
                    title.add(key);
                    titlePosition.add(titleMap.get(key));
                }
                titleSpinner.attachDataSource(title);
//                Log.d("this way", NormalUtils.getPath(getContext(), uri));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addTemplate:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 666);
                break;
            default:
                break;
        }
    }

    /**
     * 将模板复制到模板文件夹里
     * @param uri 文件的路径
     */
    private void copyTemplate(Uri uri){
        if(uri != null){
            File file = new File(Environment.getExternalStorageDirectory() + "/msc/templates/");
            if(!file.exists()){
                file.mkdirs();
            }
            String name = NormalUtils.getFileNameWithSuffix(NormalUtils.getPath(getContext(), uri));
            //将模板复制到模板文件夹里
            NormalUtils.copyFile(NormalUtils.getPath(getContext(), uri),
                    file.getAbsolutePath() + "/" +name);
        }

    }
}
