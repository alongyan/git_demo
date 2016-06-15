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
    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ָ����֤��
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
        System.out.println("ָ������ ==> " + initFingerprint(strKey, m));
    }

    /**
     * ����ָ����֤��
     *
     * @param secretKey ��Կ����32
     * @param paramMap  ����Map
     * @return ��Ӧָ���ַ���
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
     * md5 ����
     */
    private static String encryptMD5(String str) throws Exception {
        System.out.println("ָ������ ==> " + str);
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


    // ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ������������/����

    public void testEncryptBase64() throws Exception {
        byte[] keyBytes = "zxg0vEy71QlLjnwbon3thKVh".getBytes();
        for (int i = 1; i < 11; i++) {
            System.out.print(i + "  ==>  ");
            System.out.print(encryptBASE64(String.valueOf(i), keyBytes));//����
            System.out.print("  ==>  " + decryptFromBASE64(encryptBASE64(String.valueOf(i), keyBytes), keyBytes));//����
            System.out.println();
        }
    }

    /**
     * ����
     * �� szSrc ���� BASE64 ����
     *
     * @param keyBytes Ϊ������Կ ������Ϊ24�ֽ�
     * @param szSrc    �������ַ���
     * @return �������
     */
    public static String encryptBASE64(String szSrc, byte[] keyBytes) throws Exception {
        if (StringUtils.isEmpty(szSrc)) {
            return null;
        }
        byte[] encoded = encryptMode(szSrc.getBytes("utf-8"), keyBytes);//Cipher����
        return (new sun.misc.BASE64Encoder()).encode(encoded);
    }

    /**
     * ����
     *
     * @param keyBytes ������Կ������Ϊ24�ֽ�
     * @param src      Ϊ�����ܵ����ݻ�����(Դ)
     * @return ���ܺ󻺳���
     */
    private static byte[] encryptMode(byte[] src, byte[] keyBytes) {
        try {
            //������Կ
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            //����
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
     * ����
     * �� BASE64 ������ַ��� str ���н���
     *
     * @param keyBytes Ϊ������Կ ������Ϊ24�ֽ�
     * @param str      BASE64 ������ַ���
     * @return �������
     */
    public String decryptFromBASE64(String str, byte[] keyBytes) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(str);
        byte[] srcBytes = decryptMode(b, keyBytes);//Cipher����
        return new String(srcBytes, Charset.forName("UTF-8"));
    }

    /**
     * ����
     *
     * @param keyBytes ������Կ������Ϊ24�ֽ�
     * @param src      ���ܺ�Ļ�����
     * @return ���ܺ󻺳���
     */
    private static byte[] decryptMode(byte[] src, byte[] keyBytes) {
        try {
            //������Կ
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            //����
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
