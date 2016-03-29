/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;


/**
 *
 * @author hackmel
 */
public class ChargesForm {

    private Long id;
    private String description;
    private String price;
    private String subCharges;

    public boolean isSubCharges() {
        if (subCharges == null) {
            return false;
        } else if (subCharges.equalsIgnoreCase("on")) {
            return true;
        } else if (subCharges.equalsIgnoreCase("true")) {
            return true;

        }
        return false;
        
        
        
    }

    public void setSubCharges(String subCharges) {
        this.subCharges = subCharges;
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
