package example.fussen.baselibrary.network;

import android.content.Context;
import android.util.Log;

import com.tamic.novate.util.Utils;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import example.fussen.baselibrary.network.callback.ResponseCallBack;
import example.fussen.baselibrary.network.converter.JsonConverterFactory;
import example.fussen.baselibrary.network.cookie.ClearableCookieJar;
import example.fussen.baselibrary.network.cookie.PersistentCookieJar;
import example.fussen.baselibrary.network.cookie.cache.SetCookieCache;
import example.fussen.baselibrary.network.cookie.persistence.SharedPrefsCookiePersistor;
import okhttp3.Cache;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.FieldMap;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Fussen on 2016/11/30.
 */

public class NetworkUtils {


    private static final String TAG = "NetworkUtils";

    private Map<String, Observable<ResponseBody>> downMaps = new HashMap<String, Observable<ResponseBody>>() {
    };
    private Observable<ResponseBody> downObservable;

    private static volatile OkHttpClient mOkHttpClient;
    private NetService mNetService;
    private static NetworkUtils mInstance;
    private static byte[] syncByte = new byte[0];

    private Context mContext;

    public static NetworkUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (syncByte) {
                if (mInstance == null) {
                    mInstance = new NetworkUtils(context);
                }
            }
        }
        return mInstance;
    }


    public NetworkUtils(Context context) {
        this.mContext = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetConfig.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mNetService = retrofit.create(NetService.class);


    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (NetworkUtils.class) {
                //设置网络缓存路径 缓存大小为10M
                Cache cache = new Cache(new File(mContext.getExternalCacheDir().toString(), "CoachHttpCache"),
                        1024 * 1024 * 10);

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));

                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .cookieJar(cookieJar)
                            .addInterceptor(new RequestInterceptor())
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 执行post请求  带参数
     * 已过滤掉ResponseBody中的其他信息只留下返回数据中data节点
     * <p>
     * 已用gson解析
     *
     * @param url
     * @param parameters
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription executePost(final String url, final Map<String, String> parameters, final ResponseCallBack<T> callBack) {


        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postRequest(url, parameters)
                .compose(schedulersTransformer)
                .subscribe(new NetSubscriber(finalNeedType, callBack));
    }
 /**
     * 执行post请求 无参数
     * 已过滤掉ResponseBody中的其他信息只留下返回数据中data节点
     * <p>
     * 已用gson解析
     *
     * @param url
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription executePost(final String url,final ResponseCallBack<T> callBack) {


        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postRequest(url)
                .compose(schedulersTransformer)
                .subscribe(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * Retroift execute Post by Form
     *
     * @param url
     * @param fields
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription executeForm(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postForm(url, fields)
                .compose(schedulersTransformer)
                .subscribe(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * http execute Post by body
     * <p>
     * 执行需要请求体的post请求
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeBody(final String url, final Object body, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.executePostBody(url, body)
                .compose(schedulersTransformer)
                .subscribe(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * http execute Post by Json
     * <p>
     * 执行提交json数据的post请求
     *
     * @param url
     * @param jsonStr Json String
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscription executeJson(final String url, final String jsonStr, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);
        return mNetService.postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .subscribe(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * upload Flie
     *
     * @param url
     * @param requestBody requestBody
     * @param subscriber  subscriber
     * @return
     */
    public <T> T uploadFlie(String url, RequestBody requestBody, MultipartBody.Part file, Subscriber<ResponseBody> subscriber) {
        return (T) mNetService.uploadFlie(url, requestBody, file)
                .compose(schedulersTransformer)
                .subscribe(subscriber);
    }


    /**
     * upload Flies
     *
     * @param url
     * @param subscriber subscriber
     * @param <T>        T
     * @return
     */
    public <T> T uploadFlies(String url, Map<String, RequestBody> files, Subscriber<ResponseBody> subscriber) {
        return (T) mNetService.uploadFiles(url, files)
                .compose(schedulersTransformer)
                .subscribe(subscriber);
    }


    /**
     * 下载 不指定文件名
     *
     * @param url
     * @param callBack
     */
    public void download(String url, DownLoadCallBack callBack) {
        download(url, null, callBack);
    }


    /**
     * @param url
     * @param name
     * @param callBack
     */
    public void download(String url, String name, DownLoadCallBack callBack) {
        download(url, null, name, callBack);
    }


    /**
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public void download(String url, String savePath, String name, DownLoadCallBack callBack) {

        if (downMaps.get(url) == null) {
            downObservable = mNetService.downloadFile(url);
            downMaps.put(url, downObservable);
        } else {
            downObservable = downMaps.get(url);
        }
        executeDownload(savePath, name, callBack);
    }


    /**
     * executeDownload
     *
     * @param savePath
     * @param name
     * @param callBack
     */
    private void executeDownload(String savePath, String name, DownLoadCallBack callBack) {
        if (DownLoadManager.isDownLoading) {
            downObservable.unsubscribeOn(Schedulers.io());
            DownLoadManager.isDownLoading = false;
            DownLoadManager.isCancel = true;
            return;
        }
        DownLoadManager.isDownLoading = true;
        downObservable.compose(schedulersTransformerDown)
                .subscribe(new DownSubscriber<ResponseBody>(savePath, name, callBack, mContext));
    }


    /**
     * 指定线程
     */
    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    final Observable.Transformer schedulersTransformerDown = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };

    /**
     * MethodHandler
     */
    private List<Type> MethodHandler(Type[] types) {
        Log.d(TAG, "========types size: =======" + types.length);
        List<Type> needtypes = new ArrayList<>();
        Type needParentType = null;

        for (Type paramType : types) {
            System.out.println("  " + paramType);
            // if Type is T
            if (paramType instanceof ParameterizedType) {
                Type[] parentypes = ((ParameterizedType) paramType).getActualTypeArguments();

                for (Type childtype : parentypes) {
                    Log.d(TAG, "===========childtype:=======" + childtype);
                    needtypes.add(childtype);
                    //needParentType = childtype;
                    if (childtype instanceof ParameterizedType) {
                        Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                        for (Type type : childtypes) {
                            needtypes.add(type);
                            //needChildType = type;
                            Log.d(TAG, "=========type:=======" + childtype);
                        }
                    }
                }
            }
        }
        return needtypes;
    }

}
