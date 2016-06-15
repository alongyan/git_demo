package com.amarsoft.server.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RijndaelCrypt {

	public static final String TAG = "RijndaelCrypt";

	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	private SecretKey _password;
	private IvParameterSpec _IVParamSpec;

	private Cipher cipher;

	private static Logger log = LoggerFactory.getLogger(RijndaelCrypt.class);

	/**
	 * Constructor
	 */
	public RijndaelCrypt(byte[] key, byte[] iv) {

		try {
			_password = new SecretKeySpec(key, ALGORITHM);

			// Initialize objects
			cipher = Cipher.getInstance(TRANSFORMATION);

			_IVParamSpec = new IvParameterSpec(iv);

		} catch (NoSuchAlgorithmException e) {
			log.error(TAG, "No such algorithm " + ALGORITHM, e);
		} catch (NoSuchPaddingException e) {
			log.error(TAG, "No such padding PKCS7", e);
		}

	}

	public String decrypt(byte[] cipherText) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
			byte[] decryptedVal = cipher.doFinal(cipherText);
			return new String(decryptedVal);

		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Encryptor.
	 * 
	 * @text String to be encrypted
	 * @return Base64 encrypted text
	 */
	public String encrypt(byte[] text) {

		try {
			cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
			byte[] encryptedData = cipher.doFinal(text);
			return Base64.encodeBase64String(encryptedData);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String encrypt(String text) {
		byte[] data = StringUtils.getBytesUtf8(text);
		return encrypt(data);
	}

}
