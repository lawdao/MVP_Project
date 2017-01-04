package fussen.yu.news.utils.config;

import android.os.Environment;

import fussen.yu.news.App;


public class FileConfig {


    //总路径名称
    public static final String COACH_PATH_NAME = "/BodyplusCoach";
    //总路径
    public static final String COACH_PATH = Environment.getExternalStorageDirectory().toString() + COACH_PATH_NAME;
    //日志总路径
    public static final String BODYPLUS_LOG_PATH = Environment.getExternalStorageDirectory().toString() + "/bodyplus_coach_log/.log/";
    //升级路径
    public static final String UPDATE_PATH = COACH_PATH + "/update";
    //缓存路径
    //public static final String CACHE_DATA_PATH = COACH_PATH + "/data/cache";
    public static final String CACHE_DATA_PATH = App.getContext().getCacheDir().getAbsolutePath();
    //拍照储存路径
    public static final String CAMERA_IMG_PATH = COACH_PATH + "/data/cameraImg";
    //缓存图片路径
    public static final String CACHE_IMAGE_PATH = COACH_PATH + "/data/cacheImg";

    public static final String USER_HEADE_PATH = COACH_PATH + "/data/userHeader";
    //图片剪切后的储存路径
    public static final String CROP_IMG_PATH = COACH_PATH + "/data/cropImg";
    //压缩后的路径
    public static final String COMPRESS_IMA_PATH = COACH_PATH + "/data/compressImg";

    //分享图片的路径
    public static final String SHARE_IMAGE_PATH = COACH_PATH + "/data/share/";
    //分享图片的名称
    public static final String SHARE_IMAGE = "share.png";
    //日志路径
    public static final String LOG_PATH = COACH_PATH + "/log/";
    //日志文件名称
    public static final String LOG_NAME = "crash_log.log";
    //剪切后的图片名称
    public static final String CROPPED_IMAGE_NAME = "CropImage.jpg";

    //视频存放路径
    public static final String VIDEO_DATA_PATH = COACH_PATH + "/data/video";
    public static final String ANALYSIS_DATA_PATH = COACH_PATH + "/data/trainingAnalysis";


}
