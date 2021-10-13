package com.mydev.reminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.mydev.reminderapplication.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pi;

    private Button selecetTimeBtn;
    private Button setAlarmBtm;
    private Button cancleAlarmBtn;
    private TextView selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        selecetTimeBtn = findViewById(R.id.selecetTimeBtn);
        setAlarmBtm = findViewById(R.id.setAlarmBtm);
        cancleAlarmBtn = findViewById(R.id.cancleAlarmBtn);
        selectedTime = findViewById(R.id.selectedTime);
        createNotificationChannel();


        selecetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        setAlarmBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });

        cancleAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancleAlarm();
            }
        });

    }

    private void cancleAlarm() {

        Intent intent = new Intent(this, BackgroudTimerService.class);
        pi = PendingIntent.getBroadcast(this, 0, intent,0);

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pi);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();

    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BackgroudTimerService.class);
        pi = PendingIntent.getBroadcast(this, 0, intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();


    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(10)
                .setMinute(0)
                .setTitleText("Select  Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(), "notifierID" );

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(picker.getHour() > 12){
                    selectedTime.setText(
                        String.format("%2d", (picker.getHour()-12))+" : "+ String.format("%2d", picker.getMinute()) + " PM"
                    );

                }else{
                    selectedTime.setText(picker.getHour()+" : "+ picker.getMinute() + " AM");
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

            }
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NotifierReminderChannel";
            String description = "Channel for the notifier";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifierID", name,importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}