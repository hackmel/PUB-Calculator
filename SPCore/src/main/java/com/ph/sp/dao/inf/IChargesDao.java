/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.Charges;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface IChargesDao {

    public void create(Charges charges);

    public void edit(Charges charges) throws NonexistentEntityException, Exception;

    public void delete(Long id) throws NonexistentEntityException;

    public Charges findCharges(Long id);

    public List<Charges> getAllCharges();
    
    public List<Charges> findChargesByDescription(String description);
}
