/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.integration;

import common.Client;
import common.Credentials;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.model.File;
import common.FileDTO;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yuchen
 */
public class CatalogDAO {
    private static final String TABLE_NAME = "Files";
    private PreparedStatement loginStmt;
    private PreparedStatement createFileStmt;
    private PreparedStatement findFileStmt;
    private PreparedStatement findAllFilesStmt;
    private PreparedStatement deleteFileStmt;
    private static final String FILE_COLUMN_NAME = "FILENAME";
    private static final int SIZE_COLUMN_NAME = 0;
    private static final String OWNER_COLUMN_NAME = "OWNERNAME";
    private static final String PERMISSION_COLUMN_NAME = "false";
    
    public CatalogDAO(String dbms, String datasource) throws CatalogDBException {
        try {
            System.out.println("catadao");
            Connection connection = createDatasource(dbms, datasource);
            prepareStatements(connection);
        } catch (ClassNotFoundException | SQLException exception) {
            throw new CatalogDBException("Could not connect to datasource.", exception);
        }
    }
    
    private Connection connectToCatalogDB(String dbms, String datasource)
            throws ClassNotFoundException, SQLException, CatalogDBException {
        System.out.println("connect to catalogdb");
        if (dbms.equalsIgnoreCase("derby")) {
            System.out.println("yes i have derby");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("good start");
            return DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/" + datasource + ";create=true");
        } else if (dbms.equalsIgnoreCase("mysql")) {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + datasource, "user", "password");
        } else {
            throw new CatalogDBException("Unable to create datasource, unknown dbms.");
        }
    }
    
    public void createAccount(FileDTO file) throws CatalogDBException {
        String failureMsg = "Could not create the account: " + file;
        try {
            createFileStmt.setString(1, file.getFileName());
            createFileStmt.setInt(2, file.getFileSize());
            createFileStmt.setInt(3, file.getFileSize());
            createFileStmt.setInt(4, file.getFileSize());
            int rows = createFileStmt.executeUpdate();
            if (rows != 1) {
                throw new CatalogDBException(failureMsg);
            }
        } catch (SQLException sqle) {
            throw new CatalogDBException(failureMsg, sqle);
        }
    }

    private Connection createDatasource(String dbms, String datasource) throws
            ClassNotFoundException, SQLException, CatalogDBException {
        Connection connection = connectToCatalogDB(dbms, datasource);
        if (!CatalogTableExists(connection)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE " + TABLE_NAME
                                    + " (" + FILE_COLUMN_NAME + 
                    " VARCHAR(32) PRIMARY KEY, "
                                    + SIZE_COLUMN_NAME + " INT)" + 
                    OWNER_COLUMN_NAME + " VARCHAR(32)" + 
                    PERMISSION_COLUMN_NAME + " VARCGAR(32)");
        }
        return connection;
    }
    
    private boolean CatalogTableExists(Connection connection) throws SQLException {
        int tableNameColumn = 3;
        DatabaseMetaData dbm = connection.getMetaData();
        try (ResultSet rs = dbm.getTables(null, null, null, null)) {
            for (; rs.next();) {
                if (rs.getString(tableNameColumn).equals(TABLE_NAME)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void prepareStatements(Connection connection) throws SQLException {
        loginStmt = connection.prepareStatement("SELECT * FROM"
        + TABLE_NAME + " WHERE username = ? AND password = ?)");
        createFileStmt = connection.prepareStatement("INSERT INTO "
                                                        + TABLE_NAME + " VALUES (?, ?)");
        findFileStmt = connection.prepareStatement("SELECT * from "
                                                      + TABLE_NAME + " WHERE NAME = ?");
        findAllFilesStmt = connection.prepareStatement("SELECT * from "
                                                          + TABLE_NAME);
        deleteFileStmt = connection.prepareStatement("DELETE FROM "
                                                        + TABLE_NAME
                                                        + " WHERE name = ?");
//        changeBalanceStmt = connection.prepareStatement("UPDATE "
//                                                        + TABLE_NAME
//                                                        + " SET balance = ? WHERE name= ? ");
    }
    
    public File findFileByName(String filename) throws CatalogDBException {
        String failureMsg = "Could not search for specified account.";
        ResultSet result = null;
        try {
            findFileStmt.setString(1, filename);
            result = findFileStmt.executeQuery();
            if (result.next()) {
                return new File(filename, result.getInt(SIZE_COLUMN_NAME), 
                        result.getString(OWNER_COLUMN_NAME), result.getBoolean(PERMISSION_COLUMN_NAME));
            }
        } catch (SQLException sqle) {
            throw new CatalogDBException(failureMsg, sqle);
        } finally {
            try {
                result.close();
            } catch (Exception e) {
                throw new CatalogDBException(failureMsg, e);
            }
        }
        return null;
    }
    
    public List<File> findAllFiles() throws CatalogDBException {
        String failureMsg = "Could not list accounts.";
        List<File> files = new ArrayList<>();
        try (ResultSet result = findAllFilesStmt.executeQuery()) {
            while (result.next()) {
                files.add(new File(result.getString(FILE_COLUMN_NAME), result.getInt(SIZE_COLUMN_NAME), 
                        result.getString(OWNER_COLUMN_NAME), result.getBoolean(PERMISSION_COLUMN_NAME)));
            }
        } catch (SQLException sqle) {
            throw new CatalogDBException(failureMsg, sqle);
        }
        return files;
    }
    
    public void deleteFile(FileDTO file) throws CatalogDBException {
        try {
            deleteFileStmt.setString(1, file.getFileName());
            deleteFileStmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new CatalogDBException("Could not delete the account: " + file, sqle);
        }
    }
    
    public boolean login(String username, String password) throws CatalogDBException{
        boolean match = false;
        ResultSet result = null;
        try{
            loginStmt.setString(1, username);
            loginStmt.setString(2, password);
            result = loginStmt.executeQuery();
            if (result != null)
                match = true;
            
        } catch(Exception e){
            e.printStackTrace();
        }
        return match;
    }
}

