package livroslembrete.com.br.livroslembrete.model.utils;

import android.content.Context;
import android.content.Intent;

public class AlarmLembreteUtil {
    public static final String ACTION = "NOTIFICAR_LEMBRETE";
    private final Context context;

    public AlarmLembreteUtil(Context context) {
        this.context = context;
    }

    public void agendarAlarm(Long idLivro, long dataEmMilisegundos) {
        Intent intent = new Intent(ACTION);
        intent.putExtra("idLivro", idLivro);
        AlarmUtil.schedule(context, idLivro.intValue(), intent, dataEmMilisegundos);
    }

    public void cancelarAlarm(Long idLivro) {
        Intent intent = new Intent(ACTION);
        AlarmUtil.cancel(context, idLivro.intValue(), intent);
    }
}
