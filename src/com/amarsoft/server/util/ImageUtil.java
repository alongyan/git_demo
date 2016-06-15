package com.amarsoft.server.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class ImageUtil {
	private static Logger logger = Logger.getLogger(ImageUtil.class);
	public static void writeFile(String strUrl, String sFlile,String fileName) throws Exception{
		URL url = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		URLConnection urlCon =null;
		long urlFileSize =0;
		long localFileSize =0;
		try {
			url = new URL(strUrl);
			is = url.openStream();
			urlCon = url.openConnection();
			urlCon.connect();
			//��ȡurl��ͼƬ���ĵĴ�С
			urlFileSize =urlCon.getContentLength();
			File f = new File(sFlile);
			if(!f.exists()){
				f.mkdirs();
			}
			os = new FileOutputStream(sFlile+fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			//��ȡ����ͼƬ�Ĵ�С
			localFileSize = new FileInputStream(sFlile+fileName).available();
			//������ͼƬ����ʧ�ܵ���ͼƬ��٣�ѭ������ʮ��
			int count=1;
			while(localFileSize<urlFileSize){
				System.out.println("ͼƬ����ʧ��,��������,����urlFileSize="+urlFileSize+",localFileSize="+localFileSize+",sFlile+fileName="+sFlile+fileName);
				while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				//��ȡ����ͼƬ�Ĵ�С
				localFileSize = new FileInputStream(sFlile+fileName).available();
				count++;
				if(count==10){
					throw new Exception("ͼƬ����ʧ��");
				}
			}
		} catch (Exception e) {
			logger.info("---�ļ����浽���س���---");
			e.printStackTrace();
			throw e;
		} finally{
			if(os != null){
				os.close();
			}
			if(is != null){
				is.close();
			}
		}
	}
}