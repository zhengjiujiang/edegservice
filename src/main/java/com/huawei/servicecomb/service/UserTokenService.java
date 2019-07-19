package com.huawei.servicecomb.service;

import com.alibaba.fastjson.JSONObject;
import com.huawei.servicecomb.entity.User;
import com.umu360.sso.utils.JwtTokenUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserTokenService
 * @author DEV-TOM
 */
@Service
public class UserTokenService {


    @Autowired
    private HttpServletRequestEx request;

    /**
     *  Token处理类库
     */
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * token name
     */
    private final static String  TOKENKEY = "token";


    public String getToken() {
        String authToken = request.getHeader(TOKENKEY);
        if (StringUtils.isEmpty(authToken)) {
            authToken = request.getParameter(TOKENKEY);
        }
        return authToken;
    }

    /**
     * 获取用户信息（供应商）
     *
     * @return file system userInfo
     */
    public User getCurentUser() {
        /**
         * 通过sso获取用信息
         */
        User user = new User();
        String token  = getToken();
        if(token!=null &&  !token.isEmpty()) {
            JSONObject ssoUser = null;
            try {
                ssoUser = jwtTokenUtils.getUserByToken(token);
            } catch (JoseException e) {
                e.printStackTrace();
            }

            try {
                user.setUserId(ssoUser.getJSONArray("id").getString(0));
                if (user.getUserId() == null || user.getUserId().isEmpty()) {
                    new Exception("user.login.err").printStackTrace();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return  user ;
    }

}
