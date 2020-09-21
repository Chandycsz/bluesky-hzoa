package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.ProMilestoneHistory;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ProMilestoneHistoryMapper {
    @Select("select * from pro_history_milestone")
    ArrayList<ProMilestoneHistory> findAll();
}
