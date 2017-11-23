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

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.activitys.LivroDetalhesActivity;
import livroslembrete.com.br.livroslembrete.activitys.LivroFormActivity;
import livroslembrete.com.br.livroslembrete.adapters.LivroAdapter;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.services.LivroService;
import livroslembrete.com.br.livroslembrete.utils.AndroidUtils;

public class LivroFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private List<Livro> livros;
    private ProgressBar progress, progressPag;
    private int page = 0;
    private int max = 5;
    private boolean carregando = false;
    private boolean carregarMais = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livros, container, false);

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

    private RecyclerView.OnScrollListener onScrollChangeListener(final LinearLayoutManager mLayoutManager) {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && carregarMais) {
                    int totalItemCount = mLayoutManager.getItemCount();
                    int lastVisiblesItems = mLayoutManager.findLastVisibleItemPosition();

                    if (totalItemCount > 0) {
                        totalItemCount -= 1;
                    }

                    if (!carregando && lastVisiblesItems == totalItemCount) {
                        page++;
                        buscarLivros();
                    }
                }
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    page = 0;
                    carregarMais = true;
                    buscarLivros();
                    swipeLayout.setRefreshing(false);
                } else {
                    swipeLayout.setRefreshing(false);
                    snack(recyclerView, "Conexão indisponível");
                }
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
            carregando = true;

            if (page == 0) {
                progress.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                return;
            }

            progressPag.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Livro> doInBackground(Void... longs) {
            try {
                Long usuario = Application.getInstance().getUsuario().getId();
                return new LivroService().buscarLivros(page, max, usuario);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Livro> livros) {
            carregando = false;

            if (livros != null) {
                if (page == 0) {
                    progress.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    LivroFragment.this.livros = livros;
                    recyclerView.setAdapter(new LivroAdapter(getContext(), livros, onClickListener()));
                    return;
                }

                if (livros.size() != max) {
                    carregarMais = false;
                }

                LivroAdapter adapter = (LivroAdapter) recyclerView.getAdapter();
                adapter.updateList(livros);
                progressPag.setVisibility(View.GONE);
                return;
            }

            progress.setVisibility(View.GONE);
            progressPag.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            snack(recyclerView, "Aconteceu algum erro ao tentar buscar os livros");
        }
    }

    private LivroAdapter.LivrosOnClickListener onClickListener() {
        return new LivroAdapter.LivrosOnClickListener() {
            @Override
            public void onClick(LivroAdapter.LivrosViewHolder holder, int idx) {
                Livro l = livros.get(idx);
                Intent intent = new Intent(getContext(), LivroDetalhesActivity.class);
                intent.putExtra("livro", l);
                startActivity(intent);
            }
        };
    }

    protected void snack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
    }
}
