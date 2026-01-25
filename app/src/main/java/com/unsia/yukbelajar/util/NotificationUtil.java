package com.unsia.yukbelajar.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.unsia.yukbelajar.R;

public class NotificationUtil {

    public static void showQuizFinished(Context context, int score) {

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "quiz_channel",
                    "Quiz Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        Notification notification =
                new NotificationCompat.Builder(context, "quiz_channel")
                        .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                        .setContentTitle("Quiz Finished 🎉")
                        .setContentText("Your score: " + score)
                        .setAutoCancel(true)
                        .build();

        manager.notify(1001, notification);
    }
}

