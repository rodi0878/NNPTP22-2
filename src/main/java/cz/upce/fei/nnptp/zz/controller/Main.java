package cz.upce.fei.nnptp.zz.controller;

import cz.upce.fei.nnptp.zz.entity.CryptoFile;
import cz.upce.fei.nnptp.zz.entity.JSON;
import cz.upce.fei.nnptp.zz.entity.Password;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Enterprise password manager - in console
 * - uses strong industry-based encryption algorithm
 * - stores your passwords and relevent information
 * - allows you to simply manage all stored informations
 * 
 * 
 */
public class Main {
    public static void main(String[] args) {
        List<Password> passwords = new ArrayList<>();
        passwords.add(new Password(0, "sdfghjkl"));
        passwords.add(new Password(1, "ASDSAFafasdasdasdas"));
        passwords.add(new Password(2, "aaa-aaaa-"));
        String contents = new JSON().toJson(passwords);
        
        CryptoFile.writeFile(new File("test.txt"), "password",  contents);
        
        String read = CryptoFile.readFile(new File("test.txt"), "password");
        System.out.println(read);
        
    }
   
}
