package com.bitfault.grabnews.common.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        HttpMethod.GET,
        HttpMethod.POST,
        HttpMethod.DELETE,
        HttpMethod.PUT
})
@Retention(RetentionPolicy.SOURCE)
public @interface HttpMethod {

    int GET = 1;
    int POST = 2;
    int DELETE = 3;
    int PUT = 4;

}
