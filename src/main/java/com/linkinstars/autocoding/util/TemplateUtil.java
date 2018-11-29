package com.linkinstars.autocoding.util;

import com.linkinstars.autocoding.model.POJOmaker;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板制作工具
 * @author LinkinStar
 */
public class TemplateUtil {
    
    private static Template template;

    /** 绝对路径 **/
    private static String absolutePath = "";

    /** 静态目录 **/
    private static String staticDir = "static/";
    
    /** 下载目录 **/
    private static String downloadPath = "download/";

    
    public static void init() throws IOException {
        //获取跟目录
        File file = null;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!file.exists()) {
            file = new File("");
        }

        absolutePath = file.getAbsolutePath();
        File upload = new File(absolutePath, staticDir + downloadPath);
        if(!upload.exists()) {
            upload.mkdirs();
        }

        ClassPathResource classPathResource = new ClassPathResource("ftl/pojo.ftl");
        
        //存文件
        File ftlFile = new File(absolutePath, staticDir + "ftl/pojo.ftl");
        try {
            FileUtils.copyInputStreamToFile(classPathResource.getInputStream(), ftlFile);
        } catch (IOException e) {
            throw e;
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File(absolutePath, staticDir + "ftl"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        template = cfg.getTemplate("pojo.ftl");
    }
    
    public static String writePOJOmakerToTemplate(POJOmaker pojoMaker) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>(3);
        map.put("className", pojoMaker.getClassName());
        map.put("packageName", pojoMaker.getPackageName());
        map.put("fieldList", pojoMaker.getFieldList());
        map.put("classComment", pojoMaker.getClassComment());
        map.put("author", pojoMaker.getAuthor());
        
        String resultPath = downloadPath + pojoMaker.getClassName() + ".java";

        FileOutputStream fos = new FileOutputStream(new File(absolutePath, staticDir + resultPath));
        Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        template.process(map, out);
        out.flush();
        out.close();
        
        return resultPath;
    }
}
