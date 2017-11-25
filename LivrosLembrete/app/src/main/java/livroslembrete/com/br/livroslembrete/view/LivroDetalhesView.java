package livroslembrete.com.br.livroslembrete.view;

import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.domain.Livro;

public interface LivroDetalhesView extends BaseView{
    void initView(Livro livro, Lembrete lembrete);
}
