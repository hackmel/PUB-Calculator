/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.inf;

import java.util.Date;
import java.util.List;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.BillCharges;
import com.ph.sp.domain.TenantBillDuration;
import com.ph.sp.domain.TenantCharges;

/**
 *
 * @author hackmel
 */
public interface IBillTransactionService {

    public Bill createBill(Bill bill);

    public Bill editBill(Bill bill);

    public Bill findBill(Long id);

    public void createBillChages(List<BillCharges> billChargesList);

    public void updateBillChages(List<BillCharges> billChargesList);

    public void deleteBillChages(List<Long> id);

    public List<BillCharges> getAllBillChargesByBillId(Long id);

    public TenantCharges createTenantBill(TenantCharges tenantCharges);

    public TenantCharges editTenantBill(TenantCharges tenantCharges);

    public void deleteTenantBill(Long id);

    public List<TenantCharges> viewTenantCharges(Long id);

    public TenantCharges findTenantCharges(Long tenantId, Long billId);
    
    public TenantCharges findTenantCharges(Long id);

    public List<Bill> findBillStartDate(Date start, Date end);

    public TenantBillDuration createTenantBillDuration(TenantBillDuration tenantBillDuration);

    public TenantBillDuration editTenantBillDuration(TenantBillDuration tenantBillDuration);

    public void deleteTenantBillDuration(Long id);

    public List<TenantBillDuration> findTenantBillDuration(Long tenantId, Long billId);
    
    public void consolidate(Bill bill);
    
    public void computeSubCharges(Bill bill) ;
    
     public List<TenantCharges> getAllTenantVisitorChargesByBillIdAndTenantId(TenantCharges tenantCharges) ;
}
