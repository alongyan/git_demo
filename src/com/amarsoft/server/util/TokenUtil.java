package com.amarsoft.server.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by hansongtao on 4/9/14.
 */
public class TokenUtil {
    public final static String JIMU_UID = "SPID=12001";
    public final static String SPLIT = "&";
    private final static String JIMU_USERKEY = "adafcdf1242d4a81b0f8f580b95fcecd";

    /**
     * "SPID=12001&StartTime=2014-01-01&EndTime=2014-02-05", UserKey=123456
     * 签名字段顺序如下：EndTime SPID StartTime UserKey
     * 签名字符串如下：2014-02-05100012014-01-01123456
     * 签名结果�?13F7C3FEEF86531287474F96F7392AB
     *
     * @param parameters
     * @return
     */
    public static String geneTokenByParameters(String parameters)  throws Exception{
        String token = "";
        if (StrUtil.isNull(parameters)) {
            return token;
        }
        String[] ps = parameters.split(SPLIT);
        List<String> pList = Arrays.asList(ps);
        java.util.Map m = new ConcurrentSkipListMap();
        for (String s : pList) {
            String[] kv = s.split("=");
            if (kv == null && kv.length != 2) {
                throw new Exception("Your parameters is error:" + parameters);
            }
            m.put(kv[0].toUpperCase(), kv[1]);
        }
        Iterator i = m.values().iterator();
        while (i.hasNext()) {
            token += i.next();
        }
        token +=JIMU_USERKEY;
        EncryptUtil eu = new EncryptUtil();
        try {
            token = eu.md5Digest(token);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("there have some error when generate token " + e);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("there have some error when generate token " + e);
        }
        return token;
    }

    public static String geneJIMUTokenByDefaultPara(String parameters) throws Exception {
        return geneTokenByParameters(parameters + SPLIT + JIMU_UID );
    }
    public static String geneJIMUTokenByDefaultParamter(String parameters) throws Exception {
        return geneTokenByParameters(parameters);
    }

}

