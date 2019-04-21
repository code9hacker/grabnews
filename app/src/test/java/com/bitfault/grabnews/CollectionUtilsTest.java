package com.bitfault.grabnews;

import com.bitfault.grabnews.common.util.CollectionUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectionUtilsTest {

    @Test
    public void testIsNullEmpty() {
        Map map = null;
        Assert.assertTrue(CollectionUtils.isNullEmpty(map));
        Collection collection = null;
        Assert.assertTrue(CollectionUtils.isNullEmpty(collection));
        Assert.assertTrue(CollectionUtils.isNullEmpty(new HashMap<>()));
        Assert.assertTrue(CollectionUtils.isNullEmpty(new ArrayList<>()));

        Assert.assertFalse(CollectionUtils.isNullEmpty(Arrays.asList(new int[]{1, 2})));
    }

    @Test
    public void testIsNotNullEmpty() {
        Map map = null;
        Assert.assertFalse(CollectionUtils.isNotNullEmpty(map));
        Collection collection = null;
        Assert.assertFalse(CollectionUtils.isNotNullEmpty(collection));
        Assert.assertFalse(CollectionUtils.isNotNullEmpty(new HashMap<>()));
        Assert.assertFalse(CollectionUtils.isNotNullEmpty(new ArrayList<>()));

        Assert.assertTrue(CollectionUtils.isNotNullEmpty(Arrays.asList(new int[]{1, 2})));
    }

}
