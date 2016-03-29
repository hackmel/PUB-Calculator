/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.inf;

import com.ph.sp.domain.Charges;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface IChargesService {

    public void create(Charges charges);

    public void edit(Charges charges);

    public void delete(Long id);

    public Charges findCharges(Long id);

    public List<Charges> getAllCharges();

    public List<Charges> findChargesByDescription(String description);
}
