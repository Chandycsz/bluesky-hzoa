package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.*;
import com.bluesky.framework.account.organization.OrganizationManager;
import com.bluesky.framework.setting.PageSettingManager;
import com.bluesky.framework.setting.page.PageSetting;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeManager noticeManager;
    @Autowired
    private PageSettingManager pageSettingManager;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private CommentsManager commentsManager;

    @RequestMapping("/AddNotice")
    public DataResponse AddNotice(@RequestParam(required = false) MultipartFile file,
                                  @RequestParam String headline, @RequestParam String content, @RequestParam int target, @RequestParam(required = false) String members, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date failureTime, @RequestParam Long creatorId) {
        DataResponse result = new DataResponse();
        System.out.println(members);
        //系统默认路径
        String defaultDir;
        List<PageSetting> pageSettingList = pageSettingManager.findByPage(PageSetting.Page.DEFAULT.getCode());
        if (!pageSettingList.isEmpty()) {
            defaultDir = pageSettingList.get(0).getValue();
        } else {
            result.setFalseAndMessage("请先设置上传文件夹路径");
            return result;
        }

        Notice notice = Notice.builder()
                .content(content)
                .headline(headline)
                .target(target)
                .failureTime(failureTime)
                .creatorId(creatorId)
                .createTime(new Date())
                .build();

        if (file != null) {
            //拼接路径
            String code = RandomStringUtils.randomNumeric(6);
            String filePath = defaultDir + "/" + code + file.getOriginalFilename();
            notice.setPath(filePath);
            //创建文件
            File dest = new File(filePath);//用于创建文件的路径，file类型
            try {
                file.transferTo(dest); // 保存文件

            } catch (Exception e) {
                e.printStackTrace();
                return result.setFalseAndMessage("上传失败");
            }

        }
        long id = noticeManager.addNotice(notice);

        if (target == 2) {
            String[] item = members.split(",");
            for (int i = 0; i < item.length; i++) {
                noticeManager.addRelation(id, Long.parseLong(item[i]));
            }
        } else if (target == 3) {
            String[] item = members.split(",");
            for (int j = 0; j < item.length; j++) {
                List<Long> list = accountManager.findByOrgId(Long.parseLong(item[j]));
                for (int k = 0; k < list.size(); k++) {
                    noticeManager.addRelation(id, list.get(k));
                }
            }
        }
        return result;
    }

    @RequestMapping("/ListNoticeByCId")
    public DataResponse ListNoticeByCId(@RequestParam Long CId, @RequestParam String status) {

        int item = 0;
        if ("Reviewing".equals(status))
            item = 1;
        else if ("Passed".equals(status))
            item = 2;
        else if ("Returned".equals(status))
            item = 2;
        else if ("Invalid".equals(status))
            item = 2;


        DataResponse result = new DataResponse();
        List<NoticeVo> list = noticeManager.ListNoticeByCId(CId, item);
        result.addData("list", list);
        return result;
    }

    @RequestMapping("/DeleteNoticeById")
    public DataResponse DeleteNoticeById(@RequestParam Long id) {
        DataResponse result = new DataResponse();
        noticeManager.DeleteNoticeById(id);

        return result;
    }


    @RequestMapping("/getNoticeByAId")
    public DataResponse getNoticeByAId(@RequestParam Long AId) {
        DataResponse result = new DataResponse();
        List<NoticeVo> list = noticeManager.getNoticeByAId(AId);
        result.addData("list", list);
        return result;
    }

    @RequestMapping("/visitNotice")
    public DataResponse visitNotice(@RequestParam Long id, @RequestParam Long account_id) {
        DataResponse result = new DataResponse();
        noticeManager.visitNotice(id, account_id);
        return result;
    }


    @RequestMapping("/ListReviewedNotice")
    public DataResponse ListReviewedNotice(@RequestParam Long account_id) {
        DataResponse result = new DataResponse();
        //先查找他的下属id
        List<Long> children = noticeManager.findChildrenId(account_id);
        List<NoticeVo> list = noticeManager.ListReviewedNotice();
        List<NoticeVo> list1 = new ArrayList<NoticeVo>();
        //将使他下属的公告返回前端
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < children.size(); j++) {
                if (list.get(i).getCreatorId() == children.get(i)) {

                    list1.add(list.get(i));
                }
            }
        }
        result.addData("list", list1);
        return result;
    }


    @RequestMapping("/addComments")
    public DataResponse addComments(@RequestParam Long NId, @RequestParam int choice, @RequestParam String review, @RequestParam Long AId) {
        DataResponse result = new DataResponse();
        Comments comments = Comments.builder()
                .NId(NId)
                .choice(choice)
                .createTime(new Date())
                .managerId(AId)
                .review(review)
                .build();
        commentsManager.addComments(comments);
        if (choice == 2) {
            choice = 3;
        } else if (choice == 1) {
            choice = 2;
        }
        noticeManager.change(NId,choice);
        return result;
    }
}
