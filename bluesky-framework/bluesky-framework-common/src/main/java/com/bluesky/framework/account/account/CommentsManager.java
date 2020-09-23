package com.bluesky.framework.account.account;

import java.util.List;

public interface CommentsManager {

    void addComments(Comments comments);

    List<CommentsVo> listCommentsByDId(Long id);
}
