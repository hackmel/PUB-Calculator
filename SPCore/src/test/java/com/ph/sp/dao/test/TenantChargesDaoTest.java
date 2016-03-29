package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.ITenantChargesDao;
import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.Tenant;
import com.ph.sp.domain.TenantCharges;

@RunWith(SpringJUnit4ClassRunner.class)
public class TenantChargesDaoTest {
	
	@Autowired
	ITenantChargesDao tenantChargesDao;
	@Autowired
	ITenantDao tenantDao;
	
	
	@Test
    public void testCreate() throws Exception {
		
		TenantCharges tenantCharges = new TenantCharges();
		
		Bill bill = new Bill();
		
		bill.setDescription("test bill");
		bill.setEndDate(new Date());
		bill.setStartDate(new Date());
		bill.setNumberOfDays(new Long(32));
		bill.setRemarks("test bill");
		bill.setTotalCharges(new BigDecimal("1000"));
		bill.setTotalSubCharges(new BigDecimal("1000"));
		
		tenantCharges.setBill(bill);
		tenantCharges.setCharges(new BigDecimal(100));
		tenantCharges.setManualCharges(new BigDecimal(1000));
		tenantCharges.setNoOfDaysStayed(new Long(32));
		tenantCharges.setPub(new BigDecimal(1000));
		
		Tenant tenant  = tenantDao.findTenant(new Long("1"));
		tenantCharges.setTenant(tenant);
		
		tenantChargesDao.create(tenantCharges);
		
		
		Assert.assertTrue("Creation of tenant Charges Failed",!(tenantChargesDao.findTenantCharges(tenantCharges.getId())!=null));
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		TenantCharges tenantCharges  = tenantChargesDao.findTenantCharges(new Long("1"));
		tenantCharges.setCharges(new BigDecimal(100));
		tenantChargesDao.edit(tenantCharges);
		Assert.assertTrue("Update of tenant Charges Failed",tenantCharges.getCharges().equals(new BigDecimal(100)));
	}
	
	@Test
    public void testFindTenantCharges() throws Exception {
		TenantCharges tenantCharges  = tenantChargesDao.findTenantCharges(new Long("1"));
		Assert.assertTrue("Unable to find tenant Charges ",tenantCharges!=null);
	}
	
	@Test
    public void tstGetAllCharges() throws Exception {
		List<TenantCharges> tenantCharges = tenantChargesDao.getAllTenantChargesByBillId(new Long(1)); 
		Assert.assertTrue("tenant Charges is empty",!tenantCharges.isEmpty());
	}
	
	

}
