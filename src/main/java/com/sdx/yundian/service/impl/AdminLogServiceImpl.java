package com.sdx.yundian.service.impl;

import com.sdx.yundian.entity.AdminLog;
import com.sdx.yundian.service.AdminLogService;
import org.springframework.stereotype.Service;

@Service(value = "adminLogService")
public class AdminLogServiceImpl extends BaseServiceImpl <AdminLog, Integer> implements AdminLogService {
}
