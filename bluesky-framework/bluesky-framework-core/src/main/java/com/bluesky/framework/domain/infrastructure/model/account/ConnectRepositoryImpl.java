package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.Connect;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.ConnectMapper;
import com.bluesky.framework.domain.model.account.ConnectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectRepositoryImpl implements ConnectRepository {
    @Autowired
    private ConnectMapper connectMapper;

    @Override
    public List<Connect> findByParentId(long parentId) {
        return connectMapper.findByParentId(parentId);
    }

    @Override
    public Connect findOne(long parentId) {
        return connectMapper.findOne(parentId);
    }

    @Override
    public void insert(Connect connect) {
        this.connectMapper.insert(connect);
    }

    @Override
    public void update(Connect newa) {
        this.connectMapper.update(newa);
    }

    @Override
    public void delete(long id) {
        this.connectMapper.delete(id);
    }


}
