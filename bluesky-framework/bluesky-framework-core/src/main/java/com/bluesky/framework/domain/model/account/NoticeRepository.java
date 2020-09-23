package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeRepository {
    long addNotice(Notice notice);

    List<NoticeVo> ListNoticeByCId(Long cId, int item);

    void DeleteNoticeById(Long id);

    void addRelation(@Param("id") long id, @Param("s") long s);

    List<NoticeVo> getNoticeByAId(Long aId);

    void visitNotice(Long id,Long account_id);

    List<NoticeVo> ListReviewedNotice();

    List<Long> findChildrenId(Long account_id);

    void change(Long nId, int choice);
}
