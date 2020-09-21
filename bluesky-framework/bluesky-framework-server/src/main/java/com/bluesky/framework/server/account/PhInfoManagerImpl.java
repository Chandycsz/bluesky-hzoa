package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.PhInfo;
import com.bluesky.framework.account.account.PhInfoManager;
import com.bluesky.framework.domain.model.account.PhInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@DubboService
public class PhInfoManagerImpl implements PhInfoManager {
    @Autowired
    private PhInfoRepository PhInfoRepository;


    @Override
    public List<PhInfo> findAllhInf() {
        return PhInfoRepository.findAllhInf();
    }

    @Override
    public List<PhInfo> findhInfbyid(long pro_id) {
        return PhInfoRepository.findhInfbyid(pro_id);
    }

    @Override
    public void addhInfo(PhInfo h) {
        this.PhInfoRepository.addhInfo(h);
    }
}
