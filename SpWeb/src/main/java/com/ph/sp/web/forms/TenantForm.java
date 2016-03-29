/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

/**
 *
 * @author hackmel
 */
public class TenantForm {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phoneNo;
    private String email;
    private String visitor;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVisitor() {

        if (visitor == null) {
            return false;
        } else if (visitor.equalsIgnoreCase("on")) {
            return true;
        } else if (visitor.equalsIgnoreCase("true")) {
            return true;

        }
        return false;

    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }
}
