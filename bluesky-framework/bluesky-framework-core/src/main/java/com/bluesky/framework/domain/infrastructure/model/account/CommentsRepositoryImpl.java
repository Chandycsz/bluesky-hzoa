package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.Comments;
import com.bluesky.framework.account.account.CommentsVo;
import com.bluesky.framework.domain.infrastructure.model.account.mapper.CommentsMapper;
import com.bluesky.framework.domain.model.account.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentsRepositoryImpl implements CommentsRepository {
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public void addComments(Comments comments) {
        this.commentsMapper.addComments(comments);
    }

    @Override
    public List<CommentsVo> listCommentsByDId(Long id) {
        return commentsMapper.listCommentsByDId(id);
    }
}
