package com.amarsoft.server.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;

public class JMTimesEncrypt {
    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== 指纹验证串
    public void testInitFingerprint() throws Exception {
        String strKey = "a2l7Eqi9Q46f2dsXFF3BRB66dZeFRk19";
        HashMap<String, String> m = new HashMap<String, String>() {{
        this.put("sdw", "1");
        this.put("awdaw", "2");
        this.put("as", "3");
        this.put("accc", "4");
        this.put("acc", "5");
        this.put("add", "6");
        this.put("ad", "7");
        this.put("ac", "8");
        this.put("ab", "9");
        }};
        System.out.println("指纹密文 ==> " + initFingerprint(strKey, m));
    }

    /**
     * 生成指纹验证串
     *
     * @param secretKey 秘钥长度32
     * @param paramMap  参数Map
     * @return 相应指纹字符串
     */
    public static String initFingerprint(String secretKey, Map<String, String> paramMap) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<String> paramKeyList = new ArrayList<String>();
        if (null != paramMap) {
            paramKeyList.addAll(paramMap.keySet());
            Collections.sort(paramKeyList);
            for (String k : paramKeyList) {
                if (!k.equals("key")) {
                    sb.append(k).append(paramMap.get(k));
                }
            }
        }
        sb.append(secretKey);
        return encryptMD5(sb.toString().toUpperCase()).toUpperCase();
    }

    /**
     * md5 加密
     */
    private static String encryptMD5(String str) throws Exception {
        System.out.println("指纹明文 ==> " + str);
        String appendTemp;
        byte[] buf = str.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buf);
        byte[] tmp = md5.digest();
        StringBuilder sbResult = new StringBuilder();
        for (byte b : tmp) {
            appendTemp = Integer.toHexString(b & 0xff);
            if (appendTemp.length() == 1) {
                sbResult.append("0");
            }
            sbResult.append(appendTemp);
        }
        return sbResult.toString();
    }


    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== 单个参数加密/解密

    public void testEncryptBase64() throws Exception {
        byte[] keyBytes = "zxg0vEy71QlLjnwbon3thKVh".getBytes();
        for (int i = 1; i < 11; i++) {
            System.out.print(i + "  ==>  ");
            System.out.print(encryptBASE64(String.valueOf(i), keyBytes));//加密
            System.out.print("  ==>  " + decryptFromBASE64(encryptBASE64(String.valueOf(i), keyBytes), keyBytes));//解密
            System.out.println();
        }
    }

    /**
     * 加密
     * 将 szSrc 进行 BASE64 编码
     *
     * @param keyBytes 为加密密钥 ，长度为24字节
     * @param szSrc    被编码字符串
     * @return 编码后结果
     */
    public static String encryptBASE64(String szSrc, byte[] keyBytes) throws Exception {
        if (StringUtils.isEmpty(szSrc)) {
            return null;
        }
        byte[] encoded = encryptMode(szSrc.getBytes("utf-8"), keyBytes);//Cipher加密
        return (new sun.misc.BASE64Encoder()).encode(encoded);
    }

    /**
     * 加密
     *
     * @param keyBytes 加密密钥，长度为24字节
     * @param src      为被加密的数据缓冲区(源)
     * @return 加密后缓冲区
     */
    private static byte[] encryptMode(byte[] src, byte[] keyBytes) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            //加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 将 BASE64 编码的字符串 str 进行解码
     *
     * @param keyBytes 为加密密钥 ，长度为24字节
     * @param str      BASE64 编码的字符串
     * @return 解码后结果
     */
    public String decryptFromBASE64(String str, byte[] keyBytes) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(str);
        byte[] srcBytes = decryptMode(b, keyBytes);//Cipher解码
        return new String(srcBytes, Charset.forName("UTF-8"));
    }

    /**
     * 解密
     *
     * @param keyBytes 加密密钥，长度为24字节
     * @param src      加密后的缓冲区
     * @return 解密后缓冲区
     */
    private static byte[] decryptMode(byte[] src, byte[] keyBytes) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            //解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
