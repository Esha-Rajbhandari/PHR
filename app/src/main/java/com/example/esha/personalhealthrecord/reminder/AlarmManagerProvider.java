package com.example.esha.personalhealthrecord.reminder;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by delaroy on 9/22/17.
 */

public class AlarmManagerProvider {
    private static final String TAG = AlarmManagerProvider.class.getSimpleName();
    private static AlarmManager alarmManager;
    public static synchronized void injectAlarmManager(AlarmManager alarmManager) {
        if (alarmManager != null) {
            throw new IllegalStateException("Alarm Manager Already Set");
        }
        alarmManager = alarmManager;
    }
    /*package*/ static synchronized AlarmManager getAlarmManager(Context context) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return alarmManager;
    }
}
