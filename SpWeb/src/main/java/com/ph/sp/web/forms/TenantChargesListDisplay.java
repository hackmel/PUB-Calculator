/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import com.ph.sp.domain.TenantCharges;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author hackmel
 */
public class TenantChargesListDisplay {
    
    private TenantCharges mainTenantCharges;
    private List<TenantCharges> visitorCharges;
    private BigDecimal totalPub;
    private BigDecimal manualCharges;

    public BigDecimal getManualCharges() {
		return manualCharges;
	}

	public void setManualCharges(BigDecimal manualCharges) {
		this.manualCharges = manualCharges;
	}

	public BigDecimal getTotalPub() {
        return totalPub;
    }

    public void setTotalPub(BigDecimal totalPub) {
        this.totalPub = totalPub;
    }

    public TenantCharges getMainTenantCharges() {
        return mainTenantCharges;
    }

    public void setMainTenantCharges(TenantCharges mainTenantCharges) {
        this.mainTenantCharges = mainTenantCharges;
    }

    public List<TenantCharges> getVisitorCharges() {
        return visitorCharges;
    }

    public void setVisitorCharges(List<TenantCharges> visitorCharges) {
        this.visitorCharges = visitorCharges;
    }
    
    
    
}
