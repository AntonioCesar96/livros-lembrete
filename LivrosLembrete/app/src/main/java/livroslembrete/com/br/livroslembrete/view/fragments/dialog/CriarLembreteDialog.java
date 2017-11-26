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
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.presenter.CriarLembretePresenter;
import livroslembrete.com.br.livroslembrete.view.CriarLembreteView;


public class CriarLembreteDialog extends DialogFragment implements CriarLembreteView {
    private Callback callback;
    private Button tData, tHora;
    private Lembrete lembrete = null;
    private CriarLembretePresenter presenter;
    private Button dom, seg, ter, qua, qui, sex, sab;
    private Button[] buttons;
    private List<DiasSemana> auxDiasSemana;

    public static void show(FragmentManager fm, Lembrete lembrete, Callback callback) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("lembrar");
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null);
        CriarLembreteDialog frag = new CriarLembreteDialog();
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

//        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
//        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
//        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lembrar, container, false);
        view.findViewById(R.id.btLembrar).setOnClickListener(onClickSalvarLembrete());
        tData = view.findViewById(R.id.tDataInicio);
        tHora = view.findViewById(R.id.tHoraInicio);

        dom = view.findViewById(R.id.dom);
        seg = view.findViewById(R.id.seg);
        ter = view.findViewById(R.id.ter);
        qua = view.findViewById(R.id.qua);
        qui = view.findViewById(R.id.qui);
        sex = view.findViewById(R.id.sex);
        sab = view.findViewById(R.id.sab);

        buttons = new Button[]{dom, seg, ter, qua, qui, sex, sab};

        if (lembrete != null && lembrete.getDiasSemana() != null) {
            auxDiasSemana = lembrete.getDiasSemana();

            for (int j = 0; j < buttons.length; j++) {
                if (auxDiasSemana.get(j).isDiaSelecionado()) {
                    buttons[j].setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    buttons[j].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_primary));
                } else {
                    buttons[j].setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    buttons[j].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_white));
                }
            }
        } else {
            auxDiasSemana = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                auxDiasSemana.add(new DiasSemana(i + 1, false, false));
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < buttons.length; j++) {
                        if (buttons[j].equals(view)) {
                            if (!auxDiasSemana.get(j).isDiaSelecionado()) {
                                //posicoes[j] = true;
                                auxDiasSemana.get(j).setDiaSelecionado(true);
                                buttons[j].setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                                buttons[j].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_primary));
                            } else {
                                //posicoes[j] = false;
                                auxDiasSemana.get(j).setDiaSelecionado(false);
                                buttons[j].setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                                buttons[j].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_white));
                            }
                        }
                    }
                }
            });
        }

        presenter = new CriarLembretePresenter(this, callback);
        presenter.setDataHora(lembrete);

        setupButtons();

        return view;
    }

    private View.OnClickListener onClickSalvarLembrete() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.salvarLembrete(tHora.getText().toString(), auxDiasSemana);
            }
        };
    }

    private void setupButtons() {
        Calendar dataHora = presenter.getDataHora();

        DatePickerDialog dataDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                presenter.changeDataPicker(year, monthOfYear, dayOfMonth);
            }

        }, dataHora.get(Calendar.YEAR), dataHora.get(Calendar.MONTH), dataHora.get(Calendar.DAY_OF_MONTH));

        TimePickerDialog horaDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                presenter.changeTimePicker(selectedHour, selectedMinute);
            }
        }, dataHora.get(Calendar.HOUR_OF_DAY), dataHora.get(Calendar.MINUTE), true);

        dataDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());

        //tData.setOnClickListener(openPicker(dataDialog));
        tHora.setOnClickListener(openPicker(horaDialog));
    }

    private View.OnClickListener openPicker(final AlertDialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        };
    }

    @Override
    public void updateData(String data) {
        tData.setText(data);
    }

    @Override
    public void updateHora(String hora) {
        tHora.setText(hora);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public interface Callback {
        void callback(Calendar dataHora, List<DiasSemana> diasSemana);
    }

}
