package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Charges;
import com.ph.sp.domain.Tenant;

@RunWith(SpringJUnit4ClassRunner.class)
public class TenantDaoTest {
	
	@Autowired
	ITenantDao tenantDao;
	
	@Test
    public void testCreate() throws Exception {
		
		Tenant tenant = new Tenant();
		tenant.setDeleted(false);
		tenant.setEmail("hackmel@yahoo.com");
		tenant.setFirstName("A");
		tenant.setLastName("B");
		tenant.setMiddleName("C");
		tenant.setPhoneNo("12345");
		tenant.setVisitor(false);
		
	    tenantDao.create(tenant);
		
		Assert.assertTrue("Creation of tenant Failed",!(tenantDao.findTenant(tenant.getId())!=null));
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		Tenant tenant  = tenantDao.findTenant(new Long("1"));
		tenant.setFirstName("test name");
		tenantDao.edit(tenant);
		Assert.assertTrue("Update of tenant Failed",tenant.getFirstName().equals("test name"));
	}
	
	@Test
    public void testFindTenant() throws Exception {
		Tenant tenant  = tenantDao.findTenant(new Long("1"));
		Assert.assertTrue("Unable to find charges ",tenant!=null);
	}
	
	@Test
    public void tstGetAllCharges() throws Exception {
		List<Tenant> tenant = tenantDao.getAllTenant();
		Assert.assertTrue("Tenant is empty",!tenant.isEmpty());
	}
	
	@Test
    public void tstFindChargesByDescription() throws Exception {
		List<Tenant> tenant = tenantDao.findTenantByName("B", "A");
		Assert.assertTrue("Tenant is empty",!tenant.isEmpty());
	}

}
