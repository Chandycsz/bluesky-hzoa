package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.OverTime;
import com.bluesky.framework.account.account.OverTimeManager;
import com.bluesky.framework.domain.model.account.OverTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DubboService
public class OverTimeManagerImpl implements OverTimeManager {
    @Autowired
    private OverTimeRepository overTimeRepository;
    @Override
    public void addOverTime(OverTime overTime) {
        this.overTimeRepository.addOverTime(overTime);
    }
}
