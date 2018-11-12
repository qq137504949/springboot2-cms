package com.sdx.yundian.service.impl;

import com.sdx.yundian.entity.UserMsg;
import com.sdx.yundian.service.UserMsgService;
import org.springframework.stereotype.Service;

@Service("userMsgService")
public class UserMsgServiceImpl extends BaseServiceImpl<UserMsg,Integer> implements UserMsgService {
}
