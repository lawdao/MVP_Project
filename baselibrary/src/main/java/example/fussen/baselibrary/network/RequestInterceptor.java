package example.fussen.baselibrary.network;

import java.io.IOException;

import example.fussen.baselibrary.utils.LogUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Fussen on 2016/12/1.
 * <p>
 * 网络请求的固定参数
 * <p>
 * 添加请求头
 */

public class RequestInterceptor implements Interceptor {

    private static final String TAG = "RequestInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {


        Response response = chain.proceed(chain.request());

        LogUtil.fussenLog().e(TAG + "========request url========" + response.request().url());
        LogUtil.fussenLog().e(TAG + "=======response code=======" + response.code());

        //添加固定请求头

        Request.Builder builder = chain.request()
                .newBuilder();

        builder.addHeader("BODY-X-TYPE", "2")
                .addHeader("BODY-X-VERSION", "1.0")
                .build();
        return chain.proceed(builder.build());
    }
}
