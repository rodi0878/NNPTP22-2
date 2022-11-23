/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Managing passwords saved in files
 * @author Roman
 */
public class PasswordDatabase {
    private File file;
    private String password;
    
    private List<Password> passwords;

    public PasswordDatabase(File file, String password) {
        this.file = file;
        this.password = password;
        this.passwords = new ArrayList<>();
    }
    
    public void load() {
        // TODO: use JSON and CryptoFile to load
        // TODO: throw exceptions when error
    }
    
    public void save() {
        String contents = JSON.toJson(this.passwords);
        CryptoFile.writeFile(this.file, this.password, contents);
    }
    
    /**
     * Adds a password to a list
     * @param password
     */
    public void add(Password password) {
        passwords.add(password);
    }
    
    /**
     * Find a password by title from parameters
     * @param title
     * @return password
     */
    public Password findEntryByTitle(String title) {
        for (Password password : passwords) {
            
            if (password.hasParameter(ParameterType.TITLE.toString())) {
                Parameter.TextParameter titleParam = (Parameter.TextParameter)password.getParameter(ParameterType.TITLE.toString());
                if (titleParam.getValue().equals(title)) {
                    return password;
                }
            }
        }
        return null;
    }
    
}
