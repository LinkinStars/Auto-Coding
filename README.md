Auto-Coding
===========

# Sql2Pojo
**change sql 2 java pojo**

## 前言
当我们设计完成数据库之后，通常需要创建对应的实体类，有的称为Entity，有的称为DO，都是一个意思，而自己一个个去写非常的麻烦，所以麻烦的时候就需要相应的自动工具类解决这样的麻烦。

## 最后结果
http://www.linkinstars.com/auto-code

使用起来非常简单，输入对应需要的参数，点击change即可生成你需要的实体类。
sql可以从navicat等类似工具中获得，这里不做赘述。

## 项目中可以修改的点
resources/ftl/pojo.ftl  
这个为模板的文件，在这里可以修改注释的位置，有的人喜欢行尾注释，有的人喜欢使用/** **/这样的注释都可以根据自己的喜欢进行修改  
<br>

JdbcType2JavaType.java  
这个为jdbc类型对应java类型，许多类型我是从mybatis中提取出来的，但是并非全部，而且有的类型对应并不是我喜欢的，如：数据库的DECIMAL对应的java类型应该是BigDecimal但是我还是喜欢用double去对应，所以可以根据你自己的喜欢进行修改  

 
其他根据自己需要进行修改即可如application.yml配置的端口号等

## 项目中用到的技术
springboot + freemarker

## 更新
1.0.1 新增上传sql文件方式转换，上传navicat导出的建表sql语句即可


