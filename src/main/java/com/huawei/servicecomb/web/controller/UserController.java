package com.huawei.servicecomb.web.controller;

import com.huawei.servicecomb.entity.User;
import com.huawei.servicecomb.service.UserTokenService;
import com.kundeyt.util.base.pojo.BaseReturnParam;
import com.kundeyt.util.common.ReturnUtil;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DEV-TOM
 * @date 2019-07-19
 * @version 0.0.1
 * 返回登录用户的UserId
 */

@RequestMapping("/user")
@RestSchema(schemaId = "ApiGetwayUser")
public class UserController {

    /**
     * user token service
     */
    @Autowired
    private UserTokenService userTokenService;

    /**
     * get user info by token
     * @return user
     */
    @RequestMapping("/info")
    @ResponseBody
    public BaseReturnParam<User> getUserInfo(){
        User user = this.userTokenService.getCurentUser();
        return ReturnUtil.success(user);
    }
}
