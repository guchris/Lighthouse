package com.example.cleeg.lighthouse.estimote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.monitoring.EstimoteMonitoring;
import com.estimote.monitoring.EstimoteMonitoringListener;
import com.estimote.monitoring.EstimoteMonitoringPacket;
import com.example.cleeg.lighthouse.MainActivity;

import java.util.List;

public class BeaconNotificationsManager {

    private static final String TAG = "BeaconNotifications";

    private BeaconManager beaconManager;
    private EstimoteMonitoring estimoteMonitoring;

    private String enterMessages;
    private String deviceId;

    private Context context;

    private int notificationID = 0;

    public BeaconNotificationsManager(Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        estimoteMonitoring = new EstimoteMonitoring();
        estimoteMonitoring.setEstimoteMonitoringListener(new EstimoteMonitoringListener() {
            @Override
            public void onEnteredRegion() {
                Boolean notExecuted = true;
                Log.d(TAG, "onEnteredRegion");
                String message = enterMessages;
                if ((message != null) && notExecuted) {
                    showNotification(message);
                    notExecuted = false;
                }
            }

            @Override
            public void onExitedRegion() {
                Log.d(TAG, "onExitedRegion");
            }
        });

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> locations) {
                for (EstimoteLocation location : locations) {
                    if (location.id.toHexString().equals(deviceId)) {
                        estimoteMonitoring.startEstimoteMonitoring(new EstimoteMonitoringPacket(location.id.toHexString(), location.rssi, location.txPower, location.channel, location.timestamp));
                    }
                }
            }
        });
    }

    public void addNotification(String deviceId, String enterMessage) {
        this.deviceId = deviceId;
        enterMessages = enterMessage;
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    private void showNotification(String message) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Lighthouse Notifications")
                .setContentText(message)
                .setColor(Color.argb(255, 132, 251, 143))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());
    }
}
