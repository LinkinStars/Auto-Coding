package com.linkinstars.autocoding.controller;

import com.linkinstars.autocoding.model.CommonResponse;
import com.linkinstars.autocoding.util.ResolveSqlUtil;
import com.linkinstars.autocoding.util.StaticPath;
import com.linkinstars.autocoding.util.ZipUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * @author LinkinStar
 */
@Controller
public class Sql2PojoController {
    
    @RequestMapping("/auto-code")
    public String index() {
        return "index";
    }

    @RequestMapping("/sql2pojo")
    @ResponseBody
    public CommonResponse sql2pojo(String sql, String packageName, String ignoreName, String pojoSuffix, 
                                   int isUnderscore, String author, int languageType) throws Exception {
        sql = sql.trim();
        if (StringUtils.isEmpty(sql)) {
            return CommonResponse.fail("sql为空");
        }

        String[] sqlArray = sql.split("\n");
        if (sqlArray.length < 1) {
            return CommonResponse.fail("上传sql为不包含createTable");
        }

        String[] createTableArray = sqlArray[0].split(" ");
        if (createTableArray.length < 3) {
            return CommonResponse.fail("createTable语句有误");
        }

        String downloadUrl = ResolveSqlUtil.resolve(sql, packageName, 
                ignoreName, pojoSuffix, isUnderscore, author, languageType);
        if (StringUtils.isEmpty(downloadUrl)) {
            return new CommonResponse(2, "失败", "");
        }
        return new CommonResponse(1, "成功", StaticPath.downloadPath + downloadUrl);
    }

    @RequestMapping("/sqlFile2pojo")
    @ResponseBody
    public CommonResponse sqlFile2pojo(MultipartFile sqlFile, String packageName, String ignoreName, String pojoSuffix,
                                    int isUnderscore, String author, int languageType) throws Exception {
        if (sqlFile == null || sqlFile.isEmpty()) {
            return CommonResponse.fail("sql为空");
        }

        //将sql文件读取出来一行行处理
        List<String> readLines = IOUtils.readLines(sqlFile.getInputStream());
        
        List<String> javaClassNameList = new ArrayList<>();
        
        //从CREATE TABLE找到第一条
        for (int i = 0; i < readLines.size(); i++) {
            StringBuilder sql = new StringBuilder();
            String readLine = readLines.get(i); 
            if (!readLine.contains("CREATE TABLE")){
                continue;
            }
            
            //找到第一条建表语句
            sql.append(readLine).append("\n");
            for (int j = i + 1; j < readLines.size(); j++){
                String readLineTemp = readLines.get(j);
                sql.append(readLineTemp).append("\n");
                //最后一行ENGINE
                if (readLineTemp.contains("ENGINE")) {
                    break;
                }
            }
            String downloadUrl = ResolveSqlUtil.resolve(sql.toString(), packageName, 
                    ignoreName, pojoSuffix, isUnderscore, author, languageType);
            if (!StringUtils.isEmpty(downloadUrl)) {
                javaClassNameList.add(downloadUrl);
            }
        }

        String downloadUrl = ZipUtil.compress(javaClassNameList);
        if (StringUtils.isEmpty(downloadUrl)) {
            return new CommonResponse(2, "失败", "");
        }
        return new CommonResponse(1, "成功", downloadUrl);
    }
}
