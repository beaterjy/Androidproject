package com.yyh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class collecting_fragement extends Fragment {

    private Button ask;
    private Button answer;
    private Button clear;
    private  Button end;
    private  Button speakText;
    private TextView it_ask,itv_show,it_im;
    private static final String TAG = collecting_fragement.class .getSimpleName();

    private  Boolean isask=true;
    private  String name;
    private  String data;
    private String filename;
    private  SpeechRecognizer mIat;

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String , String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


     View view=inflater.inflate(R.layout.framgement_collecting,container,false);
     return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

         // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(getContext(), SpeechConstant.APPID +"=5cff96ac");
        //初始化文本框
        it_ask=(TextView)view.findViewById(R.id.tv_ask);
        itv_show=(TextView)view.findViewById(R.id.itv_show);
        itv_show.setMovementMethod(ScrollingMovementMethod.getInstance());
        it_im=(TextView)view.findViewById(R.id.tv_im);

        //初始化当前的笔录信息
        try {
            data = getArguments().getString("data");
            name = getArguments().getString("name");
        }catch (Exception e){
          e.printStackTrace();
        }

        it_im.setText("时间:"+data+" 笔录名称:"+name);
        //初始化按钮
        ask=(Button)view.findViewById(R.id.ask);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isask=true;
              speak();


            }
        });
        answer=(Button)view.findViewById(R.id.answer);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             isask=false;
            speak();
            }
        });
        clear=(Button)view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             itv_show.setText("");
            }
        });
        end=(Button)view.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            end();
            }
        });
        speakText=(Button)view.findViewById(R.id.speakText);
        speakText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               speakText();

            }
        });
    }
    private void speakText(){
        //创建对象
        SpeechSynthesizer mTts=SpeechSynthesizer.createSynthesizer(getActivity(),null);
        //整合参数
        mTts.setParameter(SpeechConstant. VOICE_NAME, "vixyun" ); // 设置发音人
        mTts.setParameter(SpeechConstant. SPEED, "50" );// 设置语速
        mTts.setParameter(SpeechConstant. VOLUME, "80" );// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant. ENGINE_TYPE, SpeechConstant. TYPE_CLOUD);

        //设置保存的位置
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
        mTts.startSpeaking( itv_show.getText().toString(), new MySynthesizerListener()) ;
    }
    class MySynthesizerListener implements SynthesizerListener{

        @Override
        public void onSpeakBegin() {
            ShowTip("开始播放");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {
        ShowTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
        ShowTip("继续播放");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if(speechError==null){
              ShowTip("播放完成");
            }
            else if(speechError!=null){
                //显示错误的结果
                ShowTip(speechError.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }

    //结束谈话
    private void end(){
        //首先暂停监听
        //首先判断是否打开了监听
        if(mIat!=null) {
            mIat.stopListening();
        }
        //首先弹出消息框让用户判断是否进行结束操作
        AlertDialog.Builder makesure=new AlertDialog.Builder(getActivity());
        makesure.setTitle("提示");
        makesure.setMessage("是否结束本次谈话");
        //首先设立确定按钮
        makesure.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //然后就是把文件传输入新建的文件中
                try {
                    FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory() +
                            "/msc/iat/" + name + "/" +filename + "笔录.txt");
                    fileOutputStream.write(itv_show.getText().toString().getBytes());
                    fileOutputStream.close();
                    ShowTip("已保存到" +Environment.getExternalStorageDirectory() +
                            "/msc/iat/" + name + "/" +filename + "笔录.txt" );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //切换到设置页面
                Setting_fragement setting_fragement=new Setting_fragement();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.right_layout,setting_fragement);
                fragmentTransaction.commit();



            }
        });
        //设置取消按钮
        makesure.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShowTip("本次操作取消");
            }
        });
        //设置提示框不可返回消失
        makesure.setCancelable(false).create();
        //显示提示框
        makesure.show();

    }


    private void speak(){
        //创建识别对象
         mIat=SpeechRecognizer.createRecognizer(getActivity(),null);

        //设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言设置为普通话
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置为短信与日常用语
        mIat.setParameter(SpeechConstant.DOMAIN,"iat");
        mIat.startListening(mRecoListener);

    }
    private RecognizerListener mRecoListener=new RecognizerListener() {
        // 听写结果回调接口 (返回Json 格式结果，用户可参见附录 13.1)；
    //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
    //关于解析Json的代码可参见 Demo中JsonParser 类；
    //isLast等于true 时会话结束。

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
        ShowTip("开始录音");
        }

        @Override
        public void onEndOfSpeech() {
            it_ask.setText("请按开始并说话");
        ShowTip("结束录音");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

                String result=recognizerResult.getResultString();
                //ShowTip(result);
                System.out.print("没解析的："+result);
                String text=JsonParser.parseIatResult(result);
                System.out.print("解析后："+text);
                String sn=null;
                try{
                    JSONObject resultJson=new JSONObject(recognizerResult.getResultString());
                    sn=resultJson.optString("sn");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                mIatResults.put(sn,text);//每得到一句话添加到结果文本中
                StringBuffer resultBuffer=new StringBuffer();
                if(isask) {
                    resultBuffer.append("问：");
                }
                else{
                    resultBuffer.append("答：");
                }
                for(String key:mIatResults.keySet()){
                    resultBuffer.append(mIatResults.get(key));
                }
                it_ask.setTextColor(Color.RED);
                it_ask.setText(resultBuffer.toString());
                if(b) {
                    itv_show.append(it_ask.getText().toString() + "\n");//设置输入框的文本
                }
        }

        @Override
        public void onError(SpeechError speechError) {
            ShowTip(speechError.getPlainDescription(true)) ;
            // 获取错误码描述
            Log. e(TAG, "error.getPlainDescription(true)==" + speechError.getPlainDescription(true ));

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    private void ShowTip(String data) {
        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
    }
}
