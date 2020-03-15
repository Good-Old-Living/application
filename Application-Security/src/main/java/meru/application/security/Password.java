package meru.application.security;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {

  private static final char SALT[] = { 'A', '!', '$', 'd', '5', '%', 'X', 'w', 'M', '0' };
  private static final Charset CHARSET = Charset.forName("UTF-8");

  private static String addSalt(String text) {
    
    char[] chars = text.toCharArray();
    StringBuilder strBuilder = new StringBuilder(chars.length+SALT.length);
    int i = 0;
    for (; i<chars.length; i++) {
      strBuilder.append(chars[i]);
      
      if (i<SALT.length) {
        strBuilder.append(SALT[i]);
      }
    }
    
    if (i<SALT.length) {
      for (;i<SALT.length; i++) {
        strBuilder.append(SALT[i]);
      }
    }
    
    return strBuilder.toString();
  }
  
  public static String encryptPassword(String password) {
    String saltedPassword = addSalt(password);
    try {
      
      MessageDigest msgDigest = MessageDigest.getInstance("MD5");
      byte[] chars = msgDigest.digest(saltedPassword.getBytes(CHARSET));
      
      return new BigInteger(1, chars).toString(16);
            
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static void main(String[] args) {
    System.out.println(encryptPassword("Nakul2006"));
  }
  
}
