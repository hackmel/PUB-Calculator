/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hackmel
 */

@Component
public class TenantBillFormValidator implements Validator {

    public boolean supports(Class<?> type) {
        return TenantBillForm.class.isAssignableFrom(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noOfDaysStayed", null, null, "No Of Days Stayed is required");
        
         TenantBillForm t=(TenantBillForm)o;
        
        try {
           new BigDecimal(t.getNoOfDaysStayed()); 
        }catch(Exception e){
            errors.rejectValue("noOfDaysStayed",null,null, "Invalid number format");
        }   
        
    }
    
    
}
