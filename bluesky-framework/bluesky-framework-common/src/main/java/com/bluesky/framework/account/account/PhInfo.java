package com.bluesky.framework.account.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhInfo implements Serializable {
    /**
     *id
     */
    private Long id;
    /**
     * 项目id
     */
    private Long pro_id;
    /**
     * 修改内容之前
     */
    private String content_before;
    /**
     * 修改内容之后
     */
    private String content_after;
    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date update_time;
    /**
     * 项目开始时间
     */
}
