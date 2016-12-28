package fussen.yu.news.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import fussen.yu.news.utils.ToastUtil;
import fussen.yu.news.App;


/**
 * Created by Fussen on 2016/12/19.
 * <p>
 * 网络状态监听
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkState";

    @Override
    public void onReceive(Context context, Intent intent) {


        // 这个监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {


            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e(TAG, "=========wifiState======" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED://WiFi关闭
                    App.getInstance().setEnablaWifi(false);
                    Log.e(TAG, "=====检测到WiFi已关闭======");
                    break;
                case WifiManager.WIFI_STATE_DISABLING:

                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
                case WifiManager.WIFI_STATE_ENABLED://WiFi打开

                    App.getInstance().setEnablaWifi(true);
                    Log.e(TAG, "=====检测到WiFi已打开======");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    break;
                default:
                    break;

            }
        }


        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
        // 当然刚打开wifi肯定还没有连接到有效的无线

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {


            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                Log.e(TAG, "=====isConnected======" + isConnected);
                if (isConnected) {
                    App.getInstance().setWifi(true);
                    Log.e(TAG, "=====WiFi已连接======" + isConnected);
                } else {
                    App.getInstance().setWifi(false);
                    Log.e(TAG, "=====WiFi已断开======" + isConnected);
                }
            }
        }


        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {

            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            Log.i(TAG, "========网络状态检测=======");

            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        App.getInstance().setWifi(true);
                        Log.e(TAG, "======当前处于WiFi网络状态=======");

                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        App.getInstance().setMobile(true);
                        Log.e(TAG, "========当前处于移动网络状态=====已连接到移动网络===== ");
                        ToastUtil.showToast("当前处于移动网络状态");
                    }
                } else {
                    Log.e(TAG, "=======当前没有网络连接，请确保你已经打开网络 =======");
                }


                Log.e(TAG, "=======info.getTypeName()=====" + activeNetwork.getTypeName());
                Log.e(TAG, "=========getSubtypeName()=====" + activeNetwork.getSubtypeName());
                Log.e(TAG, "=========getState()=======" + activeNetwork.getState());
                Log.e(TAG, "========getDetailedState()======"
                        + activeNetwork.getDetailedState().name());
                Log.e(TAG, "=======getDetailedState()=======" + activeNetwork.getExtraInfo());
                Log.e(TAG, "=======getType()======" + activeNetwork.getType());

            } else {   // not connected to the internet

                Log.e(TAG, "======当前没有网络连接，请确保你已经打开网络======= ");
                ToastUtil.showToast("当前没有可用的网络，请确保你已经打开网络");
                App.getInstance().setWifi(false);
                App.getInstance().setMobile(false);
                App.getInstance().setConnected(false);
            }
        }
    }

}
