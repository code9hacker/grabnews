package com.bitfault.grabnews;

import com.bitfault.grabnews.common.util.StringUtils;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testIsNullEmpty() {
        Assert.assertTrue(StringUtils.isNullEmpty(null));
        Assert.assertTrue(StringUtils.isNullEmpty(""));
        Assert.assertFalse(StringUtils.isNullEmpty("alpha"));
    }

    @Test
    public void testIsNotNullEmpty() {
        Assert.assertFalse(StringUtils.isNotNullEmpty(null));
        Assert.assertFalse(StringUtils.isNotNullEmpty(""));
        Assert.assertTrue(StringUtils.isNotNullEmpty("alpha"));
    }

}
