package com.bluesky.framework.account.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目基本信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProBaseInformation implements Serializable {
    /**
     *项目id
     */
    private Long pro_id;
    /**
     * 项目编码
     */
    private String pro_code;
    /**
     * 项目名称
     */
    private String pro_name;
    /**
     * 项目经理
     */
    private String pro_manager;
    /**
     * 项目成员
     */
    private String pro_member;
    /**
     * 项目开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pro_start_time;
    /**
     * 项目结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pro_end_time;
    /**
     * 项目说明
     */
    private String statement;
}
