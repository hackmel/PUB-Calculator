/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hackmel
 */
public class ValidationResponse {

    private String status;
    private List errorMessageList;
    private Map<String,String> modelAttribute;

    public void setModelAttribute(Map modelAttribute) {
        this.modelAttribute = modelAttribute;
    }

    public Map getModelAttribute() {
        
        if(modelAttribute==null)
            modelAttribute=new HashMap<String,String>();
        return modelAttribute;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List getErrorMessageList() {
        return this.errorMessageList;
    }

    public void setErrorMessageList(List errorMessageList) {
        this.errorMessageList = errorMessageList;
    }
    
    
}