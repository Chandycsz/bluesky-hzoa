package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.PhInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PhInfoMapper {
    @Select("select * from pro_history_information")
    List<PhInfo> findAllhInf();

    @Select("select * from pro_history_information where pro_id=#{pro_id}")
    List<PhInfo> findhInfbyid(long pro_id);

    @Insert("insert into pro_history_information(pro_id,content_before,content_after,update_time) values(#{h.pro_id},#{h.content_before},#{h.content_after},#{h.update_time})")
    void addhInfo(@Param("h")PhInfo h);
}
