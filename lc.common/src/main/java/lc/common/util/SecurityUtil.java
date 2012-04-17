package lc.common.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * <p>
 * 암호화 유틸 클래스
 * </p>
 *
 * @since 2010. 7. 7.
 */

public class SecurityUtil
{
    static String algorithm = "DES/ECB/PKCS5Padding"; // 알고리즘 정의 문자열
    static Key    key;                                // 키
    static Cipher cipher;                             // Cipher
    static String keyString = "lovingchurchLC";           // 암호화 키

    /**
     * <p>
     * 키 문자열 cipher instance를 초기화 시키는 메소드
     * </p>
     * @throws Exception
     */
    public static void initKeyAndCipher() throws Exception
    {
        key = getKey(keyString);
        cipher = Cipher.getInstance(algorithm);
    }

    /**
     * <p>
     * 입력된 문자열을 암호화 하는 메소드
     * </p>
     * @param input 입력 문자열
     * @return String 암호화된 문자열
     * @throws Exception
     */
    public static String encrypt(String input) throws Exception
    {
        if (key == null)
            initKeyAndCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = input.getBytes();
        byte[] outputBytes = cipher.doFinal(inputBytes);
        return Base64.encode(outputBytes);
    }

    /**
     * <p>
     * 입력된 문자열을 복호화하는 메소드
     * </p>
     * @param input 입력문자열
     * @return String 복호화된 문자열
     * @throws Exception
     */
    public static String decrypt(String input) throws Exception
    {
        if (key == null)
            initKeyAndCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] inputBytes1 = Base64.decode(input);
        byte[] outputBytes2 = cipher.doFinal(inputBytes1);
        return new String(outputBytes2);
    }
    
    public static void main(String[] args) {
    	String srcString = "asdfll";
    	try {
			System.out.println(SecurityUtil.encrypt(srcString));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * <p>
     * 키로 암호화키를 만드는 문자열
     * </p>
     * @param strkey 입력키
     * @return SecretKey 비밀키
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey getKey(String strkey) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        byte[] desKeyData = strkey.getBytes();
        DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(desKeySpec);
    }
}
