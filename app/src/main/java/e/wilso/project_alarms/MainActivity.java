package e.wilso.project_alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

   private TextView mTextView;
   MediaPlayer mediaPlayer;
   Intent intentSound;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mTextView  = findViewById(R.id.textView);

      Button buttonTimePicker = findViewById(R.id.button_timepicker);
      buttonTimePicker.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            TimePickerFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
         }
      });

      Button buttonCancelAlarm = findViewById(R.id.button_cancel);
      buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            cancelAlarm();
         }
      });
   }

   @Override
   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
      //TextView textView = findViewById(R.id.textView);
      //textView.setText("Hour: " + hourOfDay + " Minute: " + minute);

      Calendar c = Calendar.getInstance();
      c.set(Calendar.HOUR_OF_DAY, hourOfDay);
      c.set(Calendar.MINUTE, minute);
      c.set(Calendar.SECOND, 0);

      updateTimeText(c);
      startAlarm(c);
   }

   private void updateTimeText(Calendar c) {
      String timeText = "Alarm set for: ";
      timeText += java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.getTime());

      mTextView.setText(timeText);
   }

   @TargetApi(Build.VERSION_CODES.KITKAT)
   private void startAlarm(Calendar c) {
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      Intent intent = new Intent(this, AlertReceiver.class);

      intent.putExtra("music", "yes");

      PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

      alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
   }

   private void cancelAlarm() {
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      Intent intent = new Intent(this, AlertReceiver.class);

      intent.putExtra("music", "no");
      sendBroadcast(intent);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

      alarmManager.cancel(pendingIntent);
      mTextView.setText("Alarm canceled");
   }
}
