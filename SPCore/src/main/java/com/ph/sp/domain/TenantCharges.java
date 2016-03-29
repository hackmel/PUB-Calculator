/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 
 * @author hackmel
 */
@Entity
public class TenantCharges implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private Long noOfDaysStayed;
	private BigDecimal charges;
	private BigDecimal pub;
	private BigDecimal manualCharges;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Bill bill;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Tenant tenant;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Tenant visitorOf;

	public Tenant getVisitorOf() {
		return visitorOf;
	}

	public void setVisitorOf(Tenant visitorOf) {
		this.visitorOf = visitorOf;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Long getNoOfDaysStayed() {
		return noOfDaysStayed;
	}

	public void setNoOfDaysStayed(Long noOfDaysStayed) {
		this.noOfDaysStayed = noOfDaysStayed;
	}

	public BigDecimal getPub() {
		return pub;
	}

	public void setPub(BigDecimal pub) {
		this.pub = pub;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	public BigDecimal getManualCharges() {
		return manualCharges;
	}

	public void setManualCharges(BigDecimal manualCharges) {
		this.manualCharges = manualCharges;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TenantCharges)) {
			return false;
		}
		TenantCharges other = (TenantCharges) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.ph.sp.domain.TenantCharges[ id=" + id + " ]";
	}
}
