package com.sdx.yundian.service.impl;

import com.sdx.yundian.entity.Systems;
import com.sdx.yundian.service.SystemService;
import org.springframework.stereotype.Service;

@Service(value = "systemService")
public class SystemServiceImpl extends BaseServiceImpl <Systems, Integer> implements SystemService {
}
