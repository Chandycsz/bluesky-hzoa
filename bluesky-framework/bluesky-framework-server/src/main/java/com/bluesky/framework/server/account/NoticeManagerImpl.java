package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.domain.model.account.NoticeRepository;
import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeManager;
import com.bluesky.framework.account.account.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
public class NoticeManagerImpl implements NoticeManager {
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public long addNotice(Notice notice) {
        return noticeRepository.addNotice(notice);
    }

    @Override
    public void DeleteNoticeById(Long id) {
        this.noticeRepository.DeleteNoticeById(id);
    }

    @Override
    public List<NoticeVo> ListNoticeByCId(Long cId, int item) {
        return noticeRepository.ListNoticeByCId(cId, item);
    }

    @Override
    public void addRelation(long id, long id2) {
        this.noticeRepository.addRelation(id, id2);
    }

    @Override
    public List<NoticeVo> getNoticeByAId(Long aId) {
        return noticeRepository.getNoticeByAId(aId);
    }

    @Override
    public void visitNotice(Long id,Long account_id) {
        this.noticeRepository.visitNotice(id, account_id);
    }

    @Override
    public List<NoticeVo> ListReviewedNotice() {
        return noticeRepository.ListReviewedNotice();
    }

    @Override
    public List<Long> findChildrenId(Long account_id) {
        return noticeRepository.findChildrenId(account_id);
    }

    @Override
    public void change(Long nId, int choice) {
        noticeRepository.change(nId, choice);
    }
}
