package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.ProMilestoneHistory;
import com.bluesky.framework.account.account.ProMilestoneHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/program_milestone_history")
public class ProMilestoneHistoryController {
    @Autowired
    private ProMilestoneHistoryManager proMilestoneHistoryManager;

    /**
     * 更新状态信息，查询里程碑所有信息 返回list
     */
    @GetMapping("/list")
    public DataResponse findAllProgram() {
        DataResponse result = new DataResponse();
        ArrayList<ProMilestoneHistory> a = proMilestoneHistoryManager.findAll();
        result.addData("list", a);
        System.out.println(a);
        return result;
    }
}
