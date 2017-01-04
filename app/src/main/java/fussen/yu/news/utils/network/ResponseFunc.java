package fussen.yu.news.utils.network;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Fussen on 2016/12/21.
 */

public class ResponseFunc<T> implements Func1<ResponseBody, Observable<T>> {

    @SuppressWarnings("unchecked")
    @Override
    public Observable<T> call(final ResponseBody response) {
        if (response == null) {
            throw new JsonParseException("服务器返回数据异常");
        }

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                try {
                    JSONObject jsonObject = new JSONObject(response.string());



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                subscriber.onNext((T)response);

                subscriber.onCompleted();

            }
        });

    }
}
