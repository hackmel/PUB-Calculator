/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.impl;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.ITenantDao;
import com.ph.sp.domain.Tenant;
import com.ph.sp.service.exceptions.SpTechnicalException;
import com.ph.sp.service.inf.ITenantService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hackmel
 */
@Component
public class TenantService implements ITenantService {

    @Autowired
    private ITenantDao tenantDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void create(Tenant tenant) {
        tenantDao.create(tenant);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void edit(Tenant tenant) {
        try {
            tenantDao.edit(tenant);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TenantService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TenantService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        try {
            tenantDao.delete(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TenantService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpTechnicalException(null, ex);
        }
    }

    public Tenant findTenant(Long id) {
        Tenant t = tenantDao.findTenant(id);
        return t;
    }
    
    public List<Tenant> findTenantByName(String lname, String fname) {
        
        List<Tenant> tenantList = tenantDao.findTenantByName(lname, fname);
        return tenantList;

    }

    public List<Tenant> getAllTenant() {
        List<Tenant> tenantList=tenantDao.getAllTenant();
        return tenantList;
    }
}
