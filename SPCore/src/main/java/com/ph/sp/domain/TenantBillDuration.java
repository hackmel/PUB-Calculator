/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author hackmel
 */
@Entity
public class TenantBillDuration implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bill bill;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tenant tenant;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private boolean present;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TenantBillDuration)) {
            return false;
        }
        TenantBillDuration other = (TenantBillDuration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ph.sp.domain.TenantBillDuration[ id=" + id + " ]";
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
