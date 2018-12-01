/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author yuchen
 */
public class FileException extends Exception{
    
    public FileException(String reason) {
        super(reason);
    }
    public FileException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
