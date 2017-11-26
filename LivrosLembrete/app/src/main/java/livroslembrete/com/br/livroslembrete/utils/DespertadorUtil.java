package livroslembrete.com.br.livroslembrete.utils;

import android.content.Context;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;

public class DespertadorUtil {

    private Context context;

    public DespertadorUtil(Context context){
        this.context = context;
    }

    public void criarAlarm(Lembrete lembrete, List<DiasSemana> diasSemanas) {
        LembreteDAO dao = null;
        try {
            dao = new LembreteDAO(Application.getInstance().getDataBaseHelper().getConnectionSource());
            int diaSemanaAtual = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            lembrete.setDiasSemana(diasSemanas);

            int ultimoDiaSemanaAlarme = 0;
            for (int i = 6; i <= lembrete.getDiasSemana().size(); i--) {
                DiasSemana dia = lembrete.getDiasSemana().get(i);
                if (dia.isDiaSelecionado()) {
                    ultimoDiaSemanaAlarme = dia.getDiaSemana();
                    break;
                }
            }

            for (int i = 0; i < lembrete.getDiasSemana().size(); i++) {
                DiasSemana dia = lembrete.getDiasSemana().get(i);
                if (dia.isDiaSelecionado()) {
                    if (diaSemanaAtual == dia.getDiaSemana()) {
                        if (!dia.isNotificado()) {
                            lembrete.getDataHora().set(Calendar.DAY_OF_WEEK, dia.getDiaSemana());
                            dia.setNotificado(true);
                            agendarAlarm(lembrete);
                            dao.save(lembrete);
                            break;
                        } else {
                            dia.setNotificado(false);
                            dao.save(lembrete);
                        }
                    }

                    if (dia.getDiaSemana() > diaSemanaAtual) {
                        if (!dia.isNotificado()) {
                            lembrete.getDataHora().set(Calendar.DAY_OF_WEEK, dia.getDiaSemana());
                            dia.setNotificado(true);
                            agendarAlarm(lembrete);
                            dao.save(lembrete);
                            break;
                        } else {
                            dia.setNotificado(false);
                            dao.save(lembrete);
                        }
                    }

                    if (ultimoDiaSemanaAlarme == dia.getDiaSemana()) {
                        for (int m = 0; m < lembrete.getDiasSemana().size(); m++) {
                            DiasSemana diaMenor = lembrete.getDiasSemana().get(m);
                            if (diaMenor.isDiaSelecionado()) {

                                Calendar calendar = lembrete.getDataHora();

                                calendar.set(Calendar.DAY_OF_WEEK, diaMenor.getDiaSemana());
                                calendar.set(Calendar.WEEK_OF_YEAR, (calendar.get(Calendar.WEEK_OF_YEAR) + 1));

                                lembrete.setDataHora(calendar);

                                diaMenor.setNotificado(true);
                                agendarAlarm(lembrete);
                                dao.save(lembrete);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agendarAlarm(Lembrete lembrete) {
        AlarmLembreteUtil alarm = new AlarmLembreteUtil(context);
        alarm.agendarAlarm(lembrete.getIdLivro(), lembrete.getDataHora().getTimeInMillis());
    }
}
