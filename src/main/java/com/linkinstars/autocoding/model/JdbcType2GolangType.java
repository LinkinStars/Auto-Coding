package com.linkinstars.autocoding.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型对应golang类型
 * @author LinkinStar
 */
public class JdbcType2GolangType {
    
    public static Map<String, String> map = new HashMap<String, String>(){{
        put("TINYINT", "int");
        put("SMALLINT", "int");
        put("MEDIUMINT", "int");
        put("INT", "int");
        put("INTEGER", "int");
        put("BIGINT", "int64");
        put("FLOAT", "float32");
        put("DOUBLE", "float64");
        put("DECIMAL", "float64");

        put("DATE", "time.Time");
        put("TIME", "time.Time");
        put("YEAR", "time.Time");
        put("DATETIME", "time.Time");
        put("TIMESTAMP", "time.Time");

        put("CHAR", "string");
        put("VARCHAR", "string");
        put("TINYBLOB", "string");
        put("TINYTEXT", "string");
        put("BLOB", "string");
        put("TEXT", "string");
        put("MEDIUMBLOB", "string");
        put("MEDIUMTEXT", "string");
        put("LONGBLOB", "string");
        put("LONGTEXT", "string");

        put("BOOLEAN", "bool");
    }};
}
