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
public class Missionpeo implements Serializable {
    private long id;
    private long mis_id;
    private long leader_id;
    private long member_id;

}
