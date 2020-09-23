package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface NoticeMapper {

    @Insert("insert into notice set headline=#{notice.headline},content=#{notice.content},target=#{notice.target},failure_time=#{notice.failureTime},creator_id=#{notice.creatorId},create_time=#{notice.createTime},path=#{notice.path},status=1")
    @SelectKey(before = false, keyProperty = "notice.id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    void addNotice(@Param("notice") Notice notice);

    @Select("select a.id,a.headline,a.content,a.failure_time as failureTime,a.create_time as createTime,b.name as creatorName from notice a left join account b on a.creator_id=b.id where creator_id=#{cId} and a.status=#{item} order by a.id desc")
    List<NoticeVo> ListNoticeByCId(@Param("cId") Long cId, @Param("item") int item);

    @Delete("delete from notice where id=#{id}")
    void DeleteNoticeById(Long id);

    @Insert("insert into notice_relation set account_id=#{id2},status=0,notice_id=#{id}")
    void addRelation(@Param("id") long id, @Param("id2") long id2);

    @Select("select (case when c.status=0 then \"待查看\" when c.status=1  then \"已查看\" end )status,a.id,a.headline,a.content,a.failure_time as failureTime,a.create_time as createTime,b.name as creatorName from notice a left join account b on a.creator_id=b.id left join notice_relation c on c.notice_id=a.id where c.account_id=#{aId} and a.status=2 order by a.id desc")
    List<NoticeVo> getNoticeByAId(Long aId);

    @Update("update notice_relation set status=1,update_time=now() where notice_id=#{id} and account_id=#{account_id}")
    void visitNotice(@Param("id") Long id, @Param("account_id") Long account_id);

    @Select("select (case when a.target=1 then \"公开\" when a.target=2  then \"部分处室可见\"  when a.target=3  then \"部分人可见\" end )target,a.creator_id as creatorId,a.id,a.headline,a.content,a.failure_time as failureTime,a.create_time as createTime,b.name as creatorName from notice a left join account b on a.creator_id=b.id where  a.status=1 order by a.id desc")
    List<NoticeVo> ListReviewedNotice();

    @Select("select id from account where organization_id in (select organization_id from account where id=#{account_id}) and role_id=1041")
    List<Long> findChildrenId(Long account_id);

    @Update("update notice set status=#{choice} where id=#{nId}")
    void change(@Param("nId") Long nId, @Param("choice") int choice);
}
