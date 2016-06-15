package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	public static void main(String[] args) {
	   String str = "http://sr-service-dev.oss-cn-hangzhou.aliyuncs.com/attachment/2096eac2-35a4-4ec8-ac7e-d647edf6ebc9?Expires=1443109414&OSSAccessKeyId=mBnJypsuUf6Bh3rD&Signature=Q5%2FjjBHyvTtkQpmgHyuUYuX9GZE%3D";
       System.out.println(str.length());
	   try {
		URL url = new URL(str);
		InputStream in  =  url.openStream();
		URLConnection urlCon = url.openConnection();
		urlCon.connect();
		//获取url上图片报文的大小
		long urlFileSize =urlCon.getContentLength();
		System.out.println("urlFileSize = "+urlFileSize);
		byte[] buffer = new byte[8192];
		OutputStream os = new FileOutputStream("D:\\webimg\\112965\\S001\\1.jpg");
		int bytesRead = 0;
		while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		System.out.println("輸入流 ="+in.read(buffer,0,8192));
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
