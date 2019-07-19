package com.huawei.servicecomb;


import com.huawei.servicecomb.entity.User;
import com.huawei.servicecomb.service.UserTokenService;
import org.apache.servicecomb.edge.core.DefaultEdgeDispatcher;

import com.netflix.config.DynamicPropertyFactory;

import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiDispatcher extends DefaultEdgeDispatcher {
    private static final String KEY_ENABLED = "servicecomb.http.dispatcher.edge.api.enabled";

    @Autowired
    private UserTokenService userTokenService;
    @Override
    public int getOrder() {
        return 11;
    }

    @Override
    public boolean enabled() {
        return DynamicPropertyFactory.getInstance().getBooleanProperty(KEY_ENABLED, false).get();
    }

    @Override
    protected void onRequest(RoutingContext context) {
//        if (context.request().uri().toLowerCase().equals("/user/info")) {
//            // 登录接口，不需要认证
//            context.response().setStatusCode(200);
//            context.response().push();
//            context.response().end();
//            return;
//        }

        String token =this.userTokenService.getToken();
        if(token!=null && token!="" && !token.isEmpty()) {
            User user = this.userTokenService.getCurentUser();
            if (user == null || user.getUserId().isEmpty()) {
                context.response().setStatusCode(302);
                context.response().putHeader("Location", "/login");
                context.response().end();
                return;
            }
            context.request().headers().add("userId", user.getUserId());
        }
        super.onRequest(context);
    }
}