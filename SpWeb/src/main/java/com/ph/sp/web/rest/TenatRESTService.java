/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ph.sp.service.inf.ITenantService;
import com.ph.sp.web.forms.TenantForm;

/**
 *
 * @author hackmel
 */

@Controller
@RequestMapping("api")

public class TenatRESTService {
    
    
    @Autowired
    ITenantService tenantService;
    
    
    @RequestMapping(value="createTenant",method= RequestMethod.POST)
    
    public @ResponseBody TenantForm createTenant(@RequestBody TenantForm tenantForm) {
        
       
        TenantForm t=new TenantForm();
        
        t=tenantForm;
        
        
        return t;




    }
    
    
    
}
