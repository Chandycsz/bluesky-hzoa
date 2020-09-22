package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {

    @Insert("insert into notice set headline=#{notice.headline},content=#{notice.content},target=#{notice.target},failure_time=#{notice.failureTime},creator_id=#{notice.creatorId},create_time=#{notice.createTime},path=#{notice.path},status=1")
    void addNotice(@Param("notice") Notice notice);

    @Select("select a.id,a.headline,a.content,a.failure_time as failureTime,a.create_time as createTime,b.name as creatorName from notice a left join account b on a.creator_id=b.id where creator_id=#{cId} and a.status=#{item} order by a.id desc")
    List<NoticeVo> ListNoticeByCId(@Param("cId") Long cId,@Param("item") int item);

    @Delete("delete from notice where id=#{id}")
    void DeleteNoticeById(Long id);
}
