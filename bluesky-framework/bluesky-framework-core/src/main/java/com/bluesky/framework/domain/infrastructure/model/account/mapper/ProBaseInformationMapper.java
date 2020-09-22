package com.bluesky.framework.domain.infrastructure.model.account.mapper;


import com.bluesky.framework.account.account.ProBaseInformation;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ProBaseInformationMapper {
    @Select("select * from pro_base_information")
    ArrayList<ProBaseInformation> findAllProgram();

    @Insert("insert into pro_base_information " +
            "(pro_code, pro_manager,  pro_member, pro_start_time, pro_end_time, pro_name,statement) " +
            "values " +
            "(#{pbi.pro_code},#{pbi.pro_manager},#{pbi.pro_member},#{pbi.pro_start_time},#{pbi.pro_end_time},#{pbi.pro_name},#{pbi.statement})")
    void addProgram(@Param("pbi") ProBaseInformation pbi);


    @Delete("delete from pro_base_information where pro_code=#{programid}")
    void deleteProgram(@Param("programid") String programid);

    @Update("update pro_base_information set pro_name = #{pbi.pro_name},pro_start_time = #{pbi.pro_start_time},pro_end_time = #{pbi.pro_end_time},pro_manager = #{pbi.pro_manager},pro_member = #{pbi.pro_member},statement = #{pbi.statement} where pro_code = #{pbi.pro_code}")
    void editProgram(@Param("pbi") ProBaseInformation pbi);

    @Select("select * from pro_base_information where pro_code=#{pro_code}")
    ProBaseInformation findProgrambycode(String pro_code);

    @Select("select pro_id,pro_name from pro_base_information")
    List<ProBaseInformation> findAllProgramidname();
}
