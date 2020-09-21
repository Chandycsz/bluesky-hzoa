package com.bluesky.framework.account.account;

import java.util.ArrayList;
import java.util.List;

public interface ProMilestoneManager {
    ArrayList<ProMilestone> findAllMileStone();
    int updateStatusBef();
    int updateStatusNow();
    int updateStatusAft();
    int delectById(Long id);

    List<ProMilestone> findAllMileidname();
}
