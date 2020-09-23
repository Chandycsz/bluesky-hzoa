package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.domain.infrastructure.model.account.mapper.NoticeMapper;
import com.bluesky.framework.domain.model.account.NoticeRepository;
import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRepositoryImpl implements NoticeRepository {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public long addNotice(Notice notice) {
        this.noticeMapper.addNotice(notice);
        return notice.getId();
    }

    @Override
    public List<NoticeVo> ListNoticeByCId(Long cId, int item) {
        return noticeMapper.ListNoticeByCId(cId, item);
    }


    @Override
    public void DeleteNoticeById(Long id) {
        this.noticeMapper.DeleteNoticeById(id);
    }

    @Override
    public void addRelation(long id, long id2) {
        this.noticeMapper.addRelation(id,id2);
    }

    @Override
    public List<NoticeVo> getNoticeByAId(Long aId) {
        return noticeMapper.getNoticeByAId(aId);
    }

    @Override
    public void visitNotice(Long id,Long account_id) {
        this.noticeMapper.visitNotice(id,account_id);
    }

    @Override
    public List<NoticeVo> ListReviewedNotice() {
        return noticeMapper.ListReviewedNotice();
    }

    @Override
    public List<Long> findChildrenId(Long account_id) {
        return noticeMapper.findChildrenId(account_id);
    }

    @Override
    public void change(Long nId, int choice) {
        this.noticeMapper.change(nId, choice);
    }
}
