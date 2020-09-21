package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.Mission;
import com.bluesky.framework.account.account.MissionList;
import com.bluesky.framework.account.account.MissionManager;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.MissionMapper;
import com.bluesky.framework.domain.model.account.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MissionRepositoryImpl implements MissionRepository {

    @Autowired
    private MissionMapper missionMapper;

    @Override
    public List<MissionList> findAllMission() {
        return missionMapper.findAllMission();
    }

    @Override
    public void Updateconnection(Mission m) {
        this.missionMapper.Updateconnection(m);
    }

    @Override
    public void addMission(Mission m) {
        this.missionMapper.addMission(m);
    }


}
