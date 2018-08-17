package com.chujiu.core.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 将数据导成一个excel文件流
 * @author chujiu
 *
 */
public class DeportExcelDataUtil {

	/**
	 * Created on   2016-08-24 16:04:44
	 * Discription: [导出数据excel]
	 * @className:  DeportExcelDataUtil
	 * @author:     Jesse
	 * @update:     2016-08-24 16:04:44
	 */
	public static void responseExcel(List<String[]> headNameList, HttpServletResponse response,List dataList,String excelName,String sheetName) throws IOException {
		Workbook workbook = null;
		try {
			workbook = DeportExcelDataUtil.deportData(sheetName,headNameList, dataList);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(workbook!=null){
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachement;filename="+excelName+".xlsx");
			workbook.write(response.getOutputStream());
		}
	}

	/**
	 * Created on   2016-08-24 16:04:44
	 * Discription: [导出模板excel]
	 * @className:  DeportExcelDataUtil
	 * @author:     Jesse
	 * @update:     2016-08-24 16:04:44
	 */
	public static void responseTempletExcel(List<String[]> headNameList, HttpServletResponse response,String excelName,String sheetName){
		Workbook workbook = null;
		try {
			workbook = DeportExcelDataUtil.deportTempletData(sheetName,headNameList);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(workbook!=null){
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachement;filename="+excelName+".xlsx");
			try {
				workbook.write(response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置Sheet表名字,以及按顺序设置的表头[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
	 * 使用的新版的Excel模式,后缀是xlsx
	 * @param sheetName
	 * @param headNameList
	 * @param dataList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Workbook deportData(String sheetName,List<String[]> headNameList,List dataList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);
		int i=0;
		for(String[] name:headNameList){
			Cell cell = header.createCell(i++);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(name[1]);
		}
		int j=1;
		for(Object bean:dataList){
			Row row = sheet.createRow(j++);
			i=0;
			for(String[] name:headNameList){
				//4.创建单元格样式
				CellStyle cellStyle =workbook.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Cell cell = row.createCell(i++);

				cell.setCellType(Cell.CELL_TYPE_STRING);

				cell.setCellValue(BeanUtils.getProperty(bean, name[0]));

			}
		}
		return workbook;
	}

	/**
	 * 设置Sheet表名字,以及按顺序设置的表头[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
	 * 使用的新版的Excel模式,后缀是xlsx
	 * @param sheetName
	 * @param headNameList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Workbook deportTempletData(String sheetName,List<String[]> headNameList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);
		int i=0;
		for(String[] name:headNameList){
			Cell cell = header.createCell(i++);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(name[1]);
		}
		return workbook;
	}

	/**
	 * 设置Sheet表名字,以及按顺序设置的表头[["propertyName","headerName"],....],按照表头取出Bean中的属性值作为单元格的内容
	 * 使用的新版的Excel模式,后缀是xlsx
	 * @param sheetName
	 * @param dataList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Workbook deportData(String sheetName,List<String> dataList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		int i=0;
		int j=1;
		for(String data:dataList){
			Row row = sheet.createRow(j++);
			//4.创建单元格样式
			CellStyle cellStyle =workbook.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Cell cell = row.createCell(i++);

			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(data);

		}
		return workbook;
	}

}
