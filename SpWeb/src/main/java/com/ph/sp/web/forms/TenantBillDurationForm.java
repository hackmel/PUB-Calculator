/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

/**
 * 
 * @author hackmel
 */
public class TenantBillDurationForm {

	private Long tenantId;
	private String date;
	private String charges;
	private Long visitorOf;

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public void setVisitorOf(Long visitorOf) {
		this.visitorOf = visitorOf;
	}

	public Long getVisitorOf() {
		return visitorOf;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
