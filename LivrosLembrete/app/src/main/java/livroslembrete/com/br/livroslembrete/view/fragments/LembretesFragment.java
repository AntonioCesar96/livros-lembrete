package livroslembrete.com.br.livroslembrete.view.fragments;

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
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.presenter.LembretesListaPresenter;
import livroslembrete.com.br.livroslembrete.view.LembretesView;
import livroslembrete.com.br.livroslembrete.view.adapters.LembreteAdapter;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.utils.AlarmLembreteUtil;
import livroslembrete.com.br.livroslembrete.view.adapters.LivroAdapter;

public class LembretesFragment extends Fragment implements LembretesView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private LembretesListaPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lembretes, container, false);

        presenter = new LembretesListaPresenter(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeLayout = view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        presenter.buscarLembretes();
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.buscarLembretes();
                swipeLayout.setRefreshing(false);
            }
        };
    }

    @Override
    public void showSnack(String msg) {
        Snackbar.make(recyclerView, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
    }

    @Override
    public void setAdapter(List<Lembrete> lembretes) {
        recyclerView.setAdapter(new LembreteAdapter(getContext(), lembretes, new LembreteAdapter.OnCheckedChangeListener() {
            @Override
            public void onChange(boolean isChecked, int idx) {
                presenter.onChange(isChecked, idx);
            }
        }));
    }

    @Override
    public void updateAdapter(List<Lembrete> lembretes) {
        LembreteAdapter adapter = (LembreteAdapter) recyclerView.getAdapter();
        adapter.updateList(lembretes);
    }
}
