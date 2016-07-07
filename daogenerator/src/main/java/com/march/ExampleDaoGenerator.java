package com.march;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
//        operate();
    }

    private static void operate() throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
//        Schema schema = new Schema(1, "com.march");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
        Schema schema = new Schema(2, "com.march.bean");
        schema.setDefaultJavaPackageDao("com.march.dao");
        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
         schema.enableActiveEntitiesByDefault();
         schema.enableKeepSectionsByDefault();
        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        generateBean(schema);
        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, "/Users/march/AndroidPro/Reaper/app/src/main/java-gen");
    }

    private static void generateBean(Schema schema) {

        addAlbumDetail(schema);
        addRecommendAlbumItem(schema);
        addWholeAlbumItem(schema);
    }

    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity note = schema.addEntity("Note");
        // 你也可以重新给表命名
        // note.setTableName("NODE");
        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addTest(Schema schema) {
        Entity note = schema.addEntity("Test");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addAlbumDetail(Schema schema) {
        Entity albumDetail = schema.addEntity("AlbumDetail");
        albumDetail.addStringProperty("album_link");
        albumDetail.addStringProperty("photo_src");
        albumDetail.addIntProperty("width");
        albumDetail.addIntProperty("height");
        albumDetail.addStringProperty("time_stamp");
        albumDetail.addBooleanProperty("isFavorite");
    }

    private static void addRecommendAlbumItem(Schema schema) {
        Entity recommendAlbumItem = schema.addEntity("RecommendAlbumItem");
        recommendAlbumItem.addStringProperty("album_type");
        recommendAlbumItem.addStringProperty("album_link");
        recommendAlbumItem.addStringProperty("album_cover");
        recommendAlbumItem.addStringProperty("album_desc");
        recommendAlbumItem.addStringProperty("time_stamp");
        recommendAlbumItem.addBooleanProperty("isFavorite");
    }

    private static void addWholeAlbumItem(Schema schema) {
        Entity wholeAlbumItem = schema.addEntity("WholeAlbumItem");
        wholeAlbumItem.addStringProperty("album_link");
        wholeAlbumItem.addStringProperty("key_words");
        wholeAlbumItem.addStringProperty("album_cover");
        wholeAlbumItem.addStringProperty("album_desc");
        wholeAlbumItem.addStringProperty("time_stamp");
        wholeAlbumItem.addBooleanProperty("isFavorite");
    }


}
