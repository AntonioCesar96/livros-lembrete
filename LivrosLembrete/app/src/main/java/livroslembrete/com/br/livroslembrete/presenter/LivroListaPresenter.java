package livroslembrete.com.br.livroslembrete.presenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.model.LivroListaModel;
import livroslembrete.com.br.livroslembrete.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.view.LivroListaView;
import livroslembrete.com.br.livroslembrete.view.activitys.LivroDetalhesActivity;
import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;

public class LivroListaPresenter {
    private LivroListaModel model;
    private LivroListaView view;
    private List<Livro> livros;
    public int page = 0;
    public int max = 5;
    public boolean carregando = false;
    public boolean carregarMais = true;

    public LivroListaPresenter(LivroListaView view) {
        this.view = view;
        model = new LivroListaModel(this);
    }

    public List<Livro> getLivros() {
        return livros;
    }


    public void setVisibilityProgress(int visibility) {
        view.setVisibilityProgress(visibility);
    }

    public void setVisibilityRecyclerView(int visibility) {
        view.setVisibilityRecyclerView(visibility);
    }

    public void setVisibilityProgressPag(int visibility) {
        view.setVisibilityProgressPag(visibility);
    }

    public void setAdapter(List<Livro> livros) {
        this.livros = livros;
        view.setAdapter(livros);
        if (livros.size() == 0) {
            showSnack("Não há livros");
        }
    }

    public void updateAdapter(List<Livro> livros) {
        view.updateAdapter(livros);
    }

    public void showSnack(String s) {
        view.showSnack(s);
    }

    public void onClickLivro(int idx) {
        Livro l = livros.get(idx);
        Intent intent = new Intent(view.getActivity(), LivroDetalhesActivity.class);
        intent.putExtra("livro", l);
        view.startActivityForResult(intent, MainActivity.RECRIAR_ACTIVITY);
    }

    public void buscarLivros() {
        model.buscarLivros();
    }

    public void onScrolled(int dy, LinearLayoutManager mLayoutManager) {
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

    public void onRefresh() {
        if (AndroidUtils.isNetworkAvailable(view.getActivity())) {
            page = 0;
            carregarMais = true;
            buscarLivros();
            view.stopRefreshingSwipe();
        } else {
            view.stopRefreshingSwipe();
            showSnack("Conexão indisponível");
        }
    }
}
