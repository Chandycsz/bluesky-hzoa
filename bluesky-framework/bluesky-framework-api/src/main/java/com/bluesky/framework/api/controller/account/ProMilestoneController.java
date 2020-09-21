package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.ProMilestone;
import com.bluesky.framework.account.account.ProMilestoneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目里程碑
 */
@RestController
@RequestMapping("/program_milestone")
public class ProMilestoneController {
    @Autowired
    private ProMilestoneManager proMilestoneManager;
    /**
     * 更新状态信息，查询里程碑所有信息 返回list
     */
    @GetMapping("/list")
    public DataResponse findAllProgram(){
        DataResponse result = new DataResponse();
        proMilestoneManager.updateStatusBef();
        proMilestoneManager.updateStatusNow();
        proMilestoneManager.updateStatusAft();
        List<ProMilestone> a=proMilestoneManager.findAllMileStone();
        result.addData("list",a);
        return result;
    }

    @GetMapping("/listmidname")
    public DataResponse findAllMileidname(){
        DataResponse result = new DataResponse();
//        proMilestoneManager.updateStatusBef();
//        proMilestoneManager.updateStatusNow();
//        proMilestoneManager.updateStatusAft();
        List<ProMilestone> a=proMilestoneManager.findAllMileidname();
        result.addData("list",a);
        return result;
    }
    /**
     *项目里程碑删除
     * @param id 里程碑id
     * @return
     */
    @PostMapping("/delete")
    public DataResponse deleteMilestone(@RequestParam Long id){
        proMilestoneManager.delectById(id);
        DataResponse dataResponse = new DataResponse();
        return dataResponse;
    }
}
