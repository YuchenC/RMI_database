/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import server.model.File;
import server.model.FileException;

/**
 *
 * @author yuchen
 */
public interface Catalog extends Remote{
    public static final String SERVER_NAME_IN_REGISTRY = "Server";
    
    boolean login(Client remoteNode, Credentials credentials) throws RemoteException;
    
    void register(long id, String username) throws RemoteException;
    
    List<? extends FileDTO> list() throws FileException;
    
    FileDTO getFile(String filename) throws FileException;
    
    void upload(long id, String filename, int size, String readOnly) throws RemoteException;
    
    void delete(FileDTO file) throws FileException;
            
    void logout(long id) throws RemoteException;
    
    FileDTO makeFile(String filename, Integer size, String owner, Boolean readOnly);
}
