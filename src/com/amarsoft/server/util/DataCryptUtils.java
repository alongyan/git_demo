package com.amarsoft.server.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class DataCryptUtils{
	private static String KEY = KeyConfig.getKey();
	private static String IVKEY = KeyConfig.getIvKey();
	private static String SALT = KeyConfig.getSalt();
	public static String SPLIT = "@@@";
	private static String CHARSET = "UTF-8";
	
	private static byte[] key = Base64.decodeBase64(KeyConfig.getKey());//Base64.decodeBase64(KeyConfig.getKey());
	private static byte[] ivkey = Base64.decodeBase64(KeyConfig.getIvKey());//Base64.decodeBase64(KeyConfig.getIvKey());
	private static String salt= KeyConfig.getSalt();
	
	public static void main(String[] args) throws Exception {
		//System.out.println("@@@0@@@1@@@2@@@3".split(SPLIT)[4]);
		String inputString=URLEncoder.encode("330121196304233710", CHARSET);
		String jiami = encrypt(inputString);
		System.out.println("加密前："+inputString);
		System.out.println("加密后："+jiami);
		
		System.out.println("解密后："+decrypt(jiami));
	}
	public static String encrypt(String plainText) throws Exception {
        if (plainText == null) return "";
        if(plainText.startsWith(SPLIT)) return plainText;
        RijndaelCrypt rijndaelCrypt = new RijndaelCrypt(key, ivkey);
        plainText += salt;
        plainText = URLEncoder.encode(plainText, CHARSET);
        String encryptText = rijndaelCrypt.encrypt(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(plainText));
        return SPLIT + KEY + SPLIT + IVKEY + SPLIT + SALT + SPLIT + encryptText;
    }

    public static String decrypt(String encrypted) throws Exception {
    	//encrypted = "@@@oWAGotW/TvafcEe557Mhd2mKevkWAOqYB/htU9fPNmw=@@@Wzdj1Q0KBAA9B9FqY2UB9A==@@@vEVLvUN0JzYj8a1TV446gw==@@@PTfEH0RjBqlf4r5uZwrHmX7cR+YqQRYg8DwrgX9BrSvhU2xuq9Ax2F3wLtVVOSbo";
        if (StrUtil.isNull(encrypted)) return "";
        if(encrypted.startsWith(SPLIT)){
        	String[] strArr = encrypted.split(SPLIT);
        	byte[] key = Base64.decodeBase64(strArr[1]);
        	byte[] ivKey = Base64.decodeBase64(strArr[2]);
        	String str = decrypt(strArr[4].trim(), strArr[3], key, ivKey);
        	return URLDecoder.decode(str, CHARSET);
        }
        return encrypted;
        
    }

    public static String decrypt(String cipherText, String salt, byte[] key, byte[] iv) throws Exception {
        if (cipherText == null) return null;
        String decrypted = decryptStringFromBytes(Base64.decodeBase64(cipherText), key, iv);
        decrypted = URLDecoder.decode(decrypted, CHARSET);
        if (org.springframework.util.StringUtils.hasText(decrypted) && decrypted.endsWith(salt) && (decrypted.length() - salt.length() > 0)) {
            return decrypted.substring(0, (decrypted.length() - salt.length()));
        }
        throw new Exception("解密异常！！");
    }

    public static String decryptStringFromBytes(byte[] cipherText, byte[] Key, byte[] IV) {
        RijndaelCrypt rijndaelCrypt = new RijndaelCrypt(Key, IV);
        String result = rijndaelCrypt.decrypt(cipherText);
        return result;
    }

    public static String encryptSimpleXor(int source) {
        int xorCode = source ^ 963854721;
        return Integer.toHexString(xorCode);
    }

    public static int decryptSimpleXor(String xorCode) {
        return Integer.parseInt(xorCode, 16) ^ 963854721;
    }

    public static String sha1(String decript) throws NoSuchAlgorithmException {
        if (decript == null)
            return null;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(decript.getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }
}
