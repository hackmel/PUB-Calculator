/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.TenantBillDuration;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface ITenantBillDurationDao {

    public TenantBillDuration create(TenantBillDuration tenantBillDuration);

    public TenantBillDuration edit(TenantBillDuration tenantBillDuration) throws NonexistentEntityException, Exception;

    public void delete(Long id) throws NonexistentEntityException;

    public  List<TenantBillDuration> findTenantBillDuration(Long tenantId, Long billId);
    
    public  List<TenantBillDuration> findTenantBillDuration(Long billId);
}
