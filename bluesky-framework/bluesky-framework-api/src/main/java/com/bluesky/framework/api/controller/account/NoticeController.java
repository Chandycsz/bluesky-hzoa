package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeManager;
import com.bluesky.framework.account.account.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeManager noticeManager;

    @RequestMapping("/AddNotice")
    public DataResponse AddNotice(@RequestParam String headline, @RequestParam String content, @RequestParam int target, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date failureTime,@RequestParam Long creatorId){
        DataResponse result=new DataResponse();
        Notice notice =Notice.builder()
                .content(content)
                .headline(headline)
                .target(target)
                .failureTime(failureTime)
                .creatorId(creatorId)
                .createTime(new Date())
                .build();
        noticeManager.addNotice(notice);
        return result;
    }

    @RequestMapping("/ListNoticeByCId")
    public DataResponse ListNoticeByCId(@RequestParam Long CId){
        DataResponse result=new DataResponse();
        List<NoticeVo> list=noticeManager.ListNoticeByCId(CId);
        result.addData("list",result);
        return result;
    }
}
