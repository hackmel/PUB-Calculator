/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

/**
 *
 * @author hackmel
 */
public class TenantBillForm {
    private Long tenantId;
    private String noOfDaysStayed;
   
    

    public String getNoOfDaysStayed() {
        return noOfDaysStayed;
    }

    public void setNoOfDaysStayed(String noOfDaysStayed) {
        this.noOfDaysStayed = noOfDaysStayed;
    }

  

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

	
   
    
}
