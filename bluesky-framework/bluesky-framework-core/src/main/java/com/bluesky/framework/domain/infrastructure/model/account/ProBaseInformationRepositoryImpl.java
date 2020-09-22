package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.ProBaseInformation;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.ProBaseInformationMapper;
import com.bluesky.framework.domain.model.account.ProBaseInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProBaseInformationRepositoryImpl implements ProBaseInformationRepository {
    @Autowired
    private ProBaseInformationMapper proBaseInformationMapper;
    @Override
    public ArrayList<ProBaseInformation> findAllProgram() {
        return proBaseInformationMapper.findAllProgram();
    }

    @Override
    public void addProgram(ProBaseInformation pbi) {
        this.proBaseInformationMapper.addProgram(pbi);
    }

    @Override
    public void deleteProgram(String programid) {
        this.proBaseInformationMapper.deleteProgram(programid);
    }

    @Override
    public void editProgram(ProBaseInformation pbi) {
        this.proBaseInformationMapper.editProgram(pbi);
    }

    @Override
    public ProBaseInformation findProgrambycode(String pro_code) {
        return proBaseInformationMapper.findProgrambycode(pro_code);
    }

    @Override
    public List<ProBaseInformation> findAllProgramidname() {
        return proBaseInformationMapper.findAllProgramidname();
    }
}
