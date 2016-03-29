/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.Bill;
import com.ph.sp.domain.Charges;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface IBillDao {

    public Bill create(Bill bill);

    public Bill edit(Bill bill)throws NonexistentEntityException, Exception;

    public void delete(Long id)throws NonexistentEntityException;

    public Bill findBill(Long id);
    
    public List<Bill> getAllBills();
    
    public List<Bill> findBillStartDate(Date start,Date end);
}
