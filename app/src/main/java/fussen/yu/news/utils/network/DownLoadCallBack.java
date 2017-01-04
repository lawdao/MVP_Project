package fussen.yu.news.utils.network;

/**
 * Created by Fussen on 2016/12/22.
 */

public abstract class DownLoadCallBack  {
    public void onStart(){}

    public void onCancel(){}

    public void onCompleted(){}


    /**
     * @param e
     */
    abstract public void onError(java.lang.Throwable e);

    public void onProgress(long fileSize){}

    /**
     * @param path
     * @param fileName
     * @param fileSize
     */
    abstract public void onSuccess(String path, String fileName, long fileSize);
}
