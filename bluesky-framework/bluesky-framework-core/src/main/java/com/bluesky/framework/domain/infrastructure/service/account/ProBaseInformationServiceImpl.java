package com.bluesky.framework.domain.infrastructure.service.account;

import com.bluesky.framework.account.account.ProBaseInformation;
import com.bluesky.framework.domain.model.account.ProBaseInformationRepository;
import com.bluesky.framework.domain.service.account.ProBaseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProBaseInformationServiceImpl implements ProBaseInformationService {
    @Autowired
    private ProBaseInformationRepository proBaseInformationRepository;
    @Override
    public ArrayList<ProBaseInformation> findAllProgram() {
        return proBaseInformationRepository.findAllProgram();
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
}
