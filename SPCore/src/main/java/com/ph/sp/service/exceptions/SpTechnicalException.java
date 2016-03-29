/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.service.exceptions;

/**
 *
 * @author hackmel
 */
public class SpTechnicalException extends RuntimeException{
    
    public SpTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
    public SpTechnicalException(String message) {
        super(message);
    }
}
