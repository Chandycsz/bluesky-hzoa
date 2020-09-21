package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.Mission;
import com.bluesky.framework.account.account.MissionList;
import com.bluesky.framework.account.account.MissionManager;
import com.bluesky.framework.domain.model.account.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
public class MissionManagerImpl implements MissionManager {

    @Autowired
    private MissionRepository missionRepository;

    @Override
    public List<MissionList> findAllMission() {
        return missionRepository.findAllMission();
    }

    @Override
    public void Updateconnection(Mission m) {
        this.missionRepository.Updateconnection(m);
    }

    @Override
    public void addMission(Mission m) {
        this.missionRepository.addMission(m);
    }
}
