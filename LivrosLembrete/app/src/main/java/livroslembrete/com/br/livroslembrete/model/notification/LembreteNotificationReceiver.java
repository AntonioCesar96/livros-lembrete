package livroslembrete.com.br.livroslembrete.model.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LembreteNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, LembreteNotificationService.class);
        intentService.putExtra("idLivro", intent.getSerializableExtra("idLivro"));
        context.startService(intentService);
    }
}
