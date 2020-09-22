package com.bluesky.framework.domain.infrastructure.model.account.mapper;


import com.bluesky.framework.account.account.ProMilestone;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ProMilestoneMapper {
    @Select("select * from pro_milestone")
    ArrayList<ProMilestone> findAllMileStone();

    /**
     * 更新当前时间小于项目开始时间的状态为未开始
     */
    @Update("update pro_milestone set status = '未开始' where start_time > #{date}")
    int updateStatusBef(@Param("date") Date date);

    /**
     * 更新当前时间在开始时间和结束时间中间的状态为进行中
     */
    @Update("update pro_milestone set status = '进行中' where start_time < #{date} and end_time> #{date}")
    int updateStatusNow(@Param("date") Date date);

    /**
     * 更新当前时间在结束时间之后的状态为逾期
     */
    @Update("update pro_milestone set status = '逾期' where end_time < #{date}")
    int updateStatusAft(@Param("date") Date date);

    @Delete("delete from pro_milestone where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select id,name from pro_milestone")
    List<ProMilestone> findAllMileidname();
}
