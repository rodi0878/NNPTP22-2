package cz.upce.fei.inptp.zz.controller;

import cz.upce.fei.inptp.zz.entity.CryptoFile;
import cz.upce.fei.inptp.zz.entity.JSON;
import cz.upce.fei.inptp.zz.entity.Password;
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
public class main {
    public static void main(String[] args) {
        List<Password> pwds = new ArrayList<>();
        pwds.add(new Password(0, "sdfghjkl"));
        pwds.add(new Password(1, "ASDSAFafasdasdasdas"));
        pwds.add(new Password(2, "aaa-aaaa-"));
        String contents = new JSON().toJson(pwds);
        
        CryptoFile.writeFile(new File("test.txt"), "password",  contents);
        
        String read = CryptoFile.readFile(new File("test.txt"), "password");
        System.out.println(read);
        
    }
   
}
