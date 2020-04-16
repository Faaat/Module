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

    /**
     * @param s1              字符串
     * @param s2              字符串
     * @param isIgnoreCapital 是否忽略大写
     * @return 相等:true;不相等:false
     */
    public boolean compareText(String s1, String s2, boolean isIgnoreCapital) {
        if (!isIgnoreCapital) {
            return s1.equals(s2);
        }
        if (s1.length() != s2.length()) {
            return false;
        }
        for (int i = 0; i < s1.length(); i++) {
            char pswChar = s1.charAt(i);
            char checkChar = s2.charAt(i);
            if (pswChar != checkChar) {
                if ((pswChar >= 'a' && pswChar <= 'z') || (pswChar >= 'A' && pswChar <= 'Z')) {
                    if (pswChar + 32 != checkChar && pswChar - 32 != checkChar) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
