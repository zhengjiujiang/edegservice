package com.huawei.servicecomb;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.Response.Status;

import com.huawei.servicecomb.entity.User;
import com.huawei.servicecomb.service.UserTokenService;
import org.apache.servicecomb.common.rest.filter.HttpServerFilter;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.swagger.invocation.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证：对于API接口，需要进行认证。UI静态页面不提供认证。
 *
 */
public class AuthServerFilter implements HttpServerFilter {

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public int getOrder() {
        // before args is extracted
        return -200;
    }


    @Override
    public Response afterReceiveRequest(Invocation invocation, HttpServletRequestEx httpServletRequestEx) {
        // 校验身份逻辑
        if (invocation.getOperationMeta().getMicroserviceQualifiedName().equals("scm-tender.bids.getBidInfosForMainPage")) {
            // 登录接口，不需要认证
            return null;
        }

        if (invocation.getOperationMeta().getMicroserviceQualifiedName().equals("scm-common.CommonJar.getrandom")) {
            // 登录接口，不需要认证
            return null;
        }
        String token = this.userTokenService.getToken();
        if(token!=null && token!="" && !token.isEmpty()) {
            User user = this.userTokenService.getCurentUser();
            if (user == null || user.getUserId().isEmpty()) {
                return Response.create(Status.FORBIDDEN, "Not Authenticated.");
            }
            // 隐式传参，后面这次调用所有地方都可以获取
            invocation.addContext("userId", user.getUserId());
        }
        return null;
    }
}
