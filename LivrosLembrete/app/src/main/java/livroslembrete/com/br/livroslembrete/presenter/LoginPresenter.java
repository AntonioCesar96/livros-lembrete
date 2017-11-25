package livroslembrete.com.br.livroslembrete.presenter;

import livroslembrete.com.br.livroslembrete.model.LoginModel;
import livroslembrete.com.br.livroslembrete.model.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.view.BaseView;

public class LoginPresenter extends BasePresenter {
    private LoginModel model;
    private BaseView view;

    public LoginPresenter(BaseView view) {
        super(view);
        this.view = view;
        model = new LoginModel(this);
    }

    public void logar(String email, String senha) {
        if (email.trim().length() == 0) {
            view.showToast("Digite um e-mail");
            return;
        }

        if (senha.trim().length() == 0) {
            view.showToast("Digite uma senha");
            return;
        }

        if (AndroidUtils.isNetworkAvailable(getContext())) {
            model.logar(email, senha);
        } else {
            showAlert("Alerta", "Conexão com a internet indisponível!");
        }
    }

    public void logarAutomaticamente() {
        model.logarAutomaticamente();
    }
}
