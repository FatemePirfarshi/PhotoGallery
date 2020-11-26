package org.maktab.photogallery;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.maktab.photogallery.event.NotificationEvent;
import org.maktab.photogallery.event.RxBus;

public class PhotoGalleryApplication extends Application {

    private static final String TAG = "PhotoGalleryApplication";
    public static final String TAG_EVENT_BUS = "PGEventBus";
    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
        createNotificationChannel();

        bus = RxBus.getInstance();
        new Thread() {
            @Override
            public void run() {
                bus.notify();
            }
        }.start();
        //   EventBus.getDefault().register(this);
    }

    public RxBus bus() {
        return bus;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

       // EventBus.getDefault().unregister(this);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onNotificationEventListener(NotificationEvent notificationEvent) {
        String msg = "Application received the notification event";
        Log.d(TAG_EVENT_BUS, msg);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(
                notificationEvent.getNotificationId(),
                notificationEvent.getNotification());
    }
}
