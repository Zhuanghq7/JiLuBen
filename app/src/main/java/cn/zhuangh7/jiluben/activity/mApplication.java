package cn.zhuangh7.jiluben.activity;

import android.app.Application;

import SwipeHelper.ActivityLifecycleHelper;

/**
 * Created by Zhuangh7 on 2016/10/29.
 */

public class mApplication extends Application {

    private ActivityLifecycleHelper mActivityLifecycleHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());

    }

    public ActivityLifecycleHelper getActivityLifecycleHelper(){
        return mActivityLifecycleHelper;}
}
