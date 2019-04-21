package com.bitfault.grabnews.common.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    private CollectionUtils() {
        // static class
    }

    public static boolean isNullEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotNullEmpty(Collection<?> collection) {
        return !isNullEmpty(collection);
    }

    public static boolean isNullEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNullEmpty(Map<?, ?> map) {
        return !isNullEmpty(map);
    }

}
