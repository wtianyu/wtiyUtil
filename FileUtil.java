package com.springmvc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


//文件处理工具类
public class FileUtil {
	/**
	 * 新建目录
	 * @param savePath
	 * @return
	 */
	public static boolean createDir(String savePath) {
		File file = new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return true;
	}
	
	/**
	 * 复制文件[流复制文件]
	 * @param in 源文件输入流
	 * @param fileTarget 生成文件
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFile(InputStream in,File fileTarget) throws IOException {
		//流处理
        FileOutputStream out=new FileOutputStream(fileTarget);
        int c;
        byte buffer[]=new byte[1024];
        while((c=in.read(buffer))!=-1){
            for(int i=0;i<c;i++)
                out.write(buffer[i]);        
        }
        in.close();
        out.close();
		return true;
	}
	
	/**
	 * 复制文件[文件复制文件]
	 * @param fileOrgain 源文件
	 * @param fileTarget 生成文件
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFile(File fileOrgain,File fileTarget) throws IOException {
		//流处理
		FileInputStream in = new FileInputStream(fileOrgain);
        FileOutputStream out=new FileOutputStream(fileTarget);
        int c;
        byte buffer[]=new byte[1024];
        while((c=in.read(buffer))!=-1){
            for(int i=0;i<c;i++)
                out.write(buffer[i]);        
        }
        in.close();
        out.close();
		return true;
	}
	
	/** 
	 * 根据路径删除指定的文件或目录(包含里面所有的文件)，无论存在与否 
	 *@param sPath  要删除的目录或文件名 
	 *@return 删除成功返回 true，否则返回 false
	 */  
	public static boolean DeleteFolder(String sPath) {  
	    File file = new File(sPath);  
	    if (!file.exists()) {  
	        return false;  
	    } else {  
	        if (file.isFile()) { 
	            return deleteFile(sPath);  
	        } else {   
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}  
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件路径
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	private static boolean deleteFile(String sPath) {  
	    File file = new File(sPath);  
	    if (file.isFile() && file.exists()) {  
	        file.delete();
	        return true;
	    }  
	    return false;  
	}  
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	private static boolean deleteDirectory(String sPath) {  
		Boolean flag = true;
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	  //删除文件夹下的所有文件(包括子目录)
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件   
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录 
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}
	
	/**
	 * 获取文件名的后缀名,不存在则返回""
	 * @param fileName
	 * @return
	 */
	public static String getFileNameExt(String fileName){
		//不能使用"."来分割split,必须用"\\."
		if(fileName.contains(".")){
			return fileName.substring(fileName.lastIndexOf("."),fileName.length());
		}
		return "";
	}
	
	/**
	 * 获取文件名不包含后缀名，不存在后�?名则返回""
	 * @param fileName
	 * @return
	 */
	public static String getFileNameNoExt(String fileName){
		//不能使用"."来分割split,必须用"\\."
		if(fileName.contains(".")){
			return fileName.substring(0,fileName.lastIndexOf("."));
		}
		return "";
	}

	/**根据文件流获取文件
	 * @param savePath 
	 * @param fileName 
	 * @param in 
	 * @return 
	 * @throws IOException 
	 * 
	 */
	public static File createFileByInStream(String savePath, String fileName, InputStream in) throws IOException {
		FileUtil.createDir(savePath);
		File file = new File(savePath+"\\"+fileName);
		FileOutputStream out= new FileOutputStream(file);
        int c;
        byte buffer[]=new byte[2048];
        while((c=in.read(buffer))!=-1){
            for(int i=0;i<c;i++){
                out.write(buffer[i]);
            }
        }
        in.close();
        out.close();
        return file;
	}

	/**
	 * 通过response和图片，设置返回图片流
	 * @param response
	 * @param file
	 * @throws IOException 
	 */
	public static void setResponseImg(HttpServletResponse response, File file) throws IOException {
		response.setContentType("image/*");
		FileInputStream in = new FileInputStream(file);
		OutputStream out= response.getOutputStream();
        int c;
        byte buffer[]=new byte[2048];
        while((c=in.read(buffer))!=-1){
            for(int i=0;i<c;i++){
                out.write(buffer[i]);
            }
        }
        in.close();
        out.close();
	}

	/**
	 * 删除文件
	 * @param advertFilepath
	 */
	public static void DeleteFile(String advertFilepath) {
		File file  = new File(advertFilepath);
		file.delete();
	}
}
