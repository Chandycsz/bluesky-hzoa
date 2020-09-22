package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.ProMilestone;

import java.util.ArrayList;
import java.util.List;

public interface ProMilestoneRepository {
    ArrayList<ProMilestone> findAllMilestone();
    int updateStatusBef();
    int updateStatusNow();
    int updateStatusAft();
    int delectById(Long id);
    List<ProMilestone> findAllMileidname();
}
