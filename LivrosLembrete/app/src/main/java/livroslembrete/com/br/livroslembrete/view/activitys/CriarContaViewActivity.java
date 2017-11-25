package livroslembrete.com.br.livroslembrete.view.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.presenter.CriarContaPresenter;

public class CriarContaViewActivity extends BaseActivity {
    private TextView txtNome, txtSenha, txtEmail;
    private CriarContaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        presenter = new CriarContaPresenter(this);
    }

    public void criarConta(View view) {
        presenter.logar(txtNome.getText().toString(), txtEmail.getText().toString(), txtSenha.getText().toString());
    }
}
