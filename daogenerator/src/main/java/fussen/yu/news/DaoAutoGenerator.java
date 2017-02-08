package fussen.yu.news;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class DaoAutoGenerator {

    public static void main(String[] args) throws Exception {

        //数据库的版本号
        int version = 1;

        //包名
        String defaultJavaPackage = "fussen.yu.news";

        //创建一个用于添加实体（Entity）的模式（Schema）对象。
        Schema schema = new Schema(version, defaultJavaPackage);


        //创建表格
        createTable(schema);

        // /表示根目录， ./表示当前路径， ../表示上一级父目录
        new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");

    }


    private static void createTable(Schema schema) {


        /**
         * =================user======================
         */

        // 一个实体（类）就关联到数据库中的一张表，此处表名为「User」（既类名）

        //存放用户信息表
        Entity user = schema.addEntity("User");

        // 定义一个主键
        user.addLongProperty("userID").primaryKey();

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.

        // 定义一个非空的列，列名为 USERID 用户ID
        // user.addStringProperty("userId").notNull().unique();//指定的列中没有重复值，或该表中每一个值或者每一组值都将是唯一的


        user.addStringProperty("nickName").notNull();
        user.addStringProperty("uid").notNull();//用户在服务端的id
        user.addStringProperty("avatarUrl").notNull();
        user.addStringProperty("mobile").notNull();
        user.addStringProperty("gender").notNull();

        /**
         * =================userSport=========================
         */

        //存放用户户外跑步数据表
        Entity userSport = schema.addEntity("Sport");

        //sportID为sport表的主键
        userSport.addLongProperty("sportID").primaryKey();

        userSport.addStringProperty("date").notNull();//日期 2016-12-12 13：24：23
        userSport.addStringProperty("sportTime").notNull();//用时
        userSport.addStringProperty("distance").notNull();//路程
        userSport.addStringProperty("average").notNull();//平均速度
        userSport.addStringProperty("pace").notNull();//配速
        userSport.addStringProperty("calories").notNull();//卡路里

        // 两个实体类通过 Property 来建立关联，此操作会在 user 表中增加一个外键，此外键是 Sport 表中的主键
        Property userProperty = user.addLongProperty("sportID").getProperty();

        // 第一个参数为目标实体类，第二个参数为此表中的外键属性
        user.addToOne(userSport, userProperty);

        Property sportProperty = userSport.addLongProperty("userID").getProperty();
        userSport.addToOne(user, sportProperty);

    }
}
