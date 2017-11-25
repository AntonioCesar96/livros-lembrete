package livroslembrete.com.br.livroslembrete.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.view.CriarLembreteView;
import livroslembrete.com.br.livroslembrete.view.fragments.dialog.CriarLembreteDialog;

public class CriarLembretePresenter {
    private CriarLembreteView view;
    private CriarLembreteDialog.Callback callback;
    private Calendar dataHora;

    public CriarLembretePresenter(CriarLembreteView view, CriarLembreteDialog.Callback callback) {
        this.view = view;
        this.callback = callback;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Lembrete lembrete) {
        if (lembrete != null) {
            view.updateData(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(lembrete.getDataHora().getTime()));
            view.updateHora(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(lembrete.getDataHora().getTime()));
            dataHora = lembrete.getDataHora();
        } else {
            dataHora = Calendar.getInstance();
        }
    }

    public void changeDataPicker(int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dataHora.set(year, monthOfYear, dayOfMonth);
        view.updateData(dateFormatter.format(dataHora.getTime()));
    }

    public void changeTimePicker(int selectedHour, int selectedMinute) {
        dataHora.set(Calendar.HOUR_OF_DAY, selectedHour);
        dataHora.set(Calendar.MINUTE, selectedMinute);
        dataHora.set(Calendar.SECOND, 0);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        view.updateHora(timeFormatter.format(dataHora.getTime()));
    }

    public void salvarLembrete(String data, String hora) {
        try {
            new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(hora);
        } catch (ParseException pe) {
            view.showToast("Selecione uma hora");
            return;
        }

        try {
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(data);
        } catch (ParseException pe) {
            view.showToast("Selecione uma data");
            return;
        }

        if (callback != null) {
            callback.callback(dataHora);
        }
        view.dismiss();
    }
}
