/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.inf;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.domain.Tenant;
import java.util.List;

/**
 *
 * @author hackmel
 */
public interface ITenantDao {

    public void create(Tenant tenant);

    public void edit(Tenant tenant) throws NonexistentEntityException, Exception;

    public void delete(Long id) throws NonexistentEntityException;

    public Tenant findTenant(Long id);

    public List<Tenant> getAllTenant();
    
     public List<Tenant>  findTenantByName(String lname,String fname);
}
