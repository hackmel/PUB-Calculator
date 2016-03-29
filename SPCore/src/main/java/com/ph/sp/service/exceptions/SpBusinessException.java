/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.exceptions;

/**
 *
 * @author hackmel
 */
public class SpBusinessException extends Exception {
    
     public SpBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public SpBusinessException(String message) {
        super(message);
    }
}
