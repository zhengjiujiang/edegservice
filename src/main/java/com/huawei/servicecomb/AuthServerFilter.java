package com.huawei.servicecomb;

import org.apache.servicecomb.common.rest.filter.HttpServerFilter;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.swagger.invocation.Response;

public class AuthServerFilter implements HttpServerFilter {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Response afterReceiveRequest(Invocation invocation, HttpServletRequestEx httpServletRequestEx) {
        // 校验身份逻辑

        // 隐式传参，后面这次调用所有地方都可以获取
        // invocation.addContext("x-user-id", "123456");
        return null;
    }
}
