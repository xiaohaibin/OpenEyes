package com.stx.openeyes.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by xhb on 2016/1/20.
 * 网络状态工具类
 */
public class NetConnectedUtils {
    /**
     * 1.判断网络是否连接
     *
     * @param context 上下文，通过它获取到ConnectivityManager
     * @return true 网络连接成功   false  网络连接失败
     */
    public static boolean isNetConnected(Context context) {
        boolean ret = false;
        //1.获取到ConnectivityManager
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取到当前活动的网络（即正在传输数据的网络），若存在，那么可以确定网络连接存在；
        //connectivityManager.getActiveNetwork();  版本23新添加的，不建议这么早使用

        //返回当前正在传输数据的网络连接的信息，API 1 就存在了
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {//无网络连接
            return ret;
        }

        //网络是否可用
//        networkInfo.isAvailable();
        //网络是否连接
//        networkInfo.isConnected();
        //只有两个都满足才能确保网络是连接上的
        ret = networkInfo.isAvailable() & networkInfo.isConnected();
        return ret;
    }

    /**
     * 2.判断手机网络是否存在
     *
     * @param context 上下文
     * @return 返回 false 无手机网络   true 有手机网络
     */
    public static boolean isPhoneNetConnected(Context context) {
        int typeMobile = ConnectivityManager.TYPE_MOBILE;//手机网络类型
        return isNetworkConnected(context, typeMobile);
    }


    /**
     * 2.判断WIFI网络是否存在
     *
     * @param context 上下文
     * @return 返回 false 无wifi网络   true 有wifi网络
     */
    public static boolean isWifiNetConnected(Context context) {
        int typeMobile = ConnectivityManager.TYPE_WIFI;//WIFI网络类型
        return isNetworkConnected(context, typeMobile);
    }

    /**
     * 返回网络是否连接
     *
     * @param context    上下文
     * @param typeMobile 网络类型
     * @return true  有网络连接   false 无网络连接
     */
    private static boolean isNetworkConnected(Context context, int typeMobile) {
        boolean ret = false;
        //判断是否有网络
        if (!isNetConnected(context)) {
            //如果没有网络连接，直接返回
            return ret;
        }
        //获取到网络连接管理器
        ConnectivityManager connectManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //当获取到网络连接信息的时候，可以获取到某一个类型的网络连接
        //获取到手机网络的信息
        NetworkInfo networkInfo = connectManger.getNetworkInfo(typeMobile);
        //如果不存在，那么就没有连接了
        if (networkInfo == null) {
            return ret;
        }
        //判断手机网络是否可用
        ret = networkInfo.isAvailable() & networkInfo.isConnected();
        return ret;
    }
}
