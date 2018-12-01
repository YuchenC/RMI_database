/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import common.Catalog;
import common.Client;
import common.Credentials;
import common.FileDTO;
import java.rmi.RemoteException;
import java.util.List;
import server.integration.CatalogDAO;
import server.integration.CatalogDBException;
import server.model.FileException;

/**
 *
 * @author yuchen
 */
public class Controller implements Catalog{
    private final CatalogDAO catalogDb;
    public Controller(String datasource, String dbms) throws CatalogDBException{
        System.out.println("in test");
        System.out.println(datasource);
        System.out.println(dbms);
        catalogDb = new CatalogDAO(dbms, datasource);
    }

    @Override
    public boolean login(Client remoteNode, Credentials credentials) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(long id, String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends FileDTO> list() throws FileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileDTO getFile(String filename) throws FileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void upload(long id, String filename, int size, String readOnly) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(FileDTO file) throws FileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout(long id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileDTO makeFile(String filename, Integer size, String owner, Boolean readOnly) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


//package server.controller;
//
//import common.Catalog;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.List;
//import server.integration.CatalogDAO;
//
///**
// * Implementations of the bank's remote methods, this is the only server class that can be called
// * remotely
// */
//public class Controller extends UnicastRemoteObject implements Catalog {
//    private final CatalogDAO bankDb;
//
//    public Controller(String datasource, String dbms) throws RemoteException, CatalogDBException {
//        super();
//        System.out.println("in controller");
//        bankDb = new BankDAO(dbms, datasource);
//    }
//
//    @Override
//    public synchronized List<? extends AccountDTO> listAccounts() throws AccountException {
//        try {
//            return bankDb.findAllAccounts();
//        } catch (Exception e) {
//            throw new AccountException("Unable to list accounts.", e);
//        }
//    }
//
//    @Override
//    public synchronized void createAccount(String holderName) throws AccountException {
//        String acctExistsMsg = "Account for: " + holderName + " already exists";
//        String failureMsg = "Could not create account for: " + holderName;
//        try {
//            if (bankDb.findAccountByName(holderName) != null) {
//                throw new AccountException(acctExistsMsg);
//            }
//            bankDb.createAccount(new Account(holderName, bankDb));
//        } catch (Exception e) {
//            throw new AccountException(failureMsg, e);
//        }
//    }
//
//    @Override
//    public synchronized AccountDTO getAccount(String holderName) throws AccountException {
//        if (holderName == null) {
//            return null;
//        }
//
//        try {
//            return bankDb.findAccountByName(holderName);
//        } catch (Exception e) {
//            throw new AccountException("Could not search for account.", e);
//        }
//    }
//
//    @Override
//    public synchronized void deleteAccount(AccountDTO account) throws AccountException {
//        try {
//            bankDb.deleteAccount(account);
//        } catch (Exception e) {
//            throw new AccountException("Could not delete account: " + account, e);
//        }
//    }
//
//    @Override
//    public synchronized void deposit(AccountDTO acctDTO, int amt) throws RejectedException, AccountException {
//        Account acct = (Account) getAccount(acctDTO.getHolderName());
//        acct.deposit(amt);
//    }
//
//    @Override
//    public synchronized void withdraw(AccountDTO acctDTO, int amt) throws RejectedException, AccountException {
//        Account acct = (Account) getAccount(acctDTO.getHolderName());
//        acct.withdraw(amt);
//    }
//}
//
