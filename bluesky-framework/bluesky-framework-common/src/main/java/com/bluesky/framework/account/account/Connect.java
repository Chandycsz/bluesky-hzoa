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
public class Connect implements Serializable {
    private Long id;
    private String name;
    /**
     * 级别：-1无/1项目/2里程碑/3任务
     */
    private Integer level;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 顶级节点：0非最顶级节点，1是最顶级节点
     */
    private Boolean top;
    /**
     * 末节点:0非末节点，1末节点
     */
    private Boolean leaf;
}
