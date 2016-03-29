/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author hackmel
 */
@Component
public class TenantFormValidator implements Validator {

    public boolean supports(Class<?> type) {
        return TenantForm.class.isAssignableFrom(type);
    }

    public void validate(Object o, Errors errors) {
        
        TenantForm t = (TenantForm) o;

        
        if(t.getLastName()==null || t.getLastName().trim().equals("")){
              errors.rejectValue("lastName", null, null, "Last Name is required");
        }
        
        if(t.getFirstName()==null || t.getFirstName().trim().equals("")){
              errors.rejectValue("firstName", null, null, "Fist Name is required");
        }

        



    }
}
