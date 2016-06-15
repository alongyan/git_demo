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
	 * @describe 该方法用于使用Post请求传递参数内容
	 * 
	 */
	 public static String doSRPost(String url, String param) throws Exception {
		 	PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        int sResponseCode = 0;
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            HttpURLConnection httpcon = (HttpURLConnection) conn;
	            // 设置通用的请求属性
	            httpcon.setRequestMethod("POST");
	            httpcon.setRequestProperty("accept", "*/*");
	            httpcon.setRequestProperty("connection", "Keep-Alive");
	            httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            httpcon.setRequestProperty("Content-Type", "text/xml");  
	            // 发送POST请求必须设置如下两行
	            httpcon.setDoOutput(true);
	            httpcon.setDoInput(true);
	           
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(new OutputStreamWriter(httpcon.getOutputStream(),"utf-8"));
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            
	            // 定义BufferedReader输入流来读取URL的响应
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
	        //使用finally块来关闭输出流、输入流
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
	 * @describe 该方法用于使用Post请求传递参数内容
	 * 
	 */
	 public static String doPost(String url, String param) throws Exception {
		 	PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        int sResponseCode = 0;
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            HttpURLConnection httpcon = (HttpURLConnection) conn;
	            // 设置通用的请求属性
	            httpcon.setRequestMethod("POST");
	            httpcon.setRequestProperty("accept", "*/*");
	            httpcon.setRequestProperty("connection", "Keep-Alive");
	            httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            // 发送POST请求必须设置如下两行
	            httpcon.setDoOutput(true);
	            httpcon.setDoInput(true);
	           
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(new OutputStreamWriter(httpcon.getOutputStream(),"utf-8"));
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            
	            // 定义BufferedReader输入流来读取URL的响应
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
	        //使用finally块来关闭输出流、输入流
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
	 * @describe 该方法用于支持Post请求
	 */
	public static String sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
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
        //使用finally块来关闭输出流、输入流
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
	 * 该方法支持g
	 * @throws Exception 
	 */
	 public static String sendGet(String url, String param) throws Exception {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
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
	        // 使用finally块来关闭输入流
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
	  * @describe 该方法用于获取传递错误内容
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
