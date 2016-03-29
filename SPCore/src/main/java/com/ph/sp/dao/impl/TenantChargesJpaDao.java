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

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.ITenantChargesDao;
import com.ph.sp.domain.TenantCharges;

/**
 *
 * @author hackmel
 */
@Repository
public class TenantChargesJpaDao implements ITenantChargesDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public TenantCharges create(TenantCharges tenantCharges) {
        if (entityManager != null) {
            entityManager.persist(tenantCharges);

        }
        return tenantCharges;
    }

    public TenantCharges edit(TenantCharges tenantCharges) throws NonexistentEntityException, Exception {
        if (entityManager != null) {
            tenantCharges = entityManager.merge(tenantCharges);
        }

        Long id = tenantCharges.getId();
        if (findTenantCharges(id) == null) {
            throw new NonexistentEntityException("The bill Charges with id " + id + " no longer exists.");
        }
        return tenantCharges;
    }

    public void delete(Long id) throws NonexistentEntityException {
        TenantCharges tenantCharges = null;

        if (entityManager != null) {
            try {
                tenantCharges = entityManager.getReference(TenantCharges.class, id);
                tenantCharges.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bill Charges with id " + id + " no longer exists.", enfe);
            }
            entityManager.remove(tenantCharges);
        }


    }

    public TenantCharges findTenantCharges(Long id) {
        return entityManager.find(TenantCharges.class, id);

    }
    
    
    public TenantCharges findTenantCharges(Long tenantId,Long billId) {
        Query q = entityManager.createQuery("SELECT a FROM TenantCharges a where a.bill.id=:billId and a.tenant.id=:tenantId").setParameter("tenantId", tenantId).setParameter("billId", billId);
        List<TenantCharges> tenantChargesList = q.getResultList();
        
        if(!tenantChargesList.isEmpty())
            return tenantChargesList.get(0);
        
        else
            return null;
            

    }

    public List<TenantCharges> getAllTenantChargesByBillId(Long id) {
        Query q = entityManager.createQuery("SELECT a FROM TenantCharges a where a.bill.id=:id").setParameter("id", id);
        List<TenantCharges> tenantChargesList = q.getResultList();

        return tenantChargesList;
    }
    
    public List<TenantCharges> getAllTenantVisitorChargesByBillIdAndTenantId(Long id,Long tenantId) {
        Query q = entityManager.createQuery("SELECT a FROM TenantCharges a where a.bill.id=:id and a.visitorOf.id=:tenantId").setParameter("id", id).setParameter("tenantId", tenantId);
        List<TenantCharges> tenantChargesList = q.getResultList();

        return tenantChargesList;
    }
}
