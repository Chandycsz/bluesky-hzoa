package com.bluesky.framework.account.account;

import java.util.List;

public interface NoticeManager {
    void addNotice(Notice notice);

    List<NoticeVo> ListNoticeByCId(Long cId);
}
