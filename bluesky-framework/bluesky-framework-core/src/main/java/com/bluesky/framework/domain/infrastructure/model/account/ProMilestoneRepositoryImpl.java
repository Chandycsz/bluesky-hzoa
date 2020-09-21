package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.ProMilestone;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.ProMilestoneMapper;
import com.bluesky.framework.domain.model.account.ProMilestoneRepository;
import javafx.beans.binding.LongExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProMilestoneRepositoryImpl implements ProMilestoneRepository {
    @Autowired
    private ProMilestoneMapper proMilestoneMapper;

    @Override
    public ArrayList<ProMilestone> findAllMilestone() {
        return proMilestoneMapper.findAllMileStone();
    }

    @Override
    public int updateStatusBef() {
        Date date = date();
        return proMilestoneMapper.updateStatusBef(date);
    }

    @Override
    public int updateStatusNow() {
        Date date = date();
        return proMilestoneMapper.updateStatusNow(date);
    }

    @Override
    public int updateStatusAft() {
        Date date = date();
        return proMilestoneMapper.updateStatusAft(date);
    }

    @Override
    public int delectById(Long id) {
        return proMilestoneMapper.deleteById(id);
    }

    @Override
    public List<ProMilestone> findAllMileidname() {
        return this.proMilestoneMapper.findAllMileidname();
    }

    public static Date date() {
        Date dt = new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置显示格式
        String format = df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
        try {
            dt = df.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
}
