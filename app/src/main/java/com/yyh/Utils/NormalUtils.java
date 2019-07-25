package com.yyh.Utils;

import android.widget.Toast;

import com.yyh.ActivityCollector;
import com.yyh.BaseActivity;

public class NormalUtils {

    private static Toast mToast;

    public static void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

}
