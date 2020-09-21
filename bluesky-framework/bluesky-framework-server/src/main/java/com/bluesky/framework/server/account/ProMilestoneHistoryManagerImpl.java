package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.ProMilestoneHistory;
import com.bluesky.framework.account.account.ProMilestoneHistoryManager;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.ProMilestoneMapper;
import com.bluesky.framework.domain.model.account.ProMilestoneHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@DubboService
public class ProMilestoneHistoryManagerImpl implements ProMilestoneHistoryManager {
    @Autowired
    private ProMilestoneHistoryRepository proMilestoneHistoryRepository;
    @Override
    public ArrayList<ProMilestoneHistory> findAll() {
        return proMilestoneHistoryRepository.findAll();
    }
}
