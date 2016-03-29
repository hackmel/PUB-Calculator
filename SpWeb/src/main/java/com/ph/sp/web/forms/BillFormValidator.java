/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hackmel
 */

@Component
public class BillFormValidator implements Validator {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public boolean supports(Class<?> type) {
           return BillForm.class.isAssignableFrom(type);
    }

    public void validate(Object o, Errors errors) {
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null, null, "Description is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", null, null, "Start Date is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", null, null, "End Date is required");
        
        
         BillForm b=(BillForm)o;
        try {
            dateFormat.parse(b.getStartDate());
        } catch (ParseException ex) {
            errors.rejectValue("startDate", null,null,"Invalid date format");
        }
        
        try {
            dateFormat.parse(b.getEndDate());
        } catch (ParseException ex) {
            errors.rejectValue("endDate", null,null,"Invalid date format");
        }
        
    }
    
}
