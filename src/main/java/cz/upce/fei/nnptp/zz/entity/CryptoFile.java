/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Roman
 */
public class CryptoFile {
    
    public static String readFile(File file, String password) {
        try(FileInputStream fileInputStream = new FileInputStream(file);) {
            // TODO...
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            SecretKey secretKey = new SecretKeySpec(password.getBytes(), "DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            DataInputStream dataInputStream = new DataInputStream(cipherInputStream);
            String result = dataInputStream.readUTF();
            dataInputStream.close();
            cipher.doFinal();
            
            return result;        
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException |
                IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptoFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void  writeFile(File file, String password, String contents) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
            SecretKey secretKey = new SecretKeySpec(password.getBytes(), "DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            DataOutputStream dataOutputStream = new DataOutputStream(cipherOutputStream);
            dataOutputStream.writeUTF(contents);
            dataOutputStream.close();
            cipher.doFinal();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException |
                IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptoFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
