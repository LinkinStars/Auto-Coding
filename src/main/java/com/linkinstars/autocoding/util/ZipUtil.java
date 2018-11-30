package com.linkinstars.autocoding.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.linkinstars.autocoding.util.StaticPath.*;

/**
 * 压缩文件
 * @author LinkinStar
 */
public class ZipUtil {

    /**
     * 压缩文件工具
     * @param srcFiles 文件名列表，文件存放在download目录下
     * @return 压缩后的zip包的路径
     * @throws IOException
     */
    public static String compress(List<String> srcFiles) throws IOException {
        String resultUrl = downloadPath +  System.currentTimeMillis() + ".zip";
        
        FileOutputStream fos = new FileOutputStream(new File(absolutePath, staticDir + resultUrl));
        
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(absolutePath, staticDir + downloadPath + srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        
        return resultUrl;
    }
}
