package com.bluesky.framework.api.controller.account;

import com.bluesky.common.vo.DataResponse;
import com.bluesky.framework.account.account.*;
import com.bluesky.framework.account.account.Connect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 项目基本信息管理
 */
@RestController
@RequestMapping("/program_information")
public class ProBaseInformationController {
    @Autowired
    private ProBaseInformationManager proBaseInformationManager;
    @Autowired
    private PhInfoManager phInfoManager;
    @Autowired
    private ConnectManager connectManager;

    /**
     * 项目基本信息查询所有 返回list
     */
    @GetMapping("/list")
    public DataResponse findAllProgram() {
        DataResponse result = new DataResponse();
        List<ProBaseInformation> a = proBaseInformationManager.findAllProgram();

        result.addData("list", a);
        return result;
    }
    /**
     * 项目基本信息查询所有名字和code 返回list
     */
    @GetMapping("/listpidname")
    public DataResponse findAllProgramidname() {
        DataResponse result = new DataResponse();
        List<ProBaseInformation> a = proBaseInformationManager.findAllProgramidname();

        result.addData("list", a);
        return result;
    }
    /**
     * 项目修改历史信息查询所有 返回list
     */
    @GetMapping("/listhInfo")
    public DataResponse findAllhInfo() {
        DataResponse result = new DataResponse();
        List<PhInfo> a = phInfoManager.findAllhInf();

        result.addData("list", a);
        return result;
    }
    /**
     * 查询所有项目名称 返回list
     */
    @GetMapping("/findchildren")
    public DataResponse findChildren(@RequestParam long parentId) {
        System.out.println(parentId);
        DataResponse result = new DataResponse();
        List<Connect> a = connectManager.findByParentId(parentId);

        result.addData("list", a);
        return result;
    }


//    /**
//     * 查询所有项目名称 返回list
//     */
//    @GetMapping("/getlevel")
//    public DataResponse findChildren(@RequestParam long parent_id) {
//        DataResponse result = new DataResponse();
//        List<Connection> a = connectManager.findByParentId(parent_id);
//
//        result.addData("list", a);
//        return result;
//    }
    /**
     * 项目历史信息查询所有 根据id返回list
     */
    @GetMapping("/listhInfobypid")
    public DataResponse findAllhInfobyid(Long pro_id) {
        System.out.println(pro_id);
        DataResponse result = new DataResponse();
        List<PhInfo> a = phInfoManager.findhInfbyid(pro_id);

        result.addData("list", a);
        return result;
    }

    @PostMapping("/addpro")
    public DataResponse addpro(@RequestParam long parentId, @RequestParam String name) {
        System.out.println("进入后台");
        System.out.println(name);
        System.out.println(parentId);
        DataResponse result=new DataResponse();

        Connect  a=new Connect();

        if (parentId != 0) {
            a = connectManager.findOne(parentId);
            a.setLevel(a.getLevel()+1);
        } else {
            a.setLevel(1);
        };
        a.setName(name);
        a.setParentId(parentId);

        connectManager.insert(a);
        return result;
    }

    /**
     * 修改名称或区域代码
     */
    @PutMapping("/{id}/update")
    public DataResponse updateName(HttpServletRequest request, @PathVariable long id, @RequestParam String name) {
        DataResponse result = new DataResponse();
        Connect a = connectManager.findOne(id);
        if (a == null) {
            return result.setFalseAndMessage("区域选择错误择错误");
        }
        Connect newa = a.builder().id(id).name(name).build();
        connectManager.update(newa);
        return result;
    }


    @PutMapping("/{id}/delete")
    public DataResponse delete(@PathVariable long id, HttpServletRequest request) {
        System.out.println(id);
        DataResponse result = new DataResponse();
        List<Connect> a = connectManager.findByParentId(id);
//        if (a == null) {
//            return result.setFalseAndMessage("区域选择错误");
//        }
        System.out.println(a);
        if (a.size() == 0) {
            connectManager.delete(id);
        }else{
            System.out.println("我不让你删除");
            return result.setFalseAndMessage("请从末级开始删除");
        }




        result.setMessage("删除成功");
        return result;
    }

    @RequestMapping("addProgram")
    public void addProgram(@RequestParam String projectname, @RequestParam String starttime, @RequestParam String endtime, @RequestParam String projectmanager, @RequestParam String member, @RequestParam String statement, HttpServletRequest request,
                           HttpServletResponse response) {

        System.out.println(projectname);
        System.out.println(starttime);
        ProBaseInformation pbi = new ProBaseInformation();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        pbi.setPro_code(df.format(new Date()) + new Random().nextInt(900) + 100);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        pbi.setPro_name(projectname);
        Date starttime1 = null;
        Date endtime1 = null;
        try {
            starttime1 = simpleDateFormat.parse(starttime);
            endtime1 = simpleDateFormat.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pbi.setPro_start_time(starttime1);
        pbi.setPro_end_time(endtime1);

        //member为json数组
        pbi.setPro_member(member);
        pbi.setPro_manager(projectmanager);
        pbi.setStatement(statement);
        proBaseInformationManager.addProgram(pbi);
        PrintWriter out = null;
        response.setCharacterEncoding("utf-8");
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write("success");
    }
    @RequestMapping("/deleteProgram")
    public void deleteProgram(String pro_code) {
        System.out.println(pro_code);
        proBaseInformationManager.deleteProgram(pro_code);
    }

    @RequestMapping("/editProgram")
    public DataResponse editProgram(@RequestParam String pro_code, @RequestParam String projectname, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date starttime, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endtime, @RequestParam String projectmanager, @RequestParam String member, @RequestParam String statement, HttpServletRequest request,
                            HttpServletResponse response) {

        DataResponse result=new DataResponse();
        ProBaseInformation p=new ProBaseInformation();
        p=proBaseInformationManager.findProgrambycode(pro_code);
        StringBuffer content_before = new StringBuffer();
        StringBuffer content_after = new StringBuffer();
//        if(p.getPro_start_time()==starttime1){
//
//        }else{
//            content_before.append("开始时间:"+p.getPro_start_time());
//            content_after.append("开始时间:"+starttime1);
//        }
//        if(p.getPro_end_time()==endtime1){
//
//        }else{
//            content_before.append("结束时间:"+p.getPro_end_time());
//            content_after.append("结束时间:"+endtime1);
//        }
        if(p.getPro_name().equals(projectname)){

        }else{
            content_before.append("项目名称:"+p.getPro_name());
            content_after.append("项目名称:"+projectname);
        }
        if(p.getPro_manager().equals(projectmanager)){

        }else{
            content_before.append("项目经理:"+p.getPro_manager());
            content_after.append("项目经理:"+projectmanager);
        }
        if(p.getPro_member().equals(member)){

        }else{
            content_before.append("项目成员:"+p.getPro_manager());
            content_after.append("项目成员:"+projectmanager);
        }

        //增加历史信息
        PhInfo h=new PhInfo();
        h.setContent_before(content_before.toString());
        h.setContent_after(content_after.toString());
        h.setUpdate_time(new Date(System.currentTimeMillis()));
        h.setPro_id(p.getPro_id());
        phInfoManager.addhInfo(h);


        // 修改数据库内信息
        ProBaseInformation pbi = new ProBaseInformation();
        pbi.setPro_code(pro_code);
        pbi.setPro_name(projectname);
        pbi.setPro_start_time(starttime);
        pbi.setPro_end_time(endtime);
        pbi.setPro_member(member);
        pbi.setStatement(statement);
        pbi.setPro_manager(projectmanager);
        proBaseInformationManager.editProgram(pbi);
        return  result;
    }
}
