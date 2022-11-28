package cz.upce.fei.nnptp.entity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Reading/Writing passwords from/in files
 * @author Roman
 */
public class CryptoFile {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final IvParameterSpec INITIALIZATION_VECTOR = new IvParameterSpec(new byte[16]);
    private static final String KEY_CREATION_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String BASE_ALGORITHM = "AES";
    private static final int iterationCount = 65536;
    private static final int keyLength = 256;

    private static SecretKey getKeyFromPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_CREATION_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), password.getBytes(), iterationCount,keyLength );
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), BASE_ALGORITHM);
    }

    private static String encrypt(String input, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, INITIALIZATION_VECTOR);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    private static String decrypt(String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, INITIALIZATION_VECTOR);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    /**
     * Reading a file with stored passwords
     * @param file
     * @param password
     * @return null
     */
    public static String readFile(File file, String password) {

        try {
            Path path = Paths.get(file.getPath());
            String data = Files.readAllLines(path, CHARSET).stream().collect(Collectors.joining());
            return decrypt(data, getKeyFromPassword(password));
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            Logger.getLogger(CryptoFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Writing passwords into a file
     * @param file
     * @param password
     * @param contents
     */
    public static void writeFile(File file, String password, String contents) {

        try {
            Path path = Paths.get(file.getPath());
            String data = encrypt(contents, getKeyFromPassword(password));
            Files.write(path, Arrays.asList(data), CHARSET);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            Logger.getLogger(CryptoFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
