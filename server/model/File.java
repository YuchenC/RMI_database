/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.*;
import java.util.StringJoiner;
import server.integration.CatalogDAO;

/**
 *
 * @author yuchen
 */
public class File implements FileDTO{
    private transient CatalogDAO catalogDB;
    
    private final String name;
    private final Integer size;
    private final String owner;
    private final Boolean readOnly;
    
    public File(String name, Integer size, String owner, Boolean readOnly, CatalogDAO catalogDB) {
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.readOnly = readOnly;
        this.catalogDB = catalogDB;
    }

    public File(String name, Integer size, String owner, Boolean readOnly) {
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.readOnly = readOnly;
    }

    @Override
    public String getFileName(){
        return this.name;
    }
    
    @Override
    public int getFileSize(){
        return this.size;
    }
    
    @Override
    public String getFileOwner() {
        return this.owner;
    }
    
    @Override
    public boolean getFilePermit() {
        return this.readOnly;
    }
}
