package com.ph.sp.dao.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.sp.dao.inf.IBillDao;
import com.ph.sp.domain.Bill;

@RunWith(SpringJUnit4ClassRunner.class)
public class BillDaoTest {
	
	@Autowired
	IBillDao billDao;
	
	@Test
    public void testCreate() throws Exception {
		
		
		Bill bill =new Bill();
		bill.setDescription("test bill");
		bill.setEndDate(new Date());
		bill.setStartDate(new Date());
		bill.setNumberOfDays(new Long(32));
		bill.setRemarks("test bill");
		bill.setTotalCharges(new BigDecimal("1000"));
		bill.setTotalSubCharges(new BigDecimal("1000"));
		
		
		
		bill = billDao.create(bill);
		
		Assert.assertTrue("Creation of Bill Failed",!(billDao.findBill(bill.getId())!=null));
		
		
	}
	
	@Test
    public void testEdit() throws Exception {
		Bill bill  = billDao.findBill(new Long("1"));
		bill.setDescription("test bill");
		billDao.edit(bill);
		Assert.assertTrue("Update of Bill Failed",bill.getDescription().equals("test bill"));
	}
	
	@Test
    public void testFindBill() throws Exception {
		Bill bill  = billDao.findBill(new Long("1"));
		Assert.assertTrue("Unable to find bill ",bill!=null);
	}
	
	@Test
    public void testGetAllBills() throws Exception {
		List<Bill> bills = billDao.getAllBills();
		Assert.assertTrue("Bill is empty",!bills.isEmpty());
	}
	

}
