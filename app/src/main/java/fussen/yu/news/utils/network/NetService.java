package fussen.yu.news.utils.network;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Fussen on 2016/12/1.
 */

public interface NetService {

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> postRequest(
            @Url() String url,
            @FieldMap Map<String, String> params);

    @POST()
    Observable<ResponseBody> postRequest(
            @Url() String url
    );


    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> params);


    @POST("{url}")
    Observable<ResponseBody> executePostBody(
            @Path("url") String url,
            @Body Object object);


    @POST()
    Observable<ResponseBody> postJson(
            @Url() String url,
            @Body RequestBody jsonBody);


    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlie(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("files") MultipartBody.Part file);


    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(
            @Url() String url,
            @PartMap() Map<String, RequestBody> maps);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
