package com.bitfault.grabnews.common.util;

public class StringUtils {

    public static boolean isNullEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullEmpty(String str) {
        return !isNullEmpty(str);
    }

}
