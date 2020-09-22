package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.ProMilestoneHistory;

import java.util.ArrayList;

public interface ProMilestoneHistoryRepository {
    ArrayList<ProMilestoneHistory> findAll();
}
