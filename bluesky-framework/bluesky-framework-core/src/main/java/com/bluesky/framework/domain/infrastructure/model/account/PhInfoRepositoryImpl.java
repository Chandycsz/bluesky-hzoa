package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.PhInfo;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.PhInfoMapper;
import com.bluesky.framework.domain.model.account.PhInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PhInfoRepositoryImpl implements PhInfoRepository {
    @Autowired
    private com.bluesky.framework.domain.infrastructure.model.account.mapper.PhInfoMapper PhInfoMapper;

    @Override
    public List<PhInfo> findAllhInf() {
        return PhInfoMapper.findAllhInf();
    }

    @Override
    public List<PhInfo> findhInfbyid(long pro_id) {
        return PhInfoMapper.findhInfbyid(pro_id);
    }

    @Override
    public void addhInfo(PhInfo h) {
        this.PhInfoMapper.addhInfo(h);
    }
}
