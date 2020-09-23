package com.bluesky.framework.account.account;

import java.util.List;

public interface NoticeManager {
    long addNotice(Notice notice);



    void DeleteNoticeById(Long id);

    List<NoticeVo> ListNoticeByCId(Long cId, int item);

    void addRelation(long id, long id2);

    List<NoticeVo> getNoticeByAId(Long aId);

    void visitNotice(Long id,Long account_id);

    List<NoticeVo> ListReviewedNotice();

    List<Long> findChildrenId(Long account_id);

    void change(Long nId, int choice);
}
