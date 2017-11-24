package livroslembrete.com.br.livroslembrete.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtil {

    public static void schedule(Context context, int requestCode, Intent intent, long triggerAtMillis) {
        PendingIntent p = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, p);
    }

    public static void cancel(Context context, int requestCode, Intent intent) {
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent p = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarme.cancel(p);
    }
}
