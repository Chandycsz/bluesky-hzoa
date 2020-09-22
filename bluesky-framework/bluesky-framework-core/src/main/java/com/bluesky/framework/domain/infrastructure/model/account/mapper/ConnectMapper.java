package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.Connect;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ConnectMapper {

    @Select("select * from connection where parent_id = #{parentId}")
    List<Connect> findByParentId(Long parentId);

    @Select("select * from connection where id = #{parentId}")
    Connect findOne(long parentId);

    @Insert("insert into connection " +
            "(name, level, parent_id,top,leaf) " +
            "values " +
            "(#{connect.name},#{connect.level},#{connect.parentId},#{connect.top},#{connect.leaf})")
    void insert(@Param("connect") Connect connect);

    @Update("update connection set name = #{newa.name} where id = #{newa.id}")
    void update(@Param("newa") Connect newa);

    @Delete("delete from connection where id=#{id}")
    void delete(long id);
}
