package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.ProMilestoneHistory;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.ProMilestoneHistoryMapper;
import com.bluesky.framework.domain.model.account.ProMilestoneHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ProMilestoneHistoryRepositoryImpl implements ProMilestoneHistoryRepository{
    @Autowired
    private ProMilestoneHistoryMapper proMilestoneHistoryMapper;
    @Override
    public ArrayList<ProMilestoneHistory> findAll() {
        return proMilestoneHistoryMapper.findAll();
    }
}
