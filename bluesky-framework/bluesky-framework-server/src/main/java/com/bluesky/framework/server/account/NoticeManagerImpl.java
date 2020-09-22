package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.domain.model.account.NoticeRepository;
import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeManager;
import com.bluesky.framework.account.account.NoticeVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
public class NoticeManagerImpl implements NoticeManager {
    private NoticeRepository noticeRepository;

    @Override
    public void addNotice(Notice notice) {
        this.noticeRepository.addNotice(notice);
    }

    @Override
    public List<NoticeVo> ListNoticeByCId(Long cId) {
        return noticeRepository.ListNoticeByCId(cId);
    }
}
