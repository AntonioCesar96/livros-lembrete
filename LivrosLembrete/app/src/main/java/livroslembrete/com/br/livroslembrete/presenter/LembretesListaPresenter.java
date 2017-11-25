package livroslembrete.com.br.livroslembrete.presenter;

import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.model.LembretesListaModel;
import livroslembrete.com.br.livroslembrete.utils.AlarmLembreteUtil;
import livroslembrete.com.br.livroslembrete.view.LembretesView;

public class LembretesListaPresenter {
    private LembretesListaModel model;
    private LembretesView view;
    private List<Lembrete> lembretes;

    public LembretesListaPresenter(LembretesView view) {
        this.view = view;
        model = new LembretesListaModel(this);
    }

    public void setAdapter(List<Lembrete> lembretes) {
        view.setAdapter(lembretes);
    }

    public void showSnack(String s) {
        view.showSnack(s);
    }

    public void buscarLembretes() {
        lembretes = model.buscarLembretess();
        setAdapter(lembretes);
        if (lembretes.size() == 0) {
            showSnack("Não há lembretes");
        }
    }

    public void onChange(boolean isChecked, int idx) {
        Lembrete lembrete = lembretes.get(idx);
        AlarmLembreteUtil alarmUtil = new AlarmLembreteUtil(view.getActivity());

        if (isChecked) {
            model.deletar(lembrete.getIdLivro());
            alarmUtil.cancelarAlarm(lembrete.getIdLivro());
            return;
        }

        model.save(lembrete);
        alarmUtil.agendarAlarm(lembrete.getIdLivro(), lembrete.getDataHora().getTimeInMillis());
    }
}
