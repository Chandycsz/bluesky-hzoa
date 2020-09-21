package com.bluesky.framework.server.account;


import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.ConnectManager;
import com.bluesky.framework.account.account.Connect;
import com.bluesky.framework.domain.model.account.ConnectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
public class ConnectManagerImpl implements ConnectManager {
    @Autowired
    private ConnectRepository connectRepository;

    @Override
    public List<Connect> findByParentId(long parentId) {
        return connectRepository.findByParentId(parentId);
    }

    @Override
    public Connect findOne(long parentId) {
        return connectRepository.findOne(parentId);
    }

    @Override
    public void insert(Connect connect) {
        this.connectRepository.insert(connect);
    }

    @Override
    public void update(Connect newa) {
        this.connectRepository.update(newa);
    }

    @Override
    public void delete(long id) {
        this.connectRepository.delete(id);
    }
}
