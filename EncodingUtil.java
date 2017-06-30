package com.wonders.alc.common;

import java.io.UnsupportedEncodingException;

public class EncodingUtil {
	/**
	 * ��str�ַ�����charsetBefore���뷽ʽװ����charsetEnd���뷽ʽ
	 * @param str:�ַ���
	 * @param charsetBefore:���ڵı��뷽ʽ
	 * @param charsetEnd:ת��֮��ı��뷽ʽ
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String charConvert(String str,String charsetBefore,String charsetEnd) throws UnsupportedEncodingException {
		return new String(str.getBytes(charsetBefore),charsetEnd);
	}
	
	/**
	 * ��ȡ�ַ����ı��뷽ʽ
	 * @param str
	 * @return
	 */
	public static String getCharSet(String str) {
	      String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //�ж��ǲ���GB2312
	               String s = encode;      
	              return s;      //�ǵĻ������ء�GB2312�������´���ͬ��
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //�ж��ǲ���ISO-8859-1
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {   //�ж��ǲ���UTF-8
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //�ж��ǲ���GBK
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "�ǳ������";        //��������ǣ�˵����������ݲ����ڳ����ı����ʽ��
	   }
}
