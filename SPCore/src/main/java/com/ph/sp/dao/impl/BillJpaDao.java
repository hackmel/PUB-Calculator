/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.IBillDao;
import com.ph.sp.domain.Bill;

/**
 *
 * @author hackmel
 */
@Repository
public class BillJpaDao implements IBillDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public Bill create(Bill bill) {
        if (entityManager != null) {
            entityManager.persist(bill);

        }
        return bill;
    }

    public Bill edit(Bill bill) throws NonexistentEntityException {
        if (entityManager != null) {
            bill = entityManager.merge(bill);
        }

        Long id = bill.getId();
        if (findBill(id) == null) {
            throw new NonexistentEntityException("The charges with id " + id + " no longer exists.");
        }
        return bill;
    }

    public void delete(Long id) throws NonexistentEntityException {
        Bill bill = null;

        if (entityManager != null) {
            try {
                bill = entityManager.getReference(Bill.class, id);
                bill.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The charges with id " + id + " no longer exists.", enfe);
            }
            entityManager.remove(bill);
        }
    }

    public Bill findBill(Long id) {
        return entityManager.find(Bill.class, id);
    }

    public List<Bill> getAllBills() {
        Query q = entityManager.createQuery("SELECT a FROM Bill a ");
        List<Bill> billList = q.getResultList();

        return billList;
    }

    public List<Bill> findBillStartDate(Date start, Date end) {
        Query q = entityManager.createQuery("SELECT a FROM Bill a where (a.startDate >= :start or  a.endDate <=:end)").setParameter("start", start, TemporalType.DATE).setParameter("end", start, TemporalType.DATE);
        List<Bill> billList = q.getResultList();

        return billList;
    }
    
    
}
