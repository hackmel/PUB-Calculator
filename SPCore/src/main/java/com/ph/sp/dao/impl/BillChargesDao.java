/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.IBillChargesDao;
import com.ph.sp.domain.BillCharges;

/**
 *
 * @author hackmel
 */

@Repository
public class BillChargesDao implements IBillChargesDao {

    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public BillCharges create(BillCharges billCharges) {
        if (entityManager != null) {
            entityManager.persist(billCharges);

        }
        return billCharges;
    }

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public BillCharges edit(BillCharges billCharges) throws NonexistentEntityException {
        if (entityManager != null) {
            billCharges = entityManager.merge(billCharges);
        }

        Long id = billCharges.getId();
        if (findBillCharges(id) == null) {
            throw new NonexistentEntityException("The bill Charges with id " + id + " no longer exists.");
        }
        return billCharges;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) throws NonexistentEntityException {
        BillCharges billCharges = null;

        if (entityManager != null) {
            try {
                billCharges = entityManager.getReference(BillCharges.class, id);
                billCharges.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bill Charges with id " + id + " no longer exists.", enfe);
            }
            entityManager.remove(billCharges);
        }
    }

    public BillCharges findBillCharges(Long id) {
        return entityManager.find(BillCharges.class, id);
    }

    public List<BillCharges> getAllBillChargesByBillId(Long id) {
        Query q = entityManager.createQuery("SELECT a FROM BillCharges a where a.bill.id=:id").setParameter("id", id);
        List<BillCharges> billChargesList = q.getResultList();

        return billChargesList;
    }
}
