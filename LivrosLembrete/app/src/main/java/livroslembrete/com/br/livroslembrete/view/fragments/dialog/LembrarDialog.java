package livroslembrete.com.br.livroslembrete.view.fragments.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.model.domain.Lembrete;


public class LembrarDialog extends DialogFragment {
    private Callback callback;
    private Button tDataInicio, tHoraInicio;
    private Calendar dataHora;
    private Lembrete lembrete = null;

    public static void show(FragmentManager fm, Lembrete lembrete, Callback callback) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("lembrar");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        LembrarDialog frag = new LembrarDialog();
        frag.lembrete = lembrete;
        frag.callback = callback;
        frag.show(ft, "lembrar");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }

        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lembrar, container, false);
        view.findViewById(R.id.btLembrar).setOnClickListener(onClickAtualizar());
        tDataInicio = view.findViewById(R.id.tDataInicio);
        tHoraInicio = view.findViewById(R.id.tHoraInicio);

        if (lembrete != null) {
            tDataInicio.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(lembrete.getDataHora().getTime()));
            tHoraInicio.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(lembrete.getDataHora().getTime()));
            dataHora = lembrete.getDataHora();
        } else {
            dataHora = Calendar.getInstance();
        }

        configurarBotoes();
        return view;
    }

    private View.OnClickListener onClickAtualizar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tDataInicio.getText().toString();
                String hora = tHoraInicio.getText().toString();

                try {
                    new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(hora);
                } catch (ParseException pe) {
                    Toast.makeText(getActivity(), "Selecione uma hora", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(data);
                } catch (ParseException pe) {
                    Toast.makeText(getActivity(), "Selecione uma data", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (callback != null) {
                    callback.onLembrar(dataHora);
                }
                dismiss();
            }
        };
    }

    private void configurarBotoes() {
        DatePickerDialog dataInicioDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dataHora.set(year, monthOfYear, dayOfMonth);
                tDataInicio.setText(dateFormatter.format(dataHora.getTime()));
            }

        }, dataHora.get(Calendar.YEAR), dataHora.get(Calendar.MONTH), dataHora.get(Calendar.DAY_OF_MONTH));

        TimePickerDialog horaInicioDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

                dataHora.set(Calendar.HOUR_OF_DAY, selectedHour);
                dataHora.set(Calendar.MINUTE, selectedMinute);
                dataHora.set(Calendar.SECOND, 0);
                tHoraInicio.setText(timeFormatter.format(dataHora.getTime()));
            }
        }, dataHora.get(Calendar.HOUR_OF_DAY), dataHora.get(Calendar.MINUTE), true);

        dataInicioDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());

        tDataInicio.setOnClickListener(clickGeneric(dataInicioDialog));
        tHoraInicio.setOnClickListener(clickGeneric(horaInicioDialog));
    }

    private View.OnClickListener clickGeneric(final AlertDialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        };
    }

    public interface Callback {
        void onLembrar(Calendar dataHora);
    }
}
