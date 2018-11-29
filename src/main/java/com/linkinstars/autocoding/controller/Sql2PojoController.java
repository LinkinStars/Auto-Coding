package com.linkinstars.autocoding.controller;

import com.google.common.base.CaseFormat;
import com.linkinstars.autocoding.model.CommonResponse;
import com.linkinstars.autocoding.model.JdbcType2JavaType;
import com.linkinstars.autocoding.model.POJOfield;
import com.linkinstars.autocoding.model.POJOmaker;
import com.linkinstars.autocoding.util.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
                                   int isUnderscore, String author) throws Exception {
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

        String className = handleTableName(createTableArray[2], ignoreName, pojoSuffix);

        POJOmaker pojoMaker = new POJOmaker();
        //处理包名类名
        pojoMaker.setPackageName(packageName);
        pojoMaker.setClassName(className);

        //处理所有字段
        List<POJOfield> pojoFieldList = new ArrayList<>();
        for (int i = 1; i < sqlArray.length - 1; i++) {
            POJOfield pojoField = handleField(sqlArray[i], isUnderscore);
            if (pojoField != null) {
                pojoFieldList.add(pojoField);
            }
        }
        pojoMaker.setFieldList(pojoFieldList);
        
        //处理类名的注释
        String tableComment = handleTableComment(sqlArray[sqlArray.length - 1]);
        pojoMaker.setClassComment(tableComment);
        pojoMaker.setAuthor(author);

        //生成模板并返回下载地址
        String downloadUrl = TemplateUtil.writePOJOmakerToTemplate(pojoMaker);
        if (StringUtils.isEmpty(downloadUrl)) {
            return new CommonResponse(2, "失败", "");
        }
        return new CommonResponse(1, "成功", downloadUrl);
    }

    /**
     * 处理表名为pojo的名字
     * @param tableName 表名 
     * @param ignoreName 表名的前缀或者后缀如：_tab
     * @param pojoSuffix 实体类的后缀如：DO
     * @return 实体类的名称
     */
    private static String handleTableName(String tableName, String ignoreName, String pojoSuffix) {
        //删除前后两个 `
        tableName = tableName.replace("`", "");
        //删除表名的前缀或者后缀
        tableName = tableName.replaceAll(ignoreName,"");
        //转大驼峰
        tableName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
        //加上pojo的后缀
        return tableName + pojoSuffix; 
    }

    /**
     * 处理pojo的所有字段
     * @param rawField 字段
     * @return Ppjo的实体类
     */
    private static POJOfield handleField(String rawField, int isUnderscore){
        rawField = rawField.trim();
        String[] fieldArr = rawField.split(" ");
        
        if (!fieldArr[0].contains("`")) {
            return null;
        }

        String field = fieldArr[0].replace("`","");
        
        //如果是以下划线方式命名则需要转成小驼峰
        if (isUnderscore == 2) {
            field = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
        }
        
        POJOfield pojoField = new POJOfield();
        pojoField.setName(field);
        
        //将数据库类型转换为java类型
        String type = JdbcType2JavaType.map.get(fieldArr[1].toUpperCase().replaceAll("[^a-z^A-Z]", ""));
        if (type == null) {
            type = "Object";
        }
        pojoField.setType(type);
        
        for (int i = 2; i < fieldArr.length; i++) {
            if (fieldArr[i].equalsIgnoreCase("COMMENT")) {
                String comment = fieldArr[i+1];
                comment = comment.replace("'", "");
                comment = comment.replace(",", "");
                pojoField.setComment(comment);
                return pojoField;
            }
        }
        return pojoField;
    }

    /**
     * 处理表名称的注释
     */
    public static String handleTableComment(String rawTableComment) {
        rawTableComment = rawTableComment.trim();
        String[] tableCommentArr = rawTableComment.split(" ");
        if (tableCommentArr.length == 0) {
            return "";
        }
        
        String tableComment = tableCommentArr[tableCommentArr.length - 1];
        if (!tableComment.contains("COMMENT")) {
            return "";
        }
        
        tableComment = tableComment.replace("COMMENT=", "");
        tableComment = tableComment.replace("'", "");
        tableComment = tableComment.replace(";", "");
        return tableComment;
    }
}
