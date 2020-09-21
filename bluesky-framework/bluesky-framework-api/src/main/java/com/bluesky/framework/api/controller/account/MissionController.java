package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.Mission;
import com.bluesky.framework.account.account.MissionList;
import com.bluesky.framework.account.account.MissionManager;
import com.bluesky.framework.account.account.ProBaseInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis")
public class MissionController {
    @Autowired
    private MissionManager missionManager;

    /**
     * 任务基本查询所有 返回list
     */
    @GetMapping("/getmissioninfo")
    public DataResponse findAllMission() {
        DataResponse result = new DataResponse();
        List<MissionList> a = missionManager.findAllMission();

        result.addData("list", a);


        return result;
    }

    @PostMapping("/Updateconnection")
    public DataResponse Updateconnection(@RequestParam long id,@RequestParam long  pro_id,@RequestParam long mile_id){
        DataResponse result = new DataResponse();
        Mission m=new Mission();
        m.setId(id);
        m.setPro_id(pro_id);
        m.setMile_id(mile_id);
        missionManager.Updateconnection(m);
        return result;
    }

    @RequestMapping("addMission")
    public DataResponse addMission(@RequestParam String headline,@RequestParam Integer type,@RequestParam Integer priority,@RequestParam String statement,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date deadline){
        DataResponse result = new DataResponse();
        Mission m=new Mission();
        m.setPro_id(null);
        m.setMile_id(null);
        m.setStatement(statement);
        m.setDeadline(deadline);
        m.setPriority(priority);
        m.setType(type);
        m.setStatus(1);//1表示已发送
        m.setHeadline(headline);
        missionManager.addMission(m);

    return  result;

    }
}
