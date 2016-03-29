/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import com.ph.sp.domain.Bill;
import com.ph.sp.domain.Tenant;
import java.util.Date;

/**
 *
 * @author hackmel
 */
public class TenantPresence {
    
    Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    boolean checked;
    Date date;
    Bill Bill;

  

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bill getBill() {
        return Bill;
    }

    public void setBill(Bill Bill) {
        this.Bill = Bill;
    }

    
}
