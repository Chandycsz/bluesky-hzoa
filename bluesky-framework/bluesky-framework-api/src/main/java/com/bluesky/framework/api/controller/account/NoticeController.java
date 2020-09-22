package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.Notice;
import com.bluesky.framework.account.account.NoticeManager;
import com.bluesky.framework.account.account.NoticeVo;
import com.bluesky.framework.setting.PageSettingManager;
import com.bluesky.framework.setting.page.PageSetting;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeManager noticeManager;
    @Autowired
    private PageSettingManager pageSettingManager;

    @RequestMapping("/AddNotice")
    public DataResponse AddNotice(@RequestParam(required = false) MultipartFile file,
                                  @RequestParam String headline, @RequestParam String content, @RequestParam int target, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date failureTime, @RequestParam Long creatorId) {
        DataResponse result = new DataResponse();

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

        noticeManager.addNotice(notice);
        return result;
    }

    @RequestMapping("/ListNoticeByCId")
    public DataResponse ListNoticeByCId(@RequestParam Long CId, @RequestParam String status) {

        int item=0;
        if("Reviewing".equals(status))
            item=1;
        else if("Passed".equals(status))
            item=2;
        else if("Returned".equals(status))
            item=2;
        else if("Invalid".equals(status))
            item=2;



        DataResponse result = new DataResponse();
        List<NoticeVo> list = noticeManager.ListNoticeByCId(CId,item);
        result.addData("list", list);
        return result;
    }

    @RequestMapping("/DeleteNoticeById")
    public DataResponse DeleteNoticeById(@RequestParam Long id) {
        DataResponse result = new DataResponse();
        noticeManager.DeleteNoticeById(id);

        return result;
    }
}
