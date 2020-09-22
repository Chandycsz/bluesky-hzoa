package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.oa_hangzhou.model.notice.Mapper.NoticeMapper;
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
    public void addNotice(Notice notice) {
        this.noticeMapper.addNotice(notice);
    }

    @Override
    public List<NoticeVo> ListNoticeByCId(Long cId) {
        return noticeMapper.ListNoticeByCId(cId);
    }
}
