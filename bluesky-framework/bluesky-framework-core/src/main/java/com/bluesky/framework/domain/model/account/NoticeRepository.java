package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeVo;

import java.util.List;

public interface NoticeRepository {
    void addNotice(Notice notice);

    List<NoticeVo> ListNoticeByCId(Long cId, int item);

    void DeleteNoticeById(Long id);
}
