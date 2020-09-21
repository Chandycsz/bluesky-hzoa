package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.Mission;
import com.bluesky.framework.account.account.MissionList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MissionMapper {
    @Select("select id,pro_id,mile_id,headline,statement,deadline, (\n" +
            "case\n" +
            "when a.status=1 then '已发送' \n" +
            "when a.status=3 then '已接收' \n" +
            "end \n" +
            ")status,\n" +
            "(\n" +
            "case\n" +
            "when a.type=1 then '前端'\n" +
            "when a.type=2 then '后端'\n" +
            "when a.type=3 then '美工'\n" +
            "end \n" +
            ")type,\n" +
            "(case\n" +
            "when a.priority=1 then '第一优先级'\n" +
            "when a.priority=2 then '第二优先级'\n" +
            "when a.priority=3 then '第三优先级'\n" +
            "end)priority\n" +
            "from pro_mission a")
    List<MissionList> findAllMission();

    @Update("update pro_mission set pro_id = #{m.pro_id},mile_id = #{m.mile_id} where id=#{m.id}")
    void Updateconnection(@Param("m") Mission m);

    @Insert("insert into pro_mission(pro_id,mile_id,headline,priority,type,statement,status,deadline) values(#{m.pro_id},#{m.mile_id},#{m.headline},#{m.priority},#{m.type},#{m.statement},#{m.status},#{m.deadline})")
    void addMission(@Param("m") Mission m);
}
