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
public class Comments implements Serializable {
    private Long managerId;
    @JsonFormat(pattern="yyyy-MM-dd HH",timezone="GMT+8")
    private Date createTime;
    private String review;
    private int choice;
    private Long NId;

}
