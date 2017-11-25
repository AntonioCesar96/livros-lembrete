package livroslembrete.com.br.livroslembrete.model;

import android.os.AsyncTask;
import android.view.View;

import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.presenter.LivroListaPresenter;
import livroslembrete.com.br.livroslembrete.rest.LivroService;
import livroslembrete.com.br.livroslembrete.view.adapters.LivroAdapter;

public class LivroListaModel {
    private LivroListaPresenter presenter;

    public LivroListaModel(LivroListaPresenter presenter) {
        this.presenter = presenter;
    }

    public void buscarLivros() {
        new LivrosTask().execute();
    }

    private class LivrosTask extends AsyncTask<Void, Void, List<Livro>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            presenter.carregando = true;

            if (presenter.page == 0) {
                presenter.setVisibilityProgress(View.VISIBLE);
                presenter.setVisibilityRecyclerView(View.INVISIBLE);
                return;
            }

            presenter.setVisibilityProgressPag(View.VISIBLE);
        }

        @Override
        protected List<Livro> doInBackground(Void... longs) {
            try {
                Long usuario = Application.getInstance().getUsuario().getId();
                return new LivroService().buscarLivros(presenter.page, presenter.max, usuario);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Livro> livros) {
            presenter.carregando = false;

            if (livros != null) {
                if (presenter.page == 0) {
                    presenter.setVisibilityProgress(View.GONE);
                    presenter.setVisibilityRecyclerView(View.VISIBLE);
                    presenter.setAdapter(livros);
                    return;
                }

                if (livros.size() != presenter.max) {
                    presenter.carregarMais = false;
                }

                presenter.updateAdapter(livros);
                presenter.setVisibilityProgressPag(View.GONE);
                return;
            }


            presenter.setVisibilityProgress(View.GONE);
            presenter.setVisibilityProgressPag(View.GONE);
            presenter.setVisibilityRecyclerView(View.VISIBLE);

            presenter.showSnack( "Aconteceu algum erro ao tentar buscar os livros");
        }
    }
}
