package com.bluesky.oa_hangzhou.model.notice.Mapper;

import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {

    @Insert("insert into notice set headline=#{notice.headline},content=#{notice.content},target=#{notice.target},failure_time=#{notice.failureTime},creator_id=#{notice.creatorId},create_time=#{notice.createTime}")
    void addNotice(@Param("notice") Notice notice);

    @Select("select a.id,a.headline,a.content,a.failure_time,a.create_time,b.name from notice a left join account b on a.creator_id=b.id where creator_id=#{cId}")
    List<NoticeVo> ListNoticeByCId(Long cId);
}
