package e.wilso.project_alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

public class RingtonePlayingService extends Service {

   MediaPlayer mMediaPlayer;
   private int startId = 0;
   private String TAG = "RingtonePlayingService";

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      Log.e("MyActivity", "In the Richard service");
      return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {

      String music = intent.getExtras().getString("music");
      Log.e(TAG, music + " :to music!");

      assert music != null;
      switch (music) {
         case "no":
            startId = 0;
            break;
         case "yes":
            startId = 1;
            break;
      }

      if(startId == 1) {
         mMediaPlayer = MediaPlayer.create(this, R.raw.machiko);
      }
      else {
         mMediaPlayer.stop();
         mMediaPlayer.reset();
      }
      mMediaPlayer.start();

      Log.e("MyActivity", "In the service");


      return START_NOT_STICKY;
   }

   @Override
   public void onDestroy() {
      Log.e("JSLog", "on destroy called");
      super.onDestroy();
   }
}

