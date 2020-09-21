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
public class MissionList implements Serializable {
    private Long id;
    private Long pro_id;
    private Long mile_id;
    private String headline;
    private String statement;
    private String status;
    private String type;
    private String priority;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date deadline;
}
