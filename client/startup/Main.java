package client.startup;

import java.rmi.RemoteException;
import client.view.NonBlockingInterpreter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

import java.rmi.Naming;
import common.Catalog;
/**
 * Starts the chat client.
 */
public class Main {
    /**
     * @param args There are no command line arguments.
     */
    public static void main(String[] args) {
        try {
            Catalog server = (Catalog) Naming.lookup(Catalog.SERVER_NAME_IN_REGISTRY);
            new NonBlockingInterpreter().start(server);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("Could not start file system client.");
        }
    }
}

