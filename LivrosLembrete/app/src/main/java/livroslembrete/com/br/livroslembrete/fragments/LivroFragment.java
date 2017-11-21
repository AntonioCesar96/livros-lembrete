package livroslembrete.com.br.livroslembrete.fragments;


import android.content.Intent;
import android.os.AsyncTask;
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
import livroslembrete.com.br.livroslembrete.activitys.LivroDetalhesActivity;
import livroslembrete.com.br.livroslembrete.activitys.LivroFormActivity;
import livroslembrete.com.br.livroslembrete.adapters.LivroAdapter;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.services.LivroService;

public class LivroFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar progress;
    private List<Livro> livros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livro, container, false);
        progress = view.findViewById(R.id.progress);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeLayout = view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LivroFormActivity.class));
            }
        });

        buscarLivros();
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarLivros();
                swipeLayout.setRefreshing(false);
            }
        };
    }

    private void buscarLivros() {
        new LivrosTask().execute();
    }

    private class LivrosTask extends AsyncTask<Void, Void, List<Livro>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<Livro> doInBackground(Void... longs) {
            try {
                return new LivroService().buscarLivros();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Livro> livros) {
            progress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            if (livros != null) {
                LivroFragment.this.livros = livros;
                recyclerView.setAdapter(new LivroAdapter(getContext(), livros, onClickListener()));
                return;
            }

            snack(recyclerView, "Aconteceu algum erro ao tentar buscar os livros");
        }
    }

    private LivroAdapter.LivrosOnClickListener onClickListener() {
        return new LivroAdapter.LivrosOnClickListener() {
            @Override
            public void onClick(LivroAdapter.LivrosViewHolder holder, int idx) {
                Livro l = livros.get(idx);

                Intent intent = new Intent(getContext(), LivroDetalhesActivity.class);
                startActivity(intent);
            }
        };
    }

    protected void snack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
    }
}
