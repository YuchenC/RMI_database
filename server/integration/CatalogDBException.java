/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.integration;

/**
 *
 * @author yuchen
 */
public class CatalogDBException extends Exception{
    public CatalogDBException(String reason) {
        super(reason);
    }
    
    public CatalogDBException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
    
}
