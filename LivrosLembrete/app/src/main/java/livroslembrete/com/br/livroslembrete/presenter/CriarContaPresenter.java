package livroslembrete.com.br.livroslembrete.presenter;

import livroslembrete.com.br.livroslembrete.model.CriarContaModel;
import livroslembrete.com.br.livroslembrete.model.domain.Usuario;
import livroslembrete.com.br.livroslembrete.model.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.view.BaseView;

public class CriarContaPresenter extends BasePresenter {
    private CriarContaModel model;
    private BaseView view;

    public CriarContaPresenter(BaseView view) {
        super(view);
        this.view = view;
        model = new CriarContaModel(this);
    }

    public void logar(String nome, String email, String senha) {
        if (nome.trim().length() == 0) {
            view.showToast("Digite um nome");
            return;
        }

        if (email.trim().length() == 0) {
            view.showToast("Digite um e-mail");
            return;
        }

        if (senha.trim().length() == 0) {
            view.showToast("Digite uma senha");
            return;
        }

        if (AndroidUtils.isNetworkAvailable(getContext())) {
            model.criarConta(new Usuario(nome, email, senha));
        } else {
            showAlert("Alerta", "Conexão com a internet indisponível!");
        }
    }
}
