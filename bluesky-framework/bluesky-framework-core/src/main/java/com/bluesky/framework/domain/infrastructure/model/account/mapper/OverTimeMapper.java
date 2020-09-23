package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.OverTime;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OverTimeMapper {

    @Insert("insert into overtime set overtime=#{overTime.overtime},start_time=#{overTime.startTime},end_time=#{overTime.endTime},statement=#{overTime.statement},creator_id=#{overTime.creatorId}")
    void addOverTime(@Param("overTime") OverTime overTime);
}
