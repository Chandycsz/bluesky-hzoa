package com.bluesky.framework.boot;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.bluesky.framework.account.account.*;
import com.bluesky.framework.account.auth.AccountAuthorityManager;
import com.bluesky.framework.account.auth.RoleAuthorityManager;
import com.bluesky.framework.account.log.AccountLoginLogManager;
import com.bluesky.framework.account.log.AccountOperateLogManager;
import com.bluesky.framework.account.organization.OrganizationManager;
import com.bluesky.framework.account.region.RegionManager;
import com.bluesky.framework.account.role.RoleManager;
import com.bluesky.framework.setting.MenuSourceManager;
import com.bluesky.framework.setting.PageSettingManager;
import com.bluesky.framework.account.account.NoticeManager;
import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * auto configuration
 */
@Configuration
@EnableConfigurationProperties(FrameworkProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class FrameworkAutoConfiguration extends DubboBasedAutoConfiguration {
    @Autowired
    private FrameworkProperties properties;

    /**
     * setting
     **/
    @Bean
    public ReferenceBean pageSettingManager() {
        return getConsumerBean(PageSettingManager.class, properties.getVersion(), properties.getTimeout());
    }

    @Bean
    public ReferenceBean menuSourceManager() {
        return getConsumerBean(MenuSourceManager.class, properties.getVersion(), properties.getTimeout());
    }

    /**
     * account
     **/
    @Bean
    public ReferenceBean accountManager() {
        return getConsumerBean(AccountManager.class, properties.getVersion(), properties.getTimeout());
    }


    @Bean
    public ReferenceBean regionManager() {
        return getConsumerBean(RegionManager.class, properties.getVersion(), properties.getTimeout());
    }


    @Bean
    public ReferenceBean organizationManager() {
        return getConsumerBean(OrganizationManager.class, properties.getVersion(), properties.getTimeout());
    }


    @Bean
    public ReferenceBean roleManager() {
        return getConsumerBean(RoleManager.class, properties.getVersion(), properties.getTimeout());
    }


    @Bean
    public ReferenceBean accountLoginLogManager() {
        return getConsumerBean(AccountLoginLogManager.class, properties.getVersion(), properties.getTimeout());
    }


    @Bean
    public ReferenceBean accountOperateLogManager() {
        return getConsumerBean(AccountOperateLogManager.class, properties.getVersion(), properties.getTimeout());
    }

    @Bean
    public ReferenceBean roleAuthorityManager() {
        return getConsumerBean(RoleAuthorityManager.class, properties.getVersion(), properties.getTimeout());
    }

    @Bean
    public ReferenceBean accountAuthorityManager() {
        return getConsumerBean(AccountAuthorityManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean ProBaseInformationManager() {
        return getConsumerBean(ProBaseInformationManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean PhInfoManager() {
        return getConsumerBean(PhInfoManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean ProMilestone() {
        return getConsumerBean(ProMilestoneManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean ProMilestoneHistoryManager() {
        return getConsumerBean(ProMilestoneHistoryManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean ConnectManager() {
        return getConsumerBean(ConnectManager.class, properties.getVersion(), properties.getTimeout());
    }
    @Bean
    public ReferenceBean MissionManager() {
        return getConsumerBean(MissionManager.class, properties.getVersion(), properties.getTimeout());
    }

    @Bean
    public ReferenceBean NoticeManager() {
        return getConsumerBean(NoticeManager.class, properties.getVersion(), properties.getTimeout());
    }
}
