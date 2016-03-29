/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author hackmel
 */

@Component
public class ChargesFormValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ChargesForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object o, Errors errors) {
         
        ChargesForm c=(ChargesForm)o;
        
        if(c.getDescription()==null || c.getDescription().trim().equals("")){
             errors.rejectValue("description", null,null,"Charges description is required");
        }
        
        try {
            if(c.getPrice()==null || c.getPrice().trim().equals("") ){
                c.setPrice("0.00");
            }
            
           new BigDecimal(c.getPrice()); 
        }catch(Exception e){
            errors.rejectValue("price", null,null,"Invalid number format");
        }        
        
                
           
        
    }
}
