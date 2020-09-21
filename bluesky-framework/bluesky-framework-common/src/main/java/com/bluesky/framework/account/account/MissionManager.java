package com.bluesky.framework.account.account;

import java.util.List;

public interface MissionManager {
    List<MissionList> findAllMission();

    void Updateconnection(Mission m);

    void addMission(Mission m);
}