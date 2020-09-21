package com.bluesky.framework.account.account;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ProBaseInformationManager {

    ArrayList<ProBaseInformation> findAllProgram();

    ProBaseInformation findProgrambycode(String pro_code);

    void addProgram(ProBaseInformation pbi);

    void deleteProgram(String programid);

    void editProgram(ProBaseInformation pbi);

    List<ProBaseInformation> findAllProgramidname();
}
