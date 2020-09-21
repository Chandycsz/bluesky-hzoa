package com.bluesky.framework.account.account;

import java.util.List;

public interface PhInfoManager {
    List<PhInfo> findAllhInf();

    List<PhInfo> findhInfbyid(long pro_id);

    void addhInfo(PhInfo h);
}
