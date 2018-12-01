package client.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import common.Client;
import common.Credentials;
import common.FileDTO;
import java.util.List;
import common.Catalog;

/**
 * Reads and interprets user commands. The command interpreter will run in a separate thread, which
 * is started by calling the <code>start</code> method. Commands are executed in a thread pool, a
 * new prompt will be displayed as soon as a command is submitted to the pool, without waiting for
 * command execution to complete.
 */
public class NonBlockingInterpreter implements Runnable {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
    private final Client myRemoteObj;
    private Catalog server;
    private long myIdAtServer;
    private boolean loggedIn = false;
    private boolean receivingCmds = false;

    public NonBlockingInterpreter() throws RemoteException {
        myRemoteObj = new ConsoleOutput();
    }

    /**
     * Starts the interpreter. The interpreter will be waiting for user input when this method
     * returns. Calling <code>start</code> on an interpreter that is already started has no effect.
     */
    public void start(Catalog server) {
        this.server = server;
        if (receivingCmds) {
            return;
        }
        receivingCmds = true;
        new Thread(this).start();
    }

    /**
     * Interprets and performs user commands.
     */
    @Override
    public void run() {
        while (receivingCmds) {
            try {
                CmdLine cmdLine = new CmdLine(readNextLine());
                FileDTO file = null;
                switch (cmdLine.getCmd()) {
                    case LOGOUT:
                        receivingCmds = false;
                        server.logout(myIdAtServer);
                        boolean forceUnexport = false;
                        UnicastRemoteObject.unexportObject(myRemoteObj, forceUnexport);
                        outMgr.println("You have logged out.");
                        break;
                    case LOGIN:
                        lookupServer(cmdLine.getParameter(0));
                        loggedIn
                                = server.login(myRemoteObj,
                                               new Credentials(cmdLine.getParameter(1),
                                                               cmdLine.getParameter(2)));
                        if (loggedIn == true)
                            outMgr.println("Welcome to file system, " + cmdLine.getParameter(1) +". You have logged in.");
                        else
                            outMgr.println("Log in failed.");
                        break;
                    case REGISTER:
                        break;
                    case LIST:
                        List<? extends FileDTO> files = server.list();
                        for (FileDTO singleFile : files) {
                            outMgr.println(singleFile.getFileName() + " " + 
                                    singleFile.getFileSize() + " " + 
                                    singleFile.getFileOwner() + " " + 
                                    singleFile.getFilePermit());
                        }                         
                        break;
                    case HELP:
                        break;                       
                    case OPEN:
//                        FileDTO file = server.open(cmdLine.getParameter(0));
//                        if (file != null)
//                            outMgr.println(file.getFileName());
//                        else
//                            outMgr.println("No such file.");
                        break;
                    case UPLOAD:
                        // id, filename, size, readOnly
//                        if (cmdLine.getParameter(2) == null){
//                            outMgr.println("UPLOAD filename, size, ro(Read only)/rw(permit read and write");
//                            break;
//                        }
//                        server.upload(myIdAtServer, cmdLine.getParameter(0), Integer.parseInt(cmdLine.getParameter(1)), cmdLine.getParameter(2));
//                        outMgr.println("File " + cmdLine.getParameter(0) + " is uploaded.");
                        break;
                    case DELETE:
                        file = server.getFile(cmdLine.getParameter(0));
                        server.delete(file);
                    default:
                        //server.broadcastMsg(myIdAtServer, cmdLine.getUserInput());
                }
            } catch (Exception e) {
                System.err.println(e);
                outMgr.println("Operation failed");
            }
        }
    }

    private void lookupServer(String host) throws NotBoundException, MalformedURLException,
                                                  RemoteException {
        server = (Catalog) Naming.lookup("//" + host + "/" + Catalog.SERVER_NAME_IN_REGISTRY);
    }

    private String readNextLine() {
        outMgr.print(PROMPT);
        return console.nextLine();
    }

    private class ConsoleOutput extends UnicastRemoteObject implements Client {

        public ConsoleOutput() throws RemoteException {
        }

        @Override
        public void recvMsg(String msg) {
            outMgr.println((String) msg);
        }
    }
}

