//https://www.albertcoronado.com/2019/05/15/encriptar-y-desencriptar-datos-en-java/

package AccesoDatos;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Hash {
        
    private static final byte[] keyValue = "!@#$!@#$%^&**&^%".getBytes();
    private static final String ALGORITHM = "AES";
    
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SECURE_TOKEN_LENGTH = 256;
    private static final SecureRandom random = new SecureRandom();
    private static final char[] symbols = CHARACTERS.toCharArray();
    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];
    
    public static String encrypt(String textToEncrypt) throws Exception {       
        Key aesKey = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] encryptedValue = cipher.doFinal(textToEncrypt.getBytes());
        
        Base64.Encoder encoderBase64 = Base64.getEncoder();
        String encryptedByteValue = encoderBase64.encodeToString(encryptedValue);
        
        return encryptedByteValue;
    }
    
    public static String decrypt(String encryptedValue) throws Exception {
        Base64.Decoder decoderBase64 = Base64.getDecoder();
        byte[] encryptedData = decoderBase64.decode(encryptedValue);
        
        Key aesKey = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, aesKey);

        byte[] decrypted = c.doFinal(encryptedData);
        
        return new String(decrypted);
    }
    
    private static Key generateKey() throws Exception {
        Key aesKey = new SecretKeySpec(keyValue, ALGORITHM);
        return aesKey;
    }
    
    public static String getRandomString(int i) { 
        byte[] bytearray = new byte[256];
        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray); 

        mystring 
            = new String(bytearray, Charset.forName("UTF-8")); 
            
        thebuffer = new StringBuffer();
        
        //remove all spacial char 
        theAlphaNumericS 
            = mystring 
                .replaceAll("[^A-Z0-9]", ""); 

        //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) { 

            if (Character.isLetter(theAlphaNumericS.charAt(m)) 
                    && (i > 0) 
                || Character.isDigit(theAlphaNumericS.charAt(m)) 
                    && (i > 0)) { 

                thebuffer.append(theAlphaNumericS.charAt(m)); 
                i--; 
            } 
        } 

        return thebuffer.toString(); 
    }
    
    public static String secureTokenGenerator(){
        for (int idx = 0; idx < buf.length; ++idx){
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        
        return new String(buf);
    }
}
