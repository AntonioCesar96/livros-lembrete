package livroslembrete.com.br.livroslembrete.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity {
    private TextView txtSenha, txtEmail;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        presenter = new LoginPresenter(this);
        presenter.logarAutomaticamente();
    }

    public void logar(View view) {
        presenter.logar(txtEmail.getText().toString(), txtSenha.getText().toString());
    }

    public void criarConta(View view) {
        startActivity(new Intent(LoginActivity.this, CriarContaViewActivity.class));
    }
}
