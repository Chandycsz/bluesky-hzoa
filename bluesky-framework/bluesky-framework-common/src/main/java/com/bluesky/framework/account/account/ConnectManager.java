package com.bluesky.framework.account.account;


import java.util.List;

public interface ConnectManager {
    /**
     * 根据父级id查询
     *
     * @param parentId 父级id
     * @return 所有子级集合
     */
    List<Connect> findByParentId(long parentId);

    Connect findOne(long parentId);

    void insert(Connect connect);

    void update(Connect newa);

    void delete(long id);
}
