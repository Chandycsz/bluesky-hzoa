package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.PhInfo;


import java.util.List;

public interface PhInfoRepository {
    List<PhInfo> findAllhInf();

    List<PhInfo> findhInfbyid(long pro_id);

    void addhInfo(PhInfo h);

}
