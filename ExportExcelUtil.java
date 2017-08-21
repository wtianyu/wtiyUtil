package com.wondersgroup.sabic.aps.publicAppeal.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出excel工具类
 * @author wy
 */
public class ExportExcelUtil {

	/**
	 * 导出基本的excel表,单表
	 * @param fileName excel的文件名
	 * @param sheetName excel的表名
	 * @param headList excel表内容标题;null代表没有
	 * @param dataList excel表的内容;不能为null
	 * @param widthList excel表的列距;null代表默认
	 * @param format 显示格式
	 * @param response
	 * @throws IOException 
	 * @throws WriteException 
	 */
	public static void exportBaseExcelOne(String fileName, String sheetName, List<String> headList,
			List<List<String>> dataList,List<Integer> widthList,WritableCellFormat format,HttpServletResponse response) throws Exception  {
		WritableWorkbook book = null;
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		boolean candoflag = true;//执行标志
		int headRow = 0;//行标号
		try {
			if (candoflag) {
				book = Workbook.createWorkbook(os);
				WritableSheet sheet = book.createSheet(sheetName, 0);//一张表
				if(widthList!=null){//行宽设置
					for (int i = 0; i < widthList.size(); i++) {
						sheet.setColumnView(i, widthList.get(i));
					}
				}
				if(headList!=null){//标题设置
					headRow = 1;
					for (int i = 0; i < headList.size(); i++) {
						sheet.addCell(new Label(i, 0, headList.get(i),format));//第一行显示
					}
				}
				if (dataList != null) {//数据设置
					for (int i = 0; i < dataList.size(); i++) {
						for (int j = 0; j < dataList.get(i).size(); j++) {
							sheet.addCell(new Label(j, i+headRow, dataList.get(i).get(j),format));
						}
					}
				}
				book.write();
			}
		} finally {
			if (book != null) {
				book.close();
				os.close();
			}
		}
	}
	
	/**
	 * 导出基本的excel表,多表
	 * @param fileName excel的文件名
	 * @param sheetName excel的表名
	 * @param sheetIndex 操作表下标(0为第一张)
	 * @param headList excel表内容标题;null代表没有
	 * @param dataList excel表的内容;不能为null
	 * @param widthList excel表的列距;null代表默认
	 * @param format 显示格式
	 * @param response
	 * @throws IOException 
	 * @throws WriteException 
	 */
	public static void exportBaseExcelMany(String fileName, String[] sheetNames,int[] sheetIndexs, List<List<String>> headLists,
			List<List<List<String>>> dataLists,List<List<Integer>> widthLists,List<WritableCellFormat> formats,HttpServletResponse response) throws Exception  {
		WritableWorkbook book = null;
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		boolean candoflag = true;
		int[] headRow = new int[sheetIndexs.length];
		try {
			if (candoflag) {
				book = Workbook.createWorkbook(os);
				WritableSheet[] sheets = new WritableSheet[sheetNames.length];
				for (int i = 0; i < sheetNames.length; i++) {
					sheets[i] = book.createSheet(sheetNames[i], 0);//一张表
				}
				
				if(widthLists!=null){
					for (int i = 0; i < widthLists.size(); i++) {
						if(widthLists.get(i)!=null){
							for (int j = 0; j < widthLists.get(i).size(); j++) {
								sheets[i].setColumnView(j, widthLists.get(i).get(j));
							}
						}
					}
				}
				if(headLists!=null){
					for (int i = 0; i < headLists.size(); i++) {
						headRow[i] = 1;
						if(headLists.get(i)!=null){
							for (int j = 0; j < headLists.get(i).size(); j++) {
								sheets[i].addCell(new Label(j, 0, headLists.get(i).get(j),formats.get(i)));//第一行显示
							}
						}
					}
				}
				if (dataLists != null) {
					for (int i = 0; i < dataLists.size(); i++) {
						for (int j = 0; j < dataLists.get(i).size(); j++) {
							for (int j2 = 0; j2 < dataLists.get(i).get(j).size(); j2++) {
								sheets[i].addCell(new Label(j2, j+headRow[i], dataLists.get(i).get(j).get(j2),formats.get(i)));
							}
						}
					}
				}
				book.write();
			}
		} finally {
			if (book != null) {
				book.close();
				os.close();
			}
		}
	}

	/**
	 * 获取默认样式(文字居中)
	 * @return
	 * @throws Exception
	 */
	public static  WritableCellFormat getWritableFontStyle() throws Exception {
		WritableFont resultFont = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD);
		WritableCellFormat resultFormat = new WritableCellFormat(resultFont);
		resultFormat.setAlignment(Alignment.CENTRE);//水平居中
		//resultFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		//titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 边框线
		resultFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR);
		//resultFormat.setBackground(jxl.format.Colour.PALE_BLUE); // 标题背景
		//resultFormat.setWrap(true); // 自动换行
		//sheet.getSettings().setDefaultColumnWidth(20);// 默认宽度
		return resultFormat;
	}
	
	/**
	 * 默认样式，自定义内容方向,文字大小
	 * @param textAlign 0:靠左,1:靠右,其他为居中
	 * @param textSize
	 * @return
	 * @throws Exception
	 */
	public static  WritableCellFormat getWritableFontStyle(int textAlign,int textSize) throws Exception {
		
		WritableFont resultFont = new WritableFont(WritableFont.TAHOMA, textSize, WritableFont.NO_BOLD);
		WritableCellFormat resultFormat = new WritableCellFormat(resultFont);
		if(textAlign==0){
			resultFormat.setAlignment(Alignment.LEFT);//水平靠左
		}else if(textAlign==1){
			resultFormat.setAlignment(Alignment.RIGHT);//水平靠右
		}else{
			resultFormat.setAlignment(Alignment.CENTRE);//水平居中
		}
		resultFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
		resultFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR);
		return resultFormat;
	}

	/**
	 * 通过book,sheet继续完成excel表的数据输入
	 * @param book
	 * @param sheet
	 * @param fileName 文件名
	 * @param row sheet已经完成了row行的内容
	 * @param headList 内容标题
	 * @param dataList 内容
	 * @param widthList 列宽度
	 * @param format 样式
	 * @param response
	 * @throws IOException 
	 */
	public static void exportExcelByBook(WritableWorkbook book,
			WritableSheet sheet, String fileName,int row, List<String> headList,
			List<ArrayList<String>> dataList, List<Integer> widthList,
			WritableCellFormat format, HttpServletResponse response) throws Exception {
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		int headRow = 0;
		try {
			int width = 15;
			if(widthList!=null){
				for (int i = 0; i < widthList.size(); i++) {
					sheet.setColumnView(i, widthList.get(i));
				}
			}
			if(headList!=null){
				headRow = 1;
				for (int i = 0; i < headList.size(); i++) {
					sheet.addCell(new Label(i, row, headList.get(i),format));
				}
			}
			if (dataList != null) {
				for (int i = 0; i < dataList.size(); i++) {
					for (int j = 0; j < dataList.get(i).size(); j++) {
						sheet.addCell(new Label(j, i+headRow+row, dataList.get(i).get(j),format));
					}
				}
			}
			book.write();
		}finally {
			if (book != null) {
				book.close();
				response.getOutputStream().close();
			}
		}
	}

	/**
	 * 创建下载文件（可在线打开）
	 * 支持在线打开的可以预览，不支持的为下载
	 * @param filePath
	 * @param response
	 * @param isOnLine
	 * @throws Exception
	 *             void
	 *
	 */
	public static void createDownFileOnline(String filePath, HttpServletResponse response, boolean isOnLine)
			throws Exception {

		File f = new File(filePath);
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); 
		// 在线打开方式
		if (isOnLine) {
			URL u = new URL("file:"+filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename=" + toUTF8(f.getName()));
		}
		// 纯下载方式
		else {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + toUTF8(f.getName()));
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		out.flush();
		br.close();
		out.close();
	}
	
	/**
     * UTF-8编码
     * 
     * @param s
     * @return
     */
    private static String toUTF8(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 合并单元格
     * @param sheet 
     * @param i 单元格的列号
     * @param j 单元格的行号
     * @param k 从单元格[i,j]起，向下合并到的列数
     * @param l 从单元格[i,j]起，向下合并到的行数
     * @throws WriteException 
     * @throws RowsExceededException 
     */ 
	public static void mergeCell(WritableSheet sheet, int i, int j, int k, int l) throws RowsExceededException, WriteException {
		sheet.mergeCells(i, j, k, l);
	}
}
