package livroslembrete.com.br.livroslembrete.view.fragments;

import android.content.Intent;
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
import android.widget.ProgressBar;

import java.util.List;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.presenter.LivroListaPresenter;
import livroslembrete.com.br.livroslembrete.view.LivroListaView;
import livroslembrete.com.br.livroslembrete.view.activitys.LivroFormActivity;
import livroslembrete.com.br.livroslembrete.view.adapters.LivroAdapter;
import livroslembrete.com.br.livroslembrete.domain.Livro;

public class LivroFragment extends Fragment implements LivroListaView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar progress, progressPag;
    private LivroListaPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livros, container, false);

        presenter = new LivroListaPresenter(this);

        progress = view.findViewById(R.id.progress);
        progressPag = view.findViewById(R.id.progressPag);
        progressPag.setVisibility(View.GONE);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(onScrollChangeListener(mLayoutManager));

        swipeLayout = view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        view.findViewById(R.id.fab).setOnClickListener(onClickFab());
        buscarLivros();

        return view;
    }

    private View.OnClickListener onClickFab() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LivroFormActivity.class));
            }
        };
    }

    private RecyclerView.OnScrollListener onScrollChangeListener(final LinearLayoutManager mLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                presenter.onScrolled(dy, mLayoutManager);
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        };
    }

    private void buscarLivros() {
        presenter.buscarLivros();
    }

    @Override
    public void setVisibilityProgress(int visibility) {
        progress.setVisibility(visibility);
    }

    @Override
    public void setVisibilityRecyclerView(int visibility) {
        recyclerView.setVisibility(visibility);
    }

    @Override
    public void setVisibilityProgressPag(int visibility) {
        progressPag.setVisibility(visibility);
    }

    @Override
    public void showSnack(String msg) {
        Snackbar.make(recyclerView, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
    }

    @Override
    public void setAdapter(List<Livro> livros) {
        recyclerView.setAdapter(new LivroAdapter(getContext(), livros, new LivroAdapter.LivrosOnClickListener() {
            @Override
            public void onClick(LivroAdapter.LivrosViewHolder holder, int idx) {
                presenter.onClickLivro(idx);
            }
        }));
    }

    @Override
    public void stopRefreshingSwipe() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void updateAdapter(List<Livro> livros) {
        LivroAdapter adapter = (LivroAdapter) recyclerView.getAdapter();
        adapter.updateList(livros);
    }
}
