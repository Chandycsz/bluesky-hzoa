package com.bluesky.framework.domain.infrastructure.model.account;

import com.bluesky.framework.account.account.Account;
import com.bluesky.framework.common.BlueskyFrameworkAppBaseTest;
import com.bluesky.framework.domain.model.account.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@DataSet("/database/dataset/account.xml")
public class AccountRepositoryImplTest extends BlueskyFrameworkAppBaseTest {

    @SpringBeanByType
    private AccountRepository accountRepository;

    @Test
    public void insert() {
        Account account = Account.builder()
                .name("姓名")
                .nickName("昵称")
                .headImg("www.headimg.com")
                .mobile("13899999999")
                .roleType(1)
                .roleId(1L)
                .organizationId(1L)
                .department("办公室")
                .idCard("3400000000")
                .password("123456")
                .lastLoginIp("192.168.1.1")
                .lastLoginTime(new Date())
                .authoritiesText("ROLE_PERSONAL")
                .sort(1)
                .status(0).build();
        Long id = accountRepository.insert(account);
        assertNotNull(id);
    }

    @Test
    public void findOne() {
        long id = 1L;
        Account account = accountRepository.findOne(id);
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(id).isEqualTo(account.getId());
    }

    @Test
    public void findByMobile() {
        String mobile = "13000000000";
        List<Account> accountList = accountRepository.findByMobile(mobile, 1);
        Assertions.assertThat(accountList).isNotEmpty();
        accountList.forEach(account -> Assertions.assertThat(account).isNotNull().hasFieldOrPropertyWithValue("mobile", mobile));
    }

    @Test
    public void findIdsByRoleId() {
    }

    @Test
    public void update() {
    }

    @Test
    public void batchUpdateStatus() {
    }

    @Test
    public void findPageByCondition() {
    }
}