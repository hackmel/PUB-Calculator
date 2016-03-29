/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Tenant;

/**
 *
 * @author hackmel
 */
@Repository
public class TenantJpaDao implements Serializable, ITenantDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void create(Tenant tenant) {
        if (entityManager != null) {
            entityManager.persist(tenant);

        }
    }

    public void edit(Tenant tenant) throws NonexistentEntityException, Exception {

        if (entityManager != null) {
            tenant = entityManager.merge(tenant);
        }

        Long id = tenant.getId();
        if (findTenant(id) == null) {
            throw new NonexistentEntityException("The tenant with id " + id + " no longer exists.");
        }

    }

    public void delete(Long id) throws NonexistentEntityException {
        Tenant tenant = null;
        if (entityManager != null) {
            try {
                tenant = entityManager.getReference(Tenant.class, id);
                tenant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tenant with id " + id + " no longer exists.", enfe);
            }

            tenant.setDeleted(true);
            entityManager.merge(tenant);
        }




    }

    public Tenant findTenant(Long id) {
        return entityManager.find(Tenant.class, id);

    }

    public List<Tenant> findTenantByName(String lname, String fname) {
        Query q = entityManager.createQuery("SELECT a FROM Tenant a where trim(upper(a.firstName))=:fname and trim(upper(a.lastName))=:lname and a.deleted=false")
                .setParameter("fname", fname.toUpperCase().trim())
                .setParameter("lname", lname.toUpperCase().trim());
        List<Tenant> tenantList = q.getResultList();
        return tenantList;

    }

    public List<Tenant> getAllTenant() {
        Query q = entityManager.createQuery("SELECT a FROM Tenant a where a.deleted=false");
        List<Tenant> tenantList = q.getResultList();

        return tenantList;
    }
}
