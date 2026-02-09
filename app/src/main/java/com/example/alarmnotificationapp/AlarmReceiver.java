package com.example.alarmnotificationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(
                context,
                "Alarm Aktif!",
                "Waktunya bangun atau cek aktivitasmu!"
        );
    }
}