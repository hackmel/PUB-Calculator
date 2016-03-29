/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.IBillChargesDao;
import com.ph.sp.dao.inf.IBillDao;
import com.ph.sp.dao.inf.ITenantBillDurationDao;
import com.ph.sp.dao.inf.ITenantChargesDao;
import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.BillCharges;
import com.ph.sp.domain.Tenant;
import com.ph.sp.domain.TenantBillDuration;
import com.ph.sp.domain.TenantCharges;
import com.ph.sp.service.exceptions.SpTechnicalException;
import com.ph.sp.service.inf.IBillTransactionService;

/**
 * 
 * @author hackmel
 */
@Component
public class BillTransactionService implements IBillTransactionService {

	@Autowired
	private IBillDao billDao;
	@Autowired
	private IBillChargesDao billChargesDao;
	@Autowired
	private ITenantChargesDao tenantChargesDao;
	@Autowired
	private ITenantBillDurationDao tenantBillDurationDao;
	@Autowired
	private ITenantDao tenantDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Bill createBill(Bill bill) {
		return billDao.create(bill);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void createBillChages(List<BillCharges> billChargesList) {
		try {

			for (BillCharges billCharges : billChargesList) {
				billChargesDao.create(billCharges);
			}
		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateBillChages(List<BillCharges> billChargesList) {
		try {

			for (BillCharges billCharges : billChargesList) {
				billChargesDao.edit(billCharges);
			}
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteBillChages(List<Long> id) {
		try {

			for (Long billChargesId : id) {
				billChargesDao.delete(billChargesId);
			}
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<BillCharges> getAllBillChargesByBillId(Long id) {
		return billChargesDao.getAllBillChargesByBillId(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Bill editBill(Bill bill) {
		try {
			bill = billDao.edit(bill);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);

		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
		return bill;
	}

	public Bill findBill(Long id) {
		return billDao.findBill(id);
	}

	public List<Bill> findBillStartDate(Date start, Date end) {
		List<Bill> tmpBillList = billDao.findBillStartDate(start, end);
		List<Bill> billList = new ArrayList<Bill>();

		for (Bill bill : tmpBillList) {

			if (bill.getStartDate().after(start)
					|| bill.getStartDate().equals(start)) {
				if (bill.getEndDate().before(end)
						|| bill.getEndDate().equals(end)) {
					billList.add(bill);
				}
			}
		}

		return billList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TenantCharges createTenantBill(TenantCharges tenantCharges) {
		return tenantChargesDao.create(tenantCharges);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TenantCharges editTenantBill(TenantCharges tenantCharges) {
		try {
			tenantCharges = tenantChargesDao.edit(tenantCharges);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);

		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}

		return tenantCharges;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteTenantBill(Long id) {
		try {
			tenantChargesDao.delete(id);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);

		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}

	}

	public List<TenantCharges> viewTenantCharges(Long id) {
		List<TenantCharges> tenantChargesList = tenantChargesDao
				.getAllTenantChargesByBillId(id);
		return tenantChargesList;
	}

	public TenantCharges findTenantCharges(Long tenantId, Long billId) {
		return tenantChargesDao.findTenantCharges(tenantId, billId);

	}
	
	public TenantCharges findTenantCharges(Long id) {
		return tenantChargesDao.findTenantCharges(id);

    }

	/**
	 * 
	 * @param tenantCharges
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<TenantCharges> getAllTenantVisitorChargesByBillIdAndTenantId(
			TenantCharges tenantCharges) {

		List<TenantCharges> tenantChargesList = tenantChargesDao
				.getAllTenantVisitorChargesByBillIdAndTenantId(tenantCharges
						.getBill().getId(), tenantCharges.getTenant().getId());

		return tenantChargesList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TenantBillDuration createTenantBillDuration(
			TenantBillDuration tenantBillDuration) {
		return tenantBillDurationDao.create(tenantBillDuration);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TenantBillDuration editTenantBillDuration(
			TenantBillDuration tenantBillDuration) {

		try {
			tenantBillDuration = tenantBillDurationDao.edit(tenantBillDuration);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);

		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
		return tenantBillDuration;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteTenantBillDuration(Long id) {
		try {
			tenantBillDurationDao.delete(id);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);

		} catch (Exception ex) {
			Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new SpTechnicalException(null, ex);
		}
	}

	public List<TenantBillDuration> findTenantBillDuration(Long tenantId,
			Long billId) {

		return tenantBillDurationDao.findTenantBillDuration(tenantId, billId);
	}

	public Map<Date, List<Tenant>> collectBillDurationByTenantType(Long billId,
			boolean visitor) {

		Map<Date, List<Tenant>> map = new HashMap<Date, List<Tenant>>();

		List<TenantBillDuration> tenantBillDurations = tenantBillDurationDao
				.findTenantBillDuration(billId);

		if (tenantBillDurations != null) {
			for (TenantBillDuration t : tenantBillDurations) {

				if (t.getTenant().isVisitor() == visitor) {
					if (!map.containsKey(t.getDate())) {
						List<Tenant> tenantList = new ArrayList<Tenant>();
						tenantList.add(t.getTenant());
						map.put(t.getDate(), tenantList);
					} else {
						List<Tenant> tenantList = map.get(t.getDate());
						tenantList.add(t.getTenant());
					}
				}

			}
		}

		return map;

	}

	private BigDecimal getTotalPUBFromTenantWithNoDuration(Long billId) {

		List<TenantCharges> tenantChargesList = tenantChargesDao
				.getAllTenantChargesByBillId(billId);
		BigDecimal total = new BigDecimal("0.00");
		for (TenantCharges tenantCharges : tenantChargesList) {
			if (tenantCharges.getManualCharges() != null
					&& tenantCharges.getManualCharges().doubleValue() > 0) {
				total = total.add(tenantCharges.getManualCharges());
			}
		}

		return total;

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void consolidate(Bill bill) {

		BigDecimal totalPUBWithNoDuration = getTotalPUBFromTenantWithNoDuration(bill
				.getId());
		Map<Date, List<Tenant>> vistorMap = collectBillDurationByTenantType(
				bill.getId(), true);
		Map<Date, List<Tenant>> tenantOnVacationMap = collectBillDurationByTenantType(
				bill.getId(), false);

		List<Tenant> tenantList = new ArrayList<Tenant>();

		for (Tenant tenant : tenantDao.getAllTenant()) {
			if (!tenant.isVisitor()) {
				tenantList.add(tenant);
			}
		}

		BigDecimal totalCharges = new BigDecimal(bill.getTotalCharges()
				.toString());
		totalCharges = totalCharges.subtract(totalPUBWithNoDuration);

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTime(bill.getStartDate());
		endDate.setTime(bill.getEndDate());

		for (long i = 0; i < bill.getNumberOfDays(); i++) {

			List<Tenant> visitors = new ArrayList<Tenant>();
			List<Tenant> tenants = new ArrayList<Tenant>();

			if (vistorMap.containsKey(startDate.getTime())) {
				visitors = vistorMap.get(startDate.getTime());
			}

			if (tenantOnVacationMap.containsKey(startDate.getTime())) {
				tenants = tenantOnVacationMap.get(startDate.getTime());
			}

			int totalTenantAndVisitor = (tenantList.size() - tenants.size())
					+ visitors.size();

			BigDecimal chagesPerDay = new BigDecimal(totalCharges.divide(
					new BigDecimal(bill.getNumberOfDays()),
					RoundingMode.HALF_EVEN).toString());
			chagesPerDay = chagesPerDay.setScale(2);

			System.out.println("totalPUBWithNoDuration:"
					+ totalPUBWithNoDuration);
			System.out.println("totalCharges:" + totalCharges);
			System.out.println("tenantList:" + tenantList.size());
			System.out.println("tenants:" + tenants.size());
			System.out.println("visitors:" + visitors.size());
			System.out
					.println("totalTenantAndVisitor:" + totalTenantAndVisitor);
			System.out.println("chagesPerDay:" + chagesPerDay);

			chagesPerDay = chagesPerDay.divide(new BigDecimal(
					totalTenantAndVisitor), RoundingMode.HALF_EVEN);

			for (Tenant v : visitors) {
				TenantCharges t = findTenantCharges(v.getId(), bill.getId());
				if (t != null) {
					if (t.getPub() == null) {
						t.setPub(chagesPerDay);
					} else {
						t.setPub(t.getPub().add(chagesPerDay));
					}
					editTenantBill(t);
				}

			}

			for (Tenant tenant : tenantList) {
				TenantCharges t = findTenantCharges(tenant.getId(),
						bill.getId());

				if (!tenants.contains(tenant)) {
					if (t != null) {
						if (t.getPub() == null) {
							t.setPub(chagesPerDay);
						} else {
							t.setPub(t.getPub().add(chagesPerDay));
						}
						editTenantBill(t);
					}

				}
			}

			startDate.add(Calendar.DATE, 1);
		}

		computeSubCharges(bill);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void computeSubCharges(Bill bill) {
		List<Tenant> tenantList = new ArrayList<Tenant>();

		if (bill.getTotalSubCharges() != null) {
			for (Tenant tenant : tenantDao.getAllTenant()) {
				if (!tenant.isVisitor()) {
					tenantList.add(tenant);
				}
			}

			BigDecimal subCharges = bill.getTotalSubCharges().divide(
					new BigDecimal(tenantList.size()), RoundingMode.HALF_EVEN);
			subCharges.setScale(2);

			for (Tenant tenant : tenantList) {
				if (!tenant.isVisitor()) {
					TenantCharges t = findTenantCharges(tenant.getId(),
							bill.getId());
					if (t != null) {
						t.setCharges(subCharges);
						editTenantBill(t);
					}

				}

			}
		}

	}
}
