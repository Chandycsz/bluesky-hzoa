package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.ProBaseInformation;
import com.bluesky.framework.account.account.ProBaseInformationManager;
import com.bluesky.framework.domain.model.account.ProBaseInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@DubboService
public class ProBaseInformationManagerImpl implements ProBaseInformationManager {
    @Autowired
    private ProBaseInformationRepository proBaseInformationRepository;

    @Override
    public ArrayList<ProBaseInformation> findAllProgram() {
        return proBaseInformationRepository.findAllProgram();
    }

    @Override
    public ProBaseInformation findProgrambycode(String pro_code) {
        return proBaseInformationRepository.findProgrambycode(pro_code);
    }

    @Override
    public void addProgram(ProBaseInformation pbi) {
        this.proBaseInformationRepository.addProgram(pbi);
    }

    @Override
    public void deleteProgram(String programid) {
        this.proBaseInformationRepository.deleteProgram(programid);
    }

    @Override
    public void editProgram(ProBaseInformation pbi) {
        this.proBaseInformationRepository.editProgram(pbi);
    }

    @Override
    public List<ProBaseInformation> findAllProgramidname() {
        return proBaseInformationRepository.findAllProgramidname();
    }

}

