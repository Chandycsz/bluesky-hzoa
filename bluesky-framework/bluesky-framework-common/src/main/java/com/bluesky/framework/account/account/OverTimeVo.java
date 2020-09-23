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
public class OverTimeVo implements Serializable {
    private long id;
    @JsonFormat(pattern="yyyy-MM-dd hh",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd hh",timezone="GMT+8")
    private Date endTime;
    private String overtime;
    private String statement;
    private long creatorId;
}
