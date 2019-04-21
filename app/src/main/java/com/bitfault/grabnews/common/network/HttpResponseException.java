package com.bitfault.grabnews.common.network;

import java.io.IOException;

/**
 * Exception class for all Http !200 responses and others
 */
public class HttpResponseException extends IOException {

    private int responseCode;
    private Object body;

    public HttpResponseException(String message, int responseCode, Object body) {
        super(message);
        this.responseCode = responseCode;
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Object getBody() {
        return body;
    }
}
