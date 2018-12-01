/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.Client;
//import common.File;
import common.MessageException;

import java.rmi.RemoteException;

/**
 *
 * @author yuchen
 */
public class User {
    public String username;
    public String password;
    private Client remoteNode;
    private UserManager userMgr;
    private static final String DEFAULT_USERNAME = "anonymous";
    
    public User(String username, String password, Client remoteNode, UserManager mgr){
        this.username = username;
        this.password = password;
        this.remoteNode = remoteNode;
        this.userMgr = mgr;
    }
    
    public void send(String msg){
        try {
            remoteNode.recvMsg(msg);
        } catch(RemoteException re) {
            throw new MessageException("Failed to deliver message to " + username + ".");
        }
    }
    
    public boolean hasRemoteNode(Client remoteNode) {
        return remoteNode.equals(this.remoteNode);
    }
    
}
