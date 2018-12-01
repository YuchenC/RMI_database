package server.startup;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import server.controller.Controller;
import server.integration.CatalogDBException;
import common.Catalog;

import server.controller.Controller;

/**
 * Starts the chat servant and binds it in the RMI registry.
 */
public class Server {
    private static final String USAGE = "java bankjdbc.Server [bank name in rmi registry] "
                                        + "[bank database name] [dbms: derby or mysql]";
    private String fileSystem = Catalog.SERVER_NAME_IN_REGISTRY;
    private String datasource = "CatalogDB";
    private String dbms = "derby";

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.parseCommandLineArgs(args);
            server.startRMIServant();
            System.out.println("File system server started.");
        } catch (RemoteException | MalformedURLException | CatalogDBException e) {
            System.out.println("Failed to start file system server.");
        }
    }

    private void startRMIServant() throws RemoteException, MalformedURLException, CatalogDBException {
        try {
            System.out.println("here");
            LocateRegistry.getRegistry().list();
        } catch (RemoteException noRegistryRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
        System.out.println("after try catch here");
        Controller contr = new Controller(datasource, dbms);
        System.out.println("back to server main");
        Naming.rebind(fileSystem, contr);
        //Controller t = new Controller(datasource, dbms);
    }

    private void parseCommandLineArgs(String[] args) {
        if (args.length > 3 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        if (args.length > 0) {
            fileSystem = args[0];
        }

        if (args.length > 1) {
            datasource = args[1];
        }

        if (args.length > 2) {
            dbms = args[2];
        }
    }
}
