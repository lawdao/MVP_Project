package fussen.yu.news;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
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
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「User」（既类名）

        Entity user = schema.addEntity("User");
        // 你也可以重新给表命名
        // entity.setTableName("NODE");

        // 定义一个主键
        user.addIdProperty().primaryKey().unique();

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.

        // 定义一个非空的列，列名为 USERID 用户ID
//        user.addStringProperty("userId").notNull().unique();//指定的列中没有重复值，或该表中每一个值或者每一组值都将是唯一的
        user.addStringProperty("nickName").notNull();
        user.addStringProperty("avatarUrl").notNull();
        user.addStringProperty("mobile").notNull();
        user.addStringProperty("gender").notNull();

    }
}
