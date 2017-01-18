package fussen.yu.news.modules.sport.activity;


import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import example.fussen.baselibrary.utils.LogUtil;
import example.fussen.baselibrary.widget.Chronometer;
import example.fussen.baselibrary.widget.dialog.GlobalDialog;
import fussen.yu.news.R;
import fussen.yu.news.base.activity.BaseActivity;
import fussen.yu.news.modules.sport.bean.MyLatLng;
import fussen.yu.news.utils.AppUtils;
import fussen.yu.news.utils.SportUtils;
import fussen.yu.news.utils.ToastUtil;
import fussen.yu.news.utils.config.Config;
import fussen.yu.news.widget.SelectSportStateDialog;

import static fussen.yu.news.R.id.out_sport_iv_gps;

public class SportActivity extends BaseActivity implements LocationSource, AMapLocationListener, SelectSportStateDialog.HandlerDialogSelectSportStateListener {


    private static final String TAG = "[SportActivity]";
    //    @BindView(R.id.map_view)
//    MapView mapView;
    @BindView(out_sport_iv_gps)
    ImageView outGps;
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.out_sport_ll_one)
    LinearLayout outSportLlOne;
    @BindView(R.id.contentView)
    RelativeLayout contentView;
    @BindView(R.id.activity_sport)
    FrameLayout activitySport;
    @BindView(R.id.out_sport_tv_distance)
    TextView distance;
    @BindView(R.id.out_sport_tv_speed)
    TextView outSportTvSpeed;
    @BindView(R.id.out_sport_tv_calor)
    TextView outSportTvCalor;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView title;
    @BindView(R.id.btn_start)
    TextView btnStart;


    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationManager locationManager;

    private AMapLocation lastAMapLocation;
    private MyLocationStyle myLocationStyle;
    private MapView mapView;

    private int satelliteCount = 0;//手机返回卫星个数

    private int mapSatellites;  // 高德地图返回的卫星个数
    private int mapAccuracy;    // 高德地图返回的定位精度

    private LatLng positionLatLng;
    private LatLng startLatLng;
    private LatLng lastGpsLatLng;

    private long currentGpsTimestamp;
    private long lastGpsTimestamp;

    private float currentAltitude = 0.0f;
    private float lastAltitude = 0.0f;
    private boolean isFirstOpen = true;
    private boolean isSportting = false;//正在运动
    private boolean isFirst = true;

    private int currentSportState = END_SPORT;//当前的运动状态

    private static final int END_SPORT = -1;
    private static final int PAUSE_SPORT = 1;
    private static final int RUN_SPORT = 0;

    private boolean isClickContinue = false;
    private boolean isStart = false;
    private boolean isPause = false;
    private boolean TIME_PAUSE = false;

    private List<List<MyLatLng>> gpsPointArray = new ArrayList<>();
    private List<List<MyLatLng>> gpsPausePointArray = new ArrayList<>();
    private List<MyLatLng> normalPointList;

    private long startTimestamp;//开始时间戳
    private long rangeTime = 0;
    private int totalTime = 0;
    private float totalDistance;
    private float totalCalories;

    private DecimalFormat decimalFormat1, decimalFormat2;
    private float mMaxVelocity;
    private float altitudeLine;

    @Override
    protected int getContentView() {
        return R.layout.activity_sport;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        decimalFormat1 = new DecimalFormat("0.0");
        decimalFormat1.setRoundingMode(RoundingMode.HALF_UP);//四舍五入˚˚

        decimalFormat2 = new DecimalFormat("0.00");
        decimalFormat2.setRoundingMode(RoundingMode.HALF_UP);//四舍五入˚˚

        initMap();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {


        ivBack.setVisibility(View.VISIBLE);

        title.setText("跑步");

        initChronometer();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        registerGPSListener();

        //监听GPS
        gpsEnableListener();


        if (!AppUtils.isOPenGPS(this)) {
            satelliteCount = 0;
            gpsStateClose();
            GlobalDialog.showSelectDialog(this, "户外运动需要打开GPS开关以提高数据准确性", true, new GlobalDialog.DialogClickListener() {
                @Override
                public void confirm() {
                    AppUtils.intentOpenGPS(SportActivity.this);
                }

                @Override
                public void cancel() {
                }
            });
        }

        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        unregisterGpsEnableListener();
        unregisterGPSListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        deactivate();
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    @OnClick({R.id.iv_back, R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_start:

                if (currentSportState == END_SPORT) {
                    //开始运动
                    startSport();
                    if (positionLatLng != null && mapSatellites >= 4 && mapAccuracy <= 100) {
                    } else {
                        ToastUtil.showToast("GPS信号弱，请在户外环境下运动!");
                    }
                } else if (currentSportState == RUN_SPORT) {

                    //暂停运动
                    pauseSport();

                    new SelectSportStateDialog(this, this).show();
                }

                break;
        }
    }


    private void initMap() {

        aMap = mapView.getMap();

        //地图种类 平面地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);

        myLocationStyle = new MyLocationStyle();
        //自定义小图标
        //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.firetwo));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细

        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        setMapLocation(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 是否显示默认的定位按钮
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);  // 只在第一次定位移动到地图中心点。

    }


    private void initChronometer() {
        chronometer.setText("- -");
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {

                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                totalTime = (int) time / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                String count = hh + ":" + mm + ":" + ss;
                chronometer.setText(count);

            }
        });

    }


    private void startSport() {

        currentSportState = RUN_SPORT;
        btnStart.setText("暂停");
        isStart = true;
        isSportting = true;
        startTimestamp = System.currentTimeMillis();
        startTime();
        startLatLng = positionLatLng;
        lastAltitude = currentAltitude;
        lastGpsTimestamp = startTimestamp / 1000;
        Marker marker = aMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_running_start)));
        marker.setPosition(startLatLng);
        aMap.setMyLocationStyle(getMoveStyle());
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 20));
        normalPointList = new ArrayList<>();
        MyLatLng myLatLng = new MyLatLng(startLatLng.latitude, startLatLng.longitude);
        myLatLng.timestamp = lastGpsTimestamp;
        myLatLng.altitude = lastAltitude;
        myLatLng.sportTime = 0;
        normalPointList.add(myLatLng);
        paceIndex = 0;
        lastPaceTime = 0;
    }


    /**
     * 暂停运动
     */
    private void pauseSport() {
        currentSportState = PAUSE_SPORT;
        isStart = false;
        isPause = true;
        pauseTime();
    }

    private void startTime() {
        rangeTime = 0;
        //因为start、setBase会触发onChronometerTick()，所以时间需要totalTime-2.
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
//        totalTime -= 2;
    }

    private void pauseTime() {
        if (!TIME_PAUSE) {
            rangeTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime() - rangeTime);
            chronometer.start();
//            totalTime -= 2;
        }
        TIME_PAUSE = !TIME_PAUSE;
    }

    //定位相关
    private void setMapLocation(boolean isEnable) {
        // 设置定位监听
        aMap.setLocationSource(this);
        aMap.setMyLocationStyle(getPositionStyle());
        aMap.setMyLocationEnabled(isEnable);// 是否可触发定位并显示定位层
    }


    private MyLocationStyle getPositionStyle() {
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        return myLocationStyle;
    }


    /**
     * 跑步时地图的Style
     *
     * @return
     */
    private MyLocationStyle getMoveStyle() {
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_run));
        return myLocationStyle;
    }


    /**
     * 定位初始化及启动定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        LogUtil.fussenLog().d(TAG + "==========onLocationChangedListener=======");
        this.onLocationChangedListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位的相关调用
     */
    @Override
    public void deactivate() {

        onLocationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (onLocationChangedListener != null && aMapLocation != null && aMapLocation.getErrorCode() == 0) {

            mapAccuracy = (int) (aMapLocation.getAccuracy() + 0.5f);//返回定位精度
            mapSatellites = aMapLocation.getSatellites();//返回卫星个数

            if (!AppUtils.isOPenGPS(this)) {
                gpsStateClose();
            } else {
//                changeGps(mapAccuracy, mapSatellites);
            }

            positionLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            currentAltitude = (float) aMapLocation.getAltitude();//海拔
            currentGpsTimestamp = aMapLocation.getTime() / 1000;

            if (aMapLocation.getLocationType() == Config.LOCATION_TYPE_GPS) {
                lastGpsLatLng = positionLatLng;
                lastAMapLocation = aMapLocation;
            }

            if (isSportting) {

                if (isStart && !isPause) {
                    //处理正在运动
                    float d = AMapUtils.calculateLineDistance(startLatLng, positionLatLng) / 1000f;//计算运动的路程
                    if (d <= 0.001f) { //移动距离小于1米
                    }

                    if (aMapLocation.getLocationType() == Config.LOCATION_TYPE_GPS) {
                        List<LatLng> points = new ArrayList<>();
                        points.add(startLatLng);
                        points.add(positionLatLng);
                        if (isClickContinue) {
                            List<MyLatLng> points2 = new ArrayList<>();
                            points2.add(new MyLatLng(startLatLng.latitude, startLatLng.longitude));
                            points2.add(new MyLatLng(positionLatLng.latitude, positionLatLng.longitude));
                            isClickContinue = false;
                            gpsPointArray.add(normalPointList);
                            normalPointList = new ArrayList<>();
                            MyLatLng myLatLng = new MyLatLng(positionLatLng.latitude, positionLatLng.longitude);
                            myLatLng.timestamp = currentGpsTimestamp;
                            myLatLng.altitude = currentAltitude;
                            myLatLng.sportTime = totalTime;
                            normalPointList.add(myLatLng);
                            gpsPausePointArray.add(points2);
                            SportUtils.addDottedPath(aMap, points, Color.BLACK);
                        } else {
                            SportUtils.addPath(aMap, points, Color.parseColor("#F44336"));
                            updateLocation();
                        }

                        startLatLng = positionLatLng;
                        lastAltitude = currentAltitude;
                        lastGpsTimestamp = currentGpsTimestamp;
                        onLocationChangedListener.onLocationChanged(aMapLocation);
                        aMap.animateCamera(CameraUpdateFactory.changeLatLng(positionLatLng));

                    } else {

                        //没有GPS信号的时候 如果暂停状态回恢复，也要画出暂停时间内的虚线
                        if (isClickContinue && lastGpsLatLng != null) {
                            isClickContinue = false;

                            List<LatLng> points = new ArrayList<>();
                            points.add(startLatLng);
                            points.add(lastGpsLatLng);

                            List<MyLatLng> points2 = new ArrayList<>();
                            points2.add(new MyLatLng(startLatLng.latitude, startLatLng.longitude));
                            points2.add(new MyLatLng(lastGpsLatLng.latitude, lastGpsLatLng.longitude));

                            gpsPointArray.add(normalPointList);
                            normalPointList = new ArrayList<>();
                            MyLatLng myLatLng = new MyLatLng(lastGpsLatLng.latitude, lastGpsLatLng.longitude);
                            myLatLng.timestamp = currentGpsTimestamp;
                            myLatLng.altitude = currentAltitude;
                            myLatLng.sportTime = totalTime;
                            normalPointList.add(myLatLng);
                            gpsPausePointArray.add(points2);
                            SportUtils.addDottedPath(aMap, points, Color.BLACK);

                            onLocationChangedListener.onLocationChanged(lastAMapLocation);
                            aMap.animateCamera(CameraUpdateFactory.changeLatLng(lastGpsLatLng));
                            startLatLng = lastGpsLatLng;
                        }
                    }
                } else {
                    onLocationChangedListener.onLocationChanged(aMapLocation);
                    if (isFirst) {
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 16));
                        isFirst = false;
                    }

                }


            } else {
                //没有运动 显示定位
                onLocationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (isFirstOpen) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 16));
                    isFirstOpen = false;
                }
            }
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            ToastUtil.showToast(errText);
        }
    }


    private void changeGps(int mapAccuracy, int mapSatellites) {
        if (mapAccuracy <= 50 && mapSatellites >= 8) {
            gpsStateStrong();
        } else if (mapAccuracy <= 100 && mapSatellites >= 4) {
            gpsStateMiddle();
        } else {
            gpsStateWeak();
        }
    }


    /**
     * 监听GPS开关
     * <p/>
     * 监听代表 GPS 开关的 URI 的数据是否有发生变化
     * GPS的开关是系统设置，android.provider.Settings.Secure 代表了app可以读取但不能修改的系统设置。可以从这个类中获取到代表GPS开关的URI。
     */
    private void gpsEnableListener() {
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED), false, mGpsMonitor);
    }

    private void unregisterGpsEnableListener() {
        getContentResolver().unregisterContentObserver(mGpsMonitor);
    }


    private void registerGPSListener() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        locationManager.addGpsStatusListener(gpsStatusListener);
    }

    private void unregisterGPSListener() {
        if (locationManager != null) {
            locationManager.removeGpsStatusListener(gpsStatusListener);
        }
    }


    /**
     * ===================================GPS状态监听=======================================
     */

    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            final boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (enabled) {
                        //gps改变
                        LogUtil.fussenLog().d(TAG + "======ContentObserver==gps改变了====");
                        gpsChange();
                    } else {
                        //无gps
                        gpsStateClose();
                    }
                }
            });

        }
    };


    /**
     * ===================================GPS状态监听=======================================
     */

    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            // 取当前状态
            GpsStatus gpsStatus = locationManager.getGpsStatus(null);
            switch (event) {
                //第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    // 第一次定位时间UTC gps可用
                    //int i = gpsStatus.getTimeToFirstFix();
                    break;
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:// 周期的报告卫星状态

                    // 得到所有收到的卫星的信息，包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                    Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                    Iterator<GpsSatellite> satelliteIterator = satellites.iterator();

                    List<GpsSatellite> satelliteList = new ArrayList<>();
                    int count = 0;
                    int maxSatellites = gpsStatus.getMaxSatellites();//获取卫星颗数的默认最大值
                    while (satelliteIterator.hasNext() && count <= maxSatellites) {
                        GpsSatellite satellite = satelliteIterator.next();
                        boolean inFix = satellite.usedInFix();//已定位卫星
                        if (inFix) {
                            count++;
                            satelliteList.add(satellite);
                        }
                    }
                    satelliteCount = count;

                    LogUtil.fussenLog().d(TAG + "===卫星状态改变=====已定位卫星数======" + satelliteCount);
                    gpsChange();
                    break;
                //定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    break;
                //定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    break;
            }
        }
    };


    /**
     * ========================================更新位置============================================
     */


    private String currentDistance = "0.0";
    private String currentSpeed = "0.0";

    private void updateLocation() {
        float distanceMeter = AMapUtils.calculateLineDistance(startLatLng, positionLatLng);
        if (distanceMeter <= 0) {
            return;
        }
        totalDistance += distanceMeter;
        float distanceKm = Math.round((totalDistance / 1000f) * 100) / 100f;
        currentDistance = Math.round((totalDistance / 1000f) * 10) / 10f + "";
        long timeInterval = currentGpsTimestamp - lastGpsTimestamp;
        if (timeInterval <= 0) {
            return;
        }
        float timeIntervalMin = timeInterval / 60f;
        float timeIntervalHour = timeInterval / 3600f;
        float speedMIN = distanceMeter / timeIntervalMin; //米/分（速度）
        float speedHour = (distanceMeter / 1000f) / timeIntervalHour; //千米/小时（速度）当前速度
        if (speedMIN > 900.0f) { // 最大15米/秒
            Random rand = new Random();
            int i = rand.nextInt(300);
            speedMIN = i + 600.0f;
        }
        if (speedHour > 80.0f) { // 最大80公里/小时
            Random rand = new Random();
            int i = rand.nextInt(20);
            speedHour = i + 50.0f;
        }
        if (currentAltitude > lastAltitude) {
            altitudeLine += currentAltitude - lastAltitude;
        }
        float average = 0;//平均速度
        String pace = "";//配速
        if (totalTime != 0) {
            average = (totalDistance / totalTime) * 3.6f;
        }
        if (totalDistance != 0) {
            int m = (int) (totalTime / distanceKm) / 60;
            int s = (int) (totalTime / distanceKm) % 60;
            String min = m > 9 ? m + "\'" : "0" + m + "\'";
            String ss = s > 9 ? s + "\"" : "0" + s + "\"";
            pace = min + ss;
        }
        if (speedHour > mMaxVelocity) {
            mMaxVelocity = speedHour;
        }
        currentSpeed = decimalFormat1.format(speedHour);

        //已走过的路程
        distance.setText(distanceKm + "");

        //当前速度
        outSportTvSpeed.setText(decimalFormat2.format(speedHour) + "");

        // setText(pace);//配速
        MyLatLng myLatLng = new MyLatLng(positionLatLng.latitude, positionLatLng.longitude);
        myLatLng.speed = Math.round(speedHour * 100) / 100f;
        myLatLng.timestamp = currentGpsTimestamp;
        myLatLng.sportTime = totalTime;
        myLatLng.timeInterval = timeInterval;
        myLatLng.altitude = Math.round(currentAltitude * 10) / 10f;
        normalPointList.add(myLatLng);

        //增加配速组的计算
        if (((int) totalDistance / 1000 - paceIndex) == 1) {

            int paceTime = totalTime - lastPaceTime;
            if ((totalTime - priorPointTime) > 60) { //如果整公里这一点跳动较大 则取均值估算出整公里点的时间
                paceTime = (int) (((totalTime - priorPointTime) * ((paceIndex + 1) * 1000 - priorPointDistance)) / (totalDistance - priorPointDistance)) + (priorPointTime - lastPaceTime);
                lastPaceTime += paceTime;
            } else {
                lastPaceTime = totalTime;
            }
            paceIndex++;
            LogUtil.fussenLog().d(TAG + "=======paceIndex=======" + paceIndex);

        }

        // 记录上一个坐标点的相对时间和距离（相对于起点）
        priorPointTime = totalTime;
        priorPointDistance = totalDistance;

        // 计算卡路里 
        totalCalories += SportUtils.getCalories(timeIntervalMin, speedMIN);
        outSportTvCalor.setText(decimalFormat2.format(totalCalories) + "");
    }

    private int paceIndex = 0;
    private int lastPaceTime = 0;
    private int priorPointTime = 0;
    private float priorPointDistance = 0;


    private void gpsChange() {

        if (satelliteCount == 0) {
            gpsStateClose();
        } else if (satelliteCount >= 1 && satelliteCount <= 3) {
            gpsStateWeak();
        } else if (satelliteCount >= 4 && satelliteCount <= 6) {
            gpsStateMiddle();
        } else if (satelliteCount >= 7) {
            gpsStateStrong();
        }
    }


    private void gpsStateStrong() {
        outGps.setImageResource(R.drawable.out_sport_iv_gps3);
    }

    private void gpsStateWeak() {
        outGps.setImageResource(R.drawable.out_sport_iv_gps1);
    }

    private void gpsStateMiddle() {
        outGps.setImageResource(R.drawable.out_sport_iv_gps2);
    }

    //无gps
    private void gpsStateClose() {
        outGps.setImageResource(R.drawable.out_sport_iv_gps0);
    }

    @Override
    public void onSelectSportState(String i) {
        if (i.equals(SelectSportStateDialog.CONTINUE)) {
            continueSport();
        } else if (i.equals(SelectSportStateDialog.END)) {
            endSport();
        }
    }

    private void endSport() {
        gpsPointArray.add(normalPointList);

        btnStart.setText("开始");
        isSportting = false;
        isStart = false;
        isPause = false;
        isSportting = false;
        currentSportState = END_SPORT;
        paceIndex = 0;
        lastPaceTime = 0;
        totalTime = 0;
    }


    private void continueSport() {
        currentSportState = RUN_SPORT;
        isStart = true;
        isPause = false;
        isClickContinue = true;
        pauseTime();
    }

}
