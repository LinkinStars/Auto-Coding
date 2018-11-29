package com.linkinstars.autocoding.model;

import java.util.List;

/**
 * pojo生成类包含信息
 * @author LinkinStar
 */
public class POJOmaker {
    private String packageName;
    private String className;
    private List<POJOfield> fieldList;
    private String classComment;
    private String author;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<POJOfield> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<POJOfield> fieldList) {
        this.fieldList = fieldList;
    }

    public String getClassComment() {
        return classComment;
    }

    public void setClassComment(String classComment) {
        this.classComment = classComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "POJOmaker{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", fieldList=" + fieldList +
                ", classComment='" + classComment + '\'' +
                '}';
    }
}
