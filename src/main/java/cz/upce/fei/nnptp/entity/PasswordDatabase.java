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
            String passwordInJSON = CryptoFile.readFile(file, password);
            System.out.println(passwordInJSON);
            passwords = new JSON().fromJson(passwordInJSON);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public void save() {
        String passwordsContext = new JSON().toJson(passwords);
        CryptoFile.writeFile(file, password, passwordsContext);
    }
    
    public void add(Password password) {
        passwords.add(password);
    }
    
    public Password findEntryByTitle(String title) {
        for (Password password : passwords) {
            
            if (password.hasParameter(Parameter.StandardizedParameters.TITLE)) {
                Parameter.TextParameter titleParam;
                titleParam = (Parameter.TextParameter)password.getParameter(Parameter.StandardizedParameters.TITLE);
                if (titleParam.getValue().equals(title)) {
                    return password;
                }
            }
        }
        return null;
    }
    
}
