/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.BillCharges;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface IBillChargesDao {

    public BillCharges create(BillCharges billCharges);

    public BillCharges edit(BillCharges billCharges) throws NonexistentEntityException, Exception;

    public void delete(Long id) throws NonexistentEntityException;

    public BillCharges findBillCharges(Long id);

     public List<BillCharges> getAllBillChargesByBillId(Long id);
}
