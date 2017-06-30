package com.wonders.alc.common;

import java.io.UnsupportedEncodingException;

public class EncodingUtil {
	/**
	 * 将str字符串由charsetBefore编码方式装换成charsetEnd编码方式
	 * @param str:字符串
	 * @param charsetBefore:现在的编码方式
	 * @param charsetEnd:转变之后的编码方式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String charConvert(String str,String charsetBefore,String charsetEnd) throws UnsupportedEncodingException {
		return new String(str.getBytes(charsetBefore),charsetEnd);
	}
	
	/**
	 * 获取字符串的编码方式
	 * @param str
	 * @return
	 */
	public static String getCharSet(String str) {
	      String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
	               String s = encode;      
	              return s;      //是的话，返回“GB2312“，以下代码同理
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "非常规编码";        //如果都不是，说明输入的内容不属于常见的编码格式。
	   }
}
