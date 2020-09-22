package com.bluesky.framework.account.account;

import java.util.List;

public interface NoticeManager {
    void addNotice(Notice notice);



    void DeleteNoticeById(Long id);

    List<NoticeVo> ListNoticeByCId(Long cId, int item);
}
