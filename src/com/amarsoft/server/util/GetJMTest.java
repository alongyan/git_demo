package com.amarsoft.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class GetJMTest {
//	public static void main(String args[]){
//		String sUserKey = "64EF658609CF11E5A6C01697F925EC7B";
//		String smerchantId = "1006";
//		Date date = new Date();
//		long timeStamp = date.getTime();
//		String token = MD5.getMD5Code(smerchantId+"&"+timeStamp+"&"+sUserKey).toUpperCase();
//		String sGet = "merchantId=1006&timeStamp="+timeStamp+"&token="+token;
//		sendGet("https://co-test.jimubox.com/global/queryProjectId",sGet);
//		
//		
//	}
	/**
	 * @describe �÷�������ʹ��Post���󴫵ݲ�������
	 * 
	 */
	 public static String doSRPost(String url, String param) throws Exception {
		 	PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        int sResponseCode = 0;
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            HttpURLConnection httpcon = (HttpURLConnection) conn;
	            // ����ͨ�õ���������
	            httpcon.setRequestMethod("POST");
	            httpcon.setRequestProperty("accept", "*/*");
	            httpcon.setRequestProperty("connection", "Keep-Alive");
	            httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            httpcon.setRequestProperty("Content-Type", "text/xml");  
	            // ����POST�������������������
	            httpcon.setDoOutput(true);
	            httpcon.setDoInput(true);
	           
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(new OutputStreamWriter(httpcon.getOutputStream(),"utf-8"));
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            
	            // ����BufferedReader����������ȡURL����Ӧ
	            sResponseCode=httpcon.getResponseCode();
	            if(sResponseCode == 200){
	            	 in = new BufferedReader(
	 	                    new InputStreamReader(httpcon.getInputStream(),"utf-8"));
	 	            String line;
	 	            while ((line = in.readLine()) != null) {
	 	                result += line;
	 	            }
	            }else{
	            	result = getErrorMessage(httpcon.getErrorStream());
	            }
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	                throw ex;
	            }
	        }
	        return result;
	    }
	/**
	 * @describe �÷�������ʹ��Post���󴫵ݲ�������
	 * 
	 */
	 public static String doPost(String url, String param) throws Exception {
		 	PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        int sResponseCode = 0;
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            HttpURLConnection httpcon = (HttpURLConnection) conn;
	            // ����ͨ�õ���������
	            httpcon.setRequestMethod("POST");
	            httpcon.setRequestProperty("accept", "*/*");
	            httpcon.setRequestProperty("connection", "Keep-Alive");
	            httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            // ����POST�������������������
	            httpcon.setDoOutput(true);
	            httpcon.setDoInput(true);
	           
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(new OutputStreamWriter(httpcon.getOutputStream(),"utf-8"));
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            
	            // ����BufferedReader����������ȡURL����Ӧ
	            sResponseCode=httpcon.getResponseCode();
	            if(sResponseCode == 200){
	            	 in = new BufferedReader(
	 	                    new InputStreamReader(httpcon.getInputStream(),"utf-8"));
	 	            String line;
	 	            while ((line = in.readLine()) != null) {
	 	                result += line;
	 	            }
	            }else{
	            	result = getErrorMessage(httpcon.getErrorStream());
	            }
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	                throw ex;
	            }
	        }
	        return result;
	    }

	/**
	 * @throws Exception 
	 * @describe �÷�������֧��Post����
	 */
	public static String sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
                throw ex;
            }
        }
        return result;
    } 
	/**
	 * �÷���֧��g
	 * @throws Exception 
	 */
	 public static String sendGet(String url, String param) throws Exception {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // �򿪺�URL֮�������
	            URLConnection connection = realUrl.openConnection();
	            // ����ͨ�õ���������
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // ����ʵ�ʵ�����
	            connection.connect();
	            // ��ȡ������Ӧͷ�ֶ�
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // �������е���Ӧͷ�ֶ�
	            for (String key : map.keySet()) {
	            }
	            // ���� BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
	        // ʹ��finally�����ر�������
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	                throw e2;
	            }
	        }
	        return result;
	    }
	 /**
	  * @describe �÷������ڻ�ȡ���ݴ�������
	  * @param inputStream
	  * @return
	  */
	 public static String getErrorMessage(InputStream inputStream){
		 String sReturnMessage = "";
		 Scanner scanner = new Scanner(inputStream, "UTF-8");
		 sReturnMessage = scanner.useDelimiter("\\A").next();
		 return sReturnMessage;
	 }
}
