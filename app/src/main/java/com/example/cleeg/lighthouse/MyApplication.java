package com.example.cleeg.lighthouse;

import android.app.Application;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.example.cleeg.lighthouse.estimote.BeaconNotificationsManager;

public class MyApplication extends Application {

    private boolean beaconNotificationsEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "lighthouse-8u5", "8af68c0e454258ec2261aea53c15a950");
        //EstimoteSDK.enableDebugLogging(true);
    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);
        beaconNotificationsManager.addNotification(
                "af36cf7586194e78068aaf00a747d322",
                "Hello, world.",
                "Goodbye, world.");
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }
}
