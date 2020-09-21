package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.Mission;
import com.bluesky.framework.account.account.MissionList;

import java.util.List;

public interface MissionRepository {
    List<MissionList> findAllMission();
    void Updateconnection(Mission m);
    void addMission(Mission m);
}
