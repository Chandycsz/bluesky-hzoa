package com.bluesky.framework.domain.service.account;

import com.bluesky.framework.account.account.ProBaseInformation;

import java.util.Date;
import java.util.List;

public interface ProBaseInformationService {
    List<ProBaseInformation> findAllProgram();
    void addProgram(ProBaseInformation pbi);
    void deleteProgram(String programid);
    void editProgram(ProBaseInformation pbi);

}
