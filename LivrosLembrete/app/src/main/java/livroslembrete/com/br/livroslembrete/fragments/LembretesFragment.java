package livroslembrete.com.br.livroslembrete.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.adapters.LembreteAdapter;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.models.Lembrete;
import livroslembrete.com.br.livroslembrete.utils.AlarmLembreteUtil;

public class LembretesFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private List<Lembrete> lembretes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lembretes, container, false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeLayout = view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        buscarLembretes();
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarLembretes();
                swipeLayout.setRefreshing(false);
            }
        };
    }

    private void buscarLembretes() {
        try {
            DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
            LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
            lembretes = dao.queryForAll();
            recyclerView.setAdapter(new LembreteAdapter(getContext(), lembretes, onClickListener()));
            if (lembretes.size() == 0) {
                snack(recyclerView, "Não há lembretes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LembreteAdapter.OnCheckedChangeListener onClickListener() {

        return new LembreteAdapter.OnCheckedChangeListener() {
            @Override
            public void onChange(boolean isChecked, int idx) {
                Lembrete lembrete = lembretes.get(idx);

                try {
                    DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
                    LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
                    AlarmLembreteUtil alarmUtil = new AlarmLembreteUtil(getActivity());

                    if (isChecked) {
                        dao.deletar(lembrete.getIdLivro());
                        alarmUtil.cancelarAlarm(lembrete.getIdLivro());
                        return;
                    }

                    dao.save(lembrete);
                    alarmUtil.agendarAlarm(lembrete.getIdLivro(), lembrete.getDataHora().getTimeInMillis());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void snack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
    }
}
