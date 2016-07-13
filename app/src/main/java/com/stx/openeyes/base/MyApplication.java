package com.stx.openeyes.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

/**
 * Created by xhb on 2016/2/29.
 * 程序初始化
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        x.Ext.init(this);
    }
}
