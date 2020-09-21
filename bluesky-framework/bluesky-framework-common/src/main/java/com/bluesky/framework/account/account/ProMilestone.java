package com.bluesky.framework.account.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目里程碑信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProMilestone implements Serializable {
    /**
     * 项目里程碑id
     */
    private Long id;
    /**
     * 项目信息id
     */
    private Long pro_id;
    /**
     * 项目任务id
     */
    private Long pro_task_id;
    /**
     * 里程碑名称
     */
    private String name;
    /**
     * 里程碑描述
     */
    private String statement;
    /**
     * 项目里程碑开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date start_time;
    /**
     * 项目里程碑结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;
    /**
     * 负责人
     */
    private String head;
    /**
     * 里程碑状态
     */

    private String status;
    /**
     * 文件或者图片路径
     */

    private String path;

}
