package com.bluesky.framework.account.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRe implements Serializable {
    private long id;
    private long accountId;
    private long noticeId;
    private int status;

}
