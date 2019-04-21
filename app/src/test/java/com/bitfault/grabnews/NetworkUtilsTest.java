package com.bitfault.grabnews;

import com.bitfault.grabnews.common.network.BaseRequest;
import com.bitfault.grabnews.common.network.HttpMethod;
import com.bitfault.grabnews.common.network.NetworkUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import okhttp3.Request;

public class NetworkUtilsTest {

    @Before
    public void setup() {

    }

    @Test
    public void testSetHttpMethodToCall() {
        BaseRequest testRequest = new BaseRequest.Builder("http://www.google.com", "").build();
        Request.Builder getRequest = new Request.Builder().url(testRequest.getUrl());
        NetworkUtils.setHttpMethodToCall(getRequest, HttpMethod.GET, testRequest);
        Assert.assertEquals("GET", getRequest.build().method());

        Request.Builder postRequest = new Request.Builder();
        NetworkUtils.setHttpMethodToCall(getRequest, HttpMethod.POST, testRequest);
        Assert.assertEquals("POST", getRequest.build().method());

        Request.Builder deleteRequest = new Request.Builder();
        NetworkUtils.setHttpMethodToCall(getRequest, HttpMethod.DELETE, testRequest);
        Assert.assertEquals("DELETE", getRequest.build().method());

        Request.Builder putRequest = new Request.Builder();
        NetworkUtils.setHttpMethodToCall(getRequest, HttpMethod.PUT, testRequest);
        Assert.assertEquals("PUT", getRequest.build().method());
    }

    @Test
    public void testGetUrlWithQueryParams() {
        BaseRequest testRequest1 = new BaseRequest.Builder("http://example.com", "")
                .build();
        Assert.assertEquals("http://example.com", NetworkUtils.getUrlWithQueryParams(testRequest1));

        BaseRequest testRequest2 = new BaseRequest.Builder("http://example.com", "")
                .param("alpha", "one")
                .param("beta", "two")
                .build();

        Assert.assertEquals("http://example.com?alpha=one&beta=two", NetworkUtils.getUrlWithQueryParams(testRequest2));

        BaseRequest testRequest3 = new BaseRequest.Builder("http://example.com?", "")
                .param("alpha", "one")
                .param("beta", "two")
                .build();

        Assert.assertEquals("http://example.com?alpha=one&beta=two", NetworkUtils.getUrlWithQueryParams(testRequest3));

        BaseRequest testRequest4 = new BaseRequest.Builder("http://example.com?alpha=one", "")
                .param("beta", "two")
                .build();

        Assert.assertEquals("http://example.com?alpha=one&beta=two", NetworkUtils.getUrlWithQueryParams(testRequest4));

    }
}
