package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.IBillChargesDao;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.BillCharges;
import com.ph.sp.domain.Charges;

import org.junit.Assert; 

@RunWith(SpringJUnit4ClassRunner.class)
public class BillChargesDaoTest {

	@Autowired
	IBillChargesDao billChargesDao;
	
	@Test
    public void testCreate() throws Exception {
		
		BillCharges billCharges = new BillCharges();
		Bill bill = new Bill();
		Charges charges = new Charges();
		
		bill.setDescription("test bill");
		bill.setEndDate(new Date());
		bill.setStartDate(new Date());
		bill.setNumberOfDays(new Long(32));
		bill.setRemarks("test bill");
		bill.setTotalCharges(new BigDecimal("1000"));
		bill.setTotalSubCharges(new BigDecimal("1000"));
		
		charges.setDeleted(false);
		charges.setDescription("Test Charges");
		charges.setPrice(new BigDecimal("120"));
		charges.setSubCharges(false);
		
		billCharges.setActualCharges(new BigDecimal("100"));
		billCharges.setBill(bill);
		billCharges.setCharges(charges);
		
		billCharges = billChargesDao.create(billCharges);
		
		Assert.assertTrue("Creation of Bill Failed",!billChargesDao.getAllBillChargesByBillId(billCharges.getId()).isEmpty());
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		List<BillCharges> billChargesList = billChargesDao.getAllBillChargesByBillId(new Long("1"));
		BillCharges billCharges = billChargesList.get(0);
		billCharges.setActualCharges(new BigDecimal("100"));
		billCharges = billChargesDao.edit(billCharges);
		Assert.assertTrue("Update of Bill Failed",billCharges.getActualCharges().equals(new BigDecimal("100")));
	}
	
	@Test
    public void testFindBillCharges() throws Exception {
		BillCharges billCharges = billChargesDao.findBillCharges(new Long("1"));
		Assert.assertTrue("Unable to find bill Charges",billCharges!=null);
	}
	
	@Test
    public void testGetAllBillChargesByBillId() throws Exception {
		List<BillCharges> billChargesList = billChargesDao.getAllBillChargesByBillId(new Long("1"));
		Assert.assertTrue("Bill Charges is empty",!billChargesList.isEmpty());
	}
}
