/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.impl;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.IChargesDao;
import com.ph.sp.domain.Charges;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hackmel
 */
@Repository
public class ChargesJpaDao implements Serializable,IChargesDao {

    
    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

   
    

    public void create(Charges charges) {

       if (entityManager != null) {
            entityManager.persist(charges);

        }
    }

    public void edit(Charges charges) throws NonexistentEntityException, Exception {

        if (entityManager != null) {
            charges = entityManager.merge(charges);
        }

        Long id = charges.getId();
        if (findCharges(id) == null) {
            throw new NonexistentEntityException("The charges with id " + id + " no longer exists.");
        }

    }

    public void delete(Long id) throws NonexistentEntityException {
        Charges charges = null;

        if (entityManager != null) {
            try {
                charges = entityManager.getReference(Charges.class, id);
                charges.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The charges with id " + id + " no longer exists.", enfe);
            }
            
            charges.setDeleted(true);
            entityManager.merge(charges);
        }


    }

    public Charges findCharges(Long id) {
        return entityManager.find(Charges.class, id);

    }
    
    
    public List<Charges> findChargesByDescription(String description) {
         Query q = entityManager.createQuery("SELECT a FROM Charges a where trim(upper(a.description)) =:description and a.deleted=false").setParameter("description", description.toUpperCase().trim());
         List<Charges> chargesList=q.getResultList();
        return chargesList;

    }

    public List<Charges> getAllCharges() {
        
        Query q = entityManager.createQuery("SELECT a FROM Charges a where a.deleted=false");
         List<Charges> chargesList=q.getResultList();
         
         return chargesList;
    }
}
