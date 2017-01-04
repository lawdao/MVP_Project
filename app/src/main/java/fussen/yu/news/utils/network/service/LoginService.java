package fussen.yu.news.utils.network.service;

import java.util.Map;

import fussen.yu.news.modules.login.bean.UserInfo;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Fussen on 2016/12/29.
 */

public interface LoginService {
    @POST()
    @FormUrlEncoded
    Observable<UserInfo> toLogin(
            @Url() String url,
            @FieldMap Map<String, String> params);
}
