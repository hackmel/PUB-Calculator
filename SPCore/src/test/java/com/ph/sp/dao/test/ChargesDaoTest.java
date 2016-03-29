package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.IChargesDao;
import com.ph.sp.domain.Charges;

@RunWith(SpringJUnit4ClassRunner.class)
public class ChargesDaoTest {
	@Autowired
	IChargesDao chargesDao;
	
	@Test
    public void testCreate() throws Exception {
		
		Charges charges = new Charges();
		charges.setDeleted(false);
		charges.setDescription("Test Charges");
		charges.setPrice(new BigDecimal("120"));
		charges.setSubCharges(false);
		chargesDao.create(charges);
		
		Assert.assertTrue("Creation of charges Failed",!(chargesDao.findCharges(charges.getId())!=null));
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		Charges charges  = chargesDao.findCharges(new Long("1"));
		charges.setDescription("test charges");
		
		chargesDao.edit(charges);
		Assert.assertTrue("Update of charges Failed",charges.getDescription().equals("test charges"));
	}
	
	@Test
    public void testfindCharges() throws Exception {
		Charges charges  = chargesDao.findCharges(new Long("1"));
		Assert.assertTrue("Unable to find charges ",charges!=null);
	}
	
	@Test
    public void tstGetAllCharges() throws Exception {
		List<Charges> charges = chargesDao.getAllCharges();
		Assert.assertTrue("Charges is empty",!charges.isEmpty());
	}
	
	@Test
    public void tstFindChargesByDescription() throws Exception {
		List<Charges> charges = chargesDao.findChargesByDescription("test charges");
		Assert.assertTrue("Charges is empty",!charges.isEmpty());
	}
}
