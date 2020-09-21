package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.ProMilestone;
import com.bluesky.framework.account.account.ProMilestoneManager;
import com.bluesky.framework.domain.model.account.ProMilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@DubboService
public class ProMilestoneManagerImpl implements ProMilestoneManager {
    @Autowired
    private ProMilestoneRepository promilestoneRepository;
    @Override
    public ArrayList<ProMilestone> findAllMileStone() {
     return promilestoneRepository.findAllMilestone();
    }

    @Override
    public int updateStatusBef() {
        return promilestoneRepository.updateStatusBef();
    }

    @Override
    public int updateStatusNow() {
        return promilestoneRepository.updateStatusNow();
    }

    @Override
    public int updateStatusAft() {
        return promilestoneRepository.updateStatusAft();
    }

    @Override
    public int delectById(Long id) {
        return promilestoneRepository.delectById(id);
    }

    @Override
    public List<ProMilestone> findAllMileidname() {
        return this.promilestoneRepository.findAllMileidname();
    }
}
