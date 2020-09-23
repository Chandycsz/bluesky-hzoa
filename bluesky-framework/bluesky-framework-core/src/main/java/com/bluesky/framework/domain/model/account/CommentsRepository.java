package com.bluesky.framework.domain.model.account;

import com.bluesky.framework.account.account.Comments;
import com.bluesky.framework.account.account.CommentsVo;

import java.util.List;

public interface CommentsRepository {
    void addComments(Comments comments);

    List<CommentsVo> listCommentsByDId(Long id);
}
