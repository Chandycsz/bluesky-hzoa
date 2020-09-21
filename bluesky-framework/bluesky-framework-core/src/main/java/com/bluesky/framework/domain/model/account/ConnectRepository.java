package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.Connect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConnectRepository {
    List<Connect> findByParentId(long parentId);

    Connect findOne(long parentId);

    void insert(Connect connect);

    void update(Connect newa);

    void delete(long id);
}
