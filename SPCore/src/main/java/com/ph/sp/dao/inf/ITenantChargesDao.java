/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.TenantCharges;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface ITenantChargesDao {

    public TenantCharges create(TenantCharges billCharges);

    public TenantCharges edit(TenantCharges billCharges) throws NonexistentEntityException, Exception;

    public void delete(Long id) throws NonexistentEntityException;

    public TenantCharges findTenantCharges(Long id);

    public TenantCharges findTenantCharges(Long tenantId, Long billId);

    public List<TenantCharges> getAllTenantChargesByBillId(Long id);

    public List<TenantCharges> getAllTenantVisitorChargesByBillIdAndTenantId(Long id, Long tenantId);
}
