/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.impl;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.IChargesDao;
import com.ph.sp.domain.Charges;
import com.ph.sp.service.exceptions.SpTechnicalException;
import com.ph.sp.service.inf.IChargesService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hackmel
 */
@Component
public class ChargesService implements IChargesService {

    @Autowired
    private IChargesDao chargesDao;

    public void setChargesDao(IChargesDao chargesDao) {
        this.chargesDao = chargesDao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void create(Charges charges) {
        chargesDao.create(charges);
    }

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void edit(Charges charges) {
        try {
            chargesDao.edit(charges);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);

        } catch (Exception ex) {
            Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        try {
            chargesDao.delete(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ChargesService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);

        }
    }

    public Charges findCharges(Long id) {
        Charges c = chargesDao.findCharges(id);
        return c;

    }
    
    
    public List<Charges> findChargesByDescription(String description){
         List<Charges> chargesList= chargesDao.findChargesByDescription(description);
        return chargesList;
        
    }
    
    public List<Charges> getAllCharges(){
        List<Charges> chargesList= chargesDao.getAllCharges();
        return chargesList;
        
        
    }
}
