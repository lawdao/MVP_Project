package example.fussen.baselibrary.config;

import android.os.Environment;

/**
 * Created by Fussen on 2017/1/3.
 */

public class FileConfig {

    //总路径名称
    public static final String FILE_PATH_NAME = "/FussenMVP";

    public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + FILE_PATH_NAME;

    //选择照片存储路径
    public static final String SELETE_IMG_PATH = FILE_PATH + "/selectImage";
}
