package com.yyh.Utils;



import android.util.Log;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordIO {

    //模板文件地址
    private static String inputUrl;

    public WordIO(String url) {
        inputUrl = url;
    }

    public Map<String, String> getTitle(){

        try {
            //解析docx模板并获取document对象
            Map<String, String> map = new LinkedHashMap<String, String>();
            String patternTitle;
            String patternTitlePosition;
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取整个文本对象
            List<XWPFParagraph> allParagraph = document.getParagraphs();

            //获取XWPFRun对象输出整个文本内容
            StringBuffer tempText = new StringBuffer();
            for (XWPFParagraph xwpfParagraph : allParagraph) {
                List<XWPFRun> runList = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : runList) {
                    tempText.append(xwpfRun.toString());
                }
            }
            //正则表达式找

            patternTitle = "(?<=\\{).*?(?=\\})";
            patternTitlePosition = "\\{([^}]*)\\}";

            Pattern rTitle = Pattern.compile(patternTitle);
            Pattern rTitlePosition = Pattern.compile(patternTitlePosition);
            Matcher mTitle = rTitle.matcher(tempText.toString());


            while(mTitle.find()){
//                Log.d("this way", m.group());
                String title = mTitle.group();
                String titlePosition = "";
                Matcher mTitlePosition = rTitlePosition.matcher(title);
                if (mTitlePosition.find()){
                    titlePosition = mTitlePosition.group();
                }
                map.put(title, titlePosition);
            }
            return map;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;  //return null报错
    }

    /**
     * 处理段落
     * @param paragraphList
     * @param param
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

}
