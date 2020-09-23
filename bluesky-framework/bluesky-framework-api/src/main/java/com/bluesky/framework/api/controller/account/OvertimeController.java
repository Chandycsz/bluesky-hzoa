package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.NoticeManager;
import com.bluesky.framework.account.account.OverTime;
import com.bluesky.framework.account.account.OverTimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;

@RestController
@RequestMapping("/overtime")
public class OvertimeController {
    @Autowired
    private OverTimeManager overtimeManager;

    @RequestMapping("/AddOvertime")
    public DataResponse AddOvertime(@RequestParam String overtime,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd hh") Date startTime,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd hh") Date endTime,
                                    @RequestParam(required = false) String statement,
                                    @RequestParam Long creatorId){
        DataResponse result=new DataResponse();
        OverTime overTime=OverTime.builder()
                .overtime(overtime)
                .startTime(startTime)
                .endTime(endTime)
                .creatorId(creatorId)
                .statement(statement)
                .build();

        overtimeManager.addOverTime(overTime);
        return result;
    }
}
