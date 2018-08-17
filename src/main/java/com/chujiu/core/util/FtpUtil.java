package com.chujiu.core.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jesse on 2016/7/7/007.
 */
public class FtpUtil {
    /**
     * 公共上传文件
     *
     * @param ftp ftp实例对象
     * @param type 文件类型
     * @param dicPath 目标地址
     * @param files 文件list
     * @return List<Map<String, String>> 返回结果list
     */
    public static List<Map<String, String>> uploadFile(Ftp ftp, String type, String dicPath, List<MultipartFile> files) {
        boolean ftpResult = false;
        List resultList = new ArrayList();
        ftp.login(type);
        ftp.mkdir(dicPath);
        ftp.cd(dicPath);
        InputStream instream = null;
        for (MultipartFile f : files) {
            Map<String, String> map = new HashMap<>();
            if (!f.isEmpty()) {
                ImageInputStream iis = null;
                try {
                    iis = ImageIO.createImageInputStream(((DiskFileItem) ((CommonsMultipartFile) f).getFileItem()).getStoreLocation());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        iis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (iis == null) {
                    continue;
                }

                String s[] = f.getOriginalFilename().split("\\.");
                String en = s[s.length - 1];
                String fileName = RandomStringUtils.randomAlphabetic(10) + "." + en;
                String originalFilename = f.getOriginalFilename();

                try {
                    instream = f.getInputStream();
                    ftpResult = ftp.write(fileName, instream);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (ftpResult) {
                    map.put("isSuccess", "yes");
                    map.put("fileName", fileName);
                    map.put("originalFilename", originalFilename);
                } else {
                    map.put("isSuccess", "no");
                }
                resultList.add(map);
            }
        }
        ftp.disconnect();
        return resultList;
    }

    /**
     * 公共上传文件
     *
     * @param ftp ftp实例对象
     * @param type 文件类型
     * @param dicPath 目标地址
     * @param file 文件
     * @return List<Map<String, String>> 返回结果list
     */
    public static List<Map<String, String>> uploadFile(Ftp ftp, String type, String dicPath, File file) {
        boolean ftpResult = false;
        List resultList = new ArrayList();
        ftp.login(type);
        ftp.mkdir(dicPath);
        ftp.cd(dicPath);
        InputStream instream = null;
        Map<String, String> map = new HashMap<>();
        if (file != null) {

            String s[] = file.getName().split("\\.");
            String en = s[s.length - 1];
            String fileName = RandomStringUtils.randomAlphabetic(10) + "." + en;
            String originalFilename = file.getName();

            try {
                instream = new FileInputStream(file);
                ftpResult = ftp.write(fileName, instream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ftpResult) {
                map.put("isSuccess", "yes");
                map.put("fileName", fileName);
                map.put("originalFilename", originalFilename);
            } else {
                map.put("isSuccess", "no");
            }
            resultList.add(map);
        }

        ftp.disconnect();
        return resultList;
    }

    /**
     * 公共删除文件
     *
     * @param ftp
     * @param username
     * @param dicPath
     * @return
     */
    public static boolean deleteFile(Ftp ftp, String username, String dicPath) {
        boolean ftpResult = false;
        try {
            ftp.login(username);
            ftpResult = ftp.rm(dicPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftp.disconnect();
        }
        return ftpResult;
    }
}
