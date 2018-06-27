package e.wilso.project_alarms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {

   final String TAG = "AlertReceiver";

   @Override
   public void onReceive(Context context, Intent intent) {
      String music = intent.getExtras().getString("music");
      Log.e(TAG, music + " :to music!");





      NotificationHelper notificationHelper = new NotificationHelper(context);
      NotificationCompat.Builder nb = notificationHelper.getChannelNotification();

      notificationHelper.getManager().notify(1, nb.build());

      Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
      serviceIntent.putExtra("music", music);

      context.startService(serviceIntent);
   }
}
