package com.bluesky.framework.account.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 项目里程碑历史信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProMilestoneHistory implements Serializable {
    private Long id;
    private Long mil_id  ;
    private String content_before;
    private String content_after;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

}
