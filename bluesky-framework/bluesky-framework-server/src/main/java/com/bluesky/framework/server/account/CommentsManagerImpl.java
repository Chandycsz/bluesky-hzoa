package com.bluesky.framework.server.account;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.bluesky.framework.account.account.Comments;
import com.bluesky.framework.account.account.CommentsManager;
import com.bluesky.framework.account.account.CommentsVo;
import com.bluesky.framework.domain.model.account.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
public class CommentsManagerImpl implements CommentsManager {
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public void addComments(Comments comments) {
        this.commentsRepository.addComments(comments);
    }

    @Override
    public List<CommentsVo> listCommentsByDId(Long id) {
        return commentsRepository.listCommentsByDId(id);
    }
}
