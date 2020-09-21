package com.bluesky.framework.domain.model.account;


import com.bluesky.framework.account.account.ProBaseInformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ProBaseInformationRepository {
    ArrayList<ProBaseInformation> findAllProgram();
    void addProgram(ProBaseInformation pbi);
    void deleteProgram(String programid);
    void editProgram(ProBaseInformation pbi);
    ProBaseInformation findProgrambycode(String pro_code);
    List<ProBaseInformation> findAllProgramidname();

}
