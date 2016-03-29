/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.inf;

import com.ph.sp.domain.Tenant;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface ITenantService {
    
    public void create(Tenant tenant);

    public void edit(Tenant tenant);

    public void delete(Long id);

    public Tenant findTenant(Long id);

    public List<Tenant> getAllTenant();
    
     public List<Tenant> findTenantByName(String lname, String fname);

   

   

    
}
