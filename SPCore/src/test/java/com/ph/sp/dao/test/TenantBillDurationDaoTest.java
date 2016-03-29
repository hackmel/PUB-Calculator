package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.ITenantBillDurationDao;
import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.BillCharges;
import com.ph.sp.domain.Charges;
import com.ph.sp.domain.Tenant;
import com.ph.sp.domain.TenantBillDuration;
import com.ph.sp.domain.TenantCharges;

@RunWith(SpringJUnit4ClassRunner.class)
public class TenantBillDurationDaoTest {
	@Autowired
	ITenantBillDurationDao tenantBillDurationDao;
	@Autowired
	ITenantDao tenantDao;
	
	@Test
    public void testCreate() throws Exception {
		
		TenantBillDuration tenantBillDuration = new TenantBillDuration();
		Bill bill = new Bill();
		
		bill.setDescription("test bill");
		bill.setEndDate(new Date());
		bill.setStartDate(new Date());
		bill.setNumberOfDays(new Long(32));
		bill.setRemarks("test bill");
		bill.setTotalCharges(new BigDecimal("1000"));
		bill.setTotalSubCharges(new BigDecimal("1000"));
		
		Tenant tenant  = tenantDao.findTenant(new Long("1"));
		
		tenantBillDuration.setBill(bill);
		tenantBillDuration.setTenant(tenant);
		tenantBillDuration.setDate(new Date());
		tenantBillDuration.setPresent(true);
		
		tenantBillDurationDao.create(tenantBillDuration);
		
		
		Assert.assertTrue("Creation of tenant Bill Duration Failed",!(tenantBillDurationDao.findTenantBillDuration(tenantBillDuration.getId())!=null));
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		List<TenantBillDuration> tenantBillDurations  = tenantBillDurationDao.findTenantBillDuration(new Long("1"));
		
		TenantBillDuration tenantBillDuration = tenantBillDurations.get(0);
		tenantBillDuration.setPresent(false);
		tenantBillDurationDao.edit(tenantBillDuration);
		Assert.assertTrue("Update of tenant Bill Durations Failed",!tenantBillDuration.isPresent());
	}
	
	@Test
    public void testFindTenantBillDuration() throws Exception {

		List<TenantBillDuration> tenantBillDurations   = tenantBillDurationDao.findTenantBillDuration(new Long("1"));
		Assert.assertTrue("Unable to find tenant Bill Duration ",!tenantBillDurations.isEmpty());
	}

	
	
	

}
