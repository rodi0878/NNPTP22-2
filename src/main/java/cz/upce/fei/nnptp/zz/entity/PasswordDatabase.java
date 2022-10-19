/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 *
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
        try {
            String passwordsInJson = CryptoFile.readFile(file, password);
            passwords = JSON.fromJson(passwordsInJson);     
        } catch(Exception ex) {
            throw ex;
        }
    }
    
    public void save() {
        JSON Json = new JSON();
        String passwordsInJson = Json.toJson(passwords);
        CryptoFile.writeFile(file, password, passwordsInJson);      
    }
    
    public void add(Password password) {
        passwords.add(password);
    }
    
    public Password findEntryByTitle(String title) {
        for (Password password : passwords) {            
            if (password.hasParameter(Parameter.StandardizedParameters.TITLE)) {
                Parameter.TextParameter titleParam = (Parameter.TextParameter) password.getParameter(Parameter.StandardizedParameters.TITLE);
                if (titleParam.getValue().equals(title)) {
                    return password;
                }
            }
        }
        return null;
    }
    
}
