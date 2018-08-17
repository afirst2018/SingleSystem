package com.chujiu.core.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 创建CSV文件
 */
public class CSVUtils {

    /**
     * 创建CSV/TXT文件并通过ftp上传到指定服务器位置
     *
     * @param ftp ftp实例对象
     * @param type 文件类型
     * @param temporaryPath 文件暂存地址
     * @param dicPath 目标地址
     * @param fileName 生成的文件名称
     * @param head 表头
     * @param dataList 数据list
     */
    public static void createCSV(Ftp ftp, String type, String temporaryPath, String dicPath, String fileName, String[] head, List<List<Object>> dataList) {

        List<Object> headList = Arrays.asList(head);
        String filePath = temporaryPath; //文件暂存路径

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filePath + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            //文件下载，使用如下代码
//            response.setContentType("application/csv;charset=gb18030");
//            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            ServletOutputStream out = response.getOutputStream();
//            csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);

            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
            csvWtriter.write(buffer.toString() + fileName + buffer.toString());
            csvWtriter.newLine();

            // 写入文件头部
            writeRow(headList, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();

            FtpUtil.uploadFile(ftp, type, dicPath, csvFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
                //删除临时文件
                csvFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建CSV/TXT文件并通过ftp上传到指定服务器位置
     *
     * @param dicPath 目标地址
     * @param fileName 生成的文件名称
     * @param head 表头
     * @param dataList 数据list
     */
    public static void createCSV(String dicPath, String fileName, String[] head, List<List<Object>> dataList) {

        List<Object> headList = Arrays.asList(head);
        String filePath = dicPath; //文件路径

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filePath + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            //文件下载，使用如下代码
//            response.setContentType("application/csv;charset=gb18030");
//            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            ServletOutputStream out = response.getOutputStream();
//            csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);

            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
            csvWtriter.write(buffer.toString() + fileName + buffer.toString());
            csvWtriter.newLine();

            // 写入文件头部
            writeRow(headList, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写一行数据
     * @param row 数据列表
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static void main(String[] args) {
        List<List<Object>> dataList = new ArrayList<>();
        List<Object> rowList = null;
        for (int i = 0; i < 100; i++) {
            rowList = new ArrayList<Object>();
            rowList.add("张三" + i);
            rowList.add("123456789" + i);
            rowList.add(new Date());
            dataList.add(rowList);
        }
        createCSV("d:/", "abc.csv", new String[]{"姓名", "电话", "时间"}, dataList);
    }
}