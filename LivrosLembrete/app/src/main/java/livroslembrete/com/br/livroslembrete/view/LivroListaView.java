package livroslembrete.com.br.livroslembrete.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import livroslembrete.com.br.livroslembrete.domain.Livro;

public interface LivroListaView {

    void setVisibilityProgress(int visibility);
    void setVisibilityRecyclerView(int visibility);
    void setVisibilityProgressPag(int visibility);
    void showSnack(String msg);
    FragmentActivity getActivity();
    void stopRefreshingSwipe();
    void setAdapter(List<Livro> livros);
    void updateAdapter(List<Livro> livros);
    void startActivityForResult(Intent intent, int recriarActivity);
}
