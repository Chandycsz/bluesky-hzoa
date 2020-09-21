package com.bluesky.framework.common;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;

import javax.sql.DataSource;

/**
 * unitils test
 *
 * @author linux_china
 */
public class UnitilsCheckTest extends UnitilsJUnit4 {
    @TestDataSource
    private DataSource ds;

    @Test
    public void testSyncDatabase() {
        Assert.assertNotNull(ds);
    }

}
