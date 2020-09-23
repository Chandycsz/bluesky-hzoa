package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.OverTime;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.OverTimeMapper;
import com.bluesky.framework.domain.model.account.OverTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OverTimeRepositoryImpl implements OverTimeRepository {
    @Autowired
    private OverTimeMapper overTimeMapper;

    @Override
    public void addOverTime(OverTime overTime) {
        this.overTimeMapper.addOverTime(overTime);
    }
}
