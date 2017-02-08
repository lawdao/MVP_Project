package fussen.yu.news;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

import de.greenrobot.dao.query.QueryBuilder;
import fussen.yu.news.inject.component.ApplicationComponent;
import fussen.yu.news.inject.component.DaggerApplicationComponent;
import fussen.yu.news.inject.module.ApplicationModule;
import fussen.yu.news.receiver.NetworkStateReceiver;
import fussen.yu.news.utils.config.Config;


/**
 * Created by Fussen on 2016/11/23.
 */

public class App extends Application {

    private static Context context;
    private boolean isEnablaWifi;
    private boolean isWifi;
    private boolean isMobile;
    private boolean isConnected;
    private static App app;

    private static ApplicationComponent mApplicationComponent;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();

        initComponent();

        //监听网络状态的变化
        registerNetworkState();

        // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        initDatabase();

    }


    public static App getInstance() {
        return app;
    }

    private void registerNetworkState() {

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(networkStateReceiver, filter);
    }


    /**
     * 初始化全局Component
     */
    private void initComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }


    public static ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getContext() {
        return context;
    }


    /**
     * WiFi状态
     *
     * @param isEnable
     */
    public void setEnablaWifi(boolean isEnable) {

        isEnablaWifi = isEnable;
    }

    /**
     * 连接的是否是WiFi
     *
     * @param isWifi
     */
    public void setWifi(boolean isWifi) {

        this.isWifi = isWifi;

    }

    /**
     * 连接的是否是移动数据
     *
     * @param isMobile
     */
    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    /**
     * 网络是否连接
     *
     * @param isConnected
     */
    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isWifi || isMobile;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public boolean isWifi() {
        return isWifi;
    }

    /**
     * WiFi是否打开
     *
     * @return
     */
    public boolean isEnablaWifi() {
        return isEnablaWifi;
    }


    private void initDatabase() {

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Config.DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();

        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);

        mDaoSession = daoMaster.newSession();

        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }

    public static UserDao getUserDao() {
        return mDaoSession.getUserDao();
    }

    public static SportDao getSportDao() {
        return mDaoSession.getSportDao();
    }

}
