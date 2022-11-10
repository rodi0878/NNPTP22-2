package cz.upce.fei.nnptp.controller;

import cz.upce.fei.nnptp.entity.*;

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
        List<Password> pwds = new ArrayList<>();
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        pwds.add(new Password(0, "sdfghjkl"));
        pwds.add(new Password(1, "ASDSAFafasdasdasdas"));
        pwds.add(new Password(2, "aaa-aaaa-"));
        pwds.add(new Password(3, passwordGenerator.generatePassword(15,
                 new PasswordGeneratorConfiguration(false, true, false))));
        pwds.add(new Password(4, passwordGenerator.generatePassword(10,
                 new PasswordGeneratorConfiguration(true, true, true))));
        pwds.add(new Password(5, passwordGenerator.generatePassword(21,
                 new PasswordGeneratorConfiguration(true, false, false))));
        String contents = new JSON().toJson(pwds);
        
        CryptoFile.writeFile(new File("test.txt"), "password",  contents);
        
        String read = CryptoFile.readFile(new File("test.txt"), "password");
        System.out.println(read);
        
    }
   
}
