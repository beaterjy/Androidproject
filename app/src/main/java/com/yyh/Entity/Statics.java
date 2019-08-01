package com.yyh.Entity;

import android.os.Environment;

public class Statics {
    //视频录制的路径
    public static String PATH_VIDEO = Environment.getExternalStorageDirectory() + "/msc/";
    //访谈放置的路径
    public static String PATH_INTERVIEW = Environment.getExternalStorageDirectory() + "/msc/iat/";
    public static String PATH_TEMPLATES= Environment.getExternalStorageDirectory() + "/msc/templates/";
    public static String PATH_HTML= Environment.getExternalStorageDirectory() + "/msc/html/";
    public static String PATH_CONVERT= Environment.getExternalStorageDirectory() + "/msc/convert/";
    public static String PATH_TbsReaderTemp= Environment.getExternalStorageDirectory() + "/msc/TbsReaderTemp/";

}
