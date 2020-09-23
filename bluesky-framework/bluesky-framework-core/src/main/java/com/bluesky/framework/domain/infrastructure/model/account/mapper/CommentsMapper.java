package com.bluesky.framework.domain.infrastructure.model.account.mapper;

import com.bluesky.framework.account.account.Comments;
import com.bluesky.framework.account.account.CommentsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentsMapper {

    @Insert("insert into comments set a_id=#{comments.managerId},review=#{comments.review},create_time=#{comments.createTime},n_id=#{comments.NId},choice=#{comments.choice}")
    void addComments(@Param("comments") Comments comments);

    @Select("select a.id,review,create_time as createTime,b.name as contractName,c.name as managerName,\n" +
            "(case\n" +
            "when a.choice=0 then '退回'\n" +
            "when a.choice=1 then '通过'\n" +
            "end)choice\n" +
            "from comments a left join contract_draft b on a.d_id=b.id left join account c on a.a_id=c.id where b.id=#{id}")
    List<CommentsVo> listCommentsByDId(Long id);
}
