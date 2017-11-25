package livroslembrete.com.br.livroslembrete.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.view.activitys.LivroDetalhesActivity;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.utils.NotificationUtil;

public class LembreteNotificationService extends Service {

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new WorkerThread(intent, startId).start();
        return super.onStartCommand(intent, flags, startId);
    }

    class WorkerThread extends Thread {
        private Intent intent;
        private int startId;

        public WorkerThread(Intent intent, int startId) {
            this.intent = intent;
            this.startId = startId;
        }

        public void run() {

            try {
                Long idLivro = (Long) intent.getSerializableExtra("idLivro");
                DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
                LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
                Lembrete lembrete = dao.queryForId(idLivro);

                Intent notifIntent = new Intent(getApplicationContext(), LivroDetalhesActivity.class);
                notifIntent.putExtra("notificacao", true);
                notifIntent.putExtra("lembrete", lembrete);

                NotificationUtil.create(getApplicationContext(), idLivro.intValue(), notifIntent,
                        R.mipmap.ic_launcher, "Lembrete de leitura", lembrete.getNomeLivro());
                dao.deletar(idLivro);
                stopSelf(startId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
