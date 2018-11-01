package com.sdx.yundian.service;

import com.sdx.yundian.entity.User;

public interface UserService extends BaseService<User,Integer>  {

   /**
    * 后台用户密码登录
    * @param userName 用户名
    * @param password  密码
    * @return 验证通过返回用户信 否则 null
    */
   User adminLogin(String userName,String password);
}
