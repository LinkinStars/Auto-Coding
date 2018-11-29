package com.linkinstars.autocoding.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型对应java类型
 * @author LinkinStar
 */
public class JdbcType2JavaType {
    
    public static Map<String, String> map = new HashMap<String, String>(){{
        put("TINYINT", "int");
        put("SMALLINT", "int");
        put("MEDIUMINT", "int");
        put("INT", "int");
        put("INTEGER", "int");
        put("BIGINT", "long");
        put("FLOAT", "float");
        put("DOUBLE", "double");
        //put("DECIMAL", "BigDecimal");
        put("DECIMAL", "double");

        put("DATE", "Date");
        put("TIME", "Time");
        put("YEAR", "Year");
        put("DATETIME", "Timestamp");
        put("TIMESTAMP", "Timestamp");

        put("CHAR", "String");
        put("VARCHAR", "String");
        put("TINYBLOB", "String");
        put("TINYTEXT", "String");
        put("BLOB", "String");
        put("TEXT", "String");
        put("MEDIUMBLOB", "String");
        put("MEDIUMTEXT", "String");
        put("LONGBLOB", "String");
        put("LONGTEXT", "String");

        put("BOOLEAN", "boolean");
    }};
}
