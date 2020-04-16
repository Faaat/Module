package com.felix.baselibrary.Utils;

import android.text.TextUtils;

public class StringUtils {
    private static StringUtils instance;

    private StringUtils() {
    }

    public static StringUtils getInstance() {
        if (instance == null) {
            synchronized (StringUtils.class) {
                if (instance == null) {
                    instance = new StringUtils();
                }
            }
        }
        return instance;
    }

    public boolean isEmpty(String s) {
        return s.equals("") || TextUtils.isEmpty(s);
    }
}
