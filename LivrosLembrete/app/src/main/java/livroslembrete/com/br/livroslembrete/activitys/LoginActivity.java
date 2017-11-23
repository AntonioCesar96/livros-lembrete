package livroslembrete.com.br.livroslembrete.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.services.UsuarioService;
import livroslembrete.com.br.livroslembrete.utils.AlertUtils;
import livroslembrete.com.br.livroslembrete.utils.AndroidUtils;

public class LoginActivity extends AppCompatActivity {
    private TextView txtSenha, txtEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
        try {
            Usuario usuario = new UsuarioDAO(dataBaseHelper.getConnectionSource()).getUsuario();
            if (usuario != null) {
                Application.getInstance().setUsuario(usuario);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();

                //txtEmail.setText(usuario.getEmail());
                //txtSenha.setText(usuario.getSenha());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logar(View view) {
        if (txtEmail.getText().toString().trim().length() == 0) {
            txtEmail.setError("Digite um e-mail");
            return;
        }

        if (txtSenha.getText().toString().trim().length() == 0) {
            txtSenha.setError("Digite uma senha");
            return;
        }

        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        if (AndroidUtils.isNetworkAvailable(getApplicationContext())) {
            new UsuarioTask().execute(email, senha);
        } else {
            AlertUtils.alert(LoginActivity.this, "Alerta", "Conexão com a internet indisponível!");
        }
    }

    public void criarConta(View view) {
        startActivity(new Intent(this, CriarContaActivity.class));
        finish();
    }

    private class UsuarioTask extends AsyncTask<String, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Alerta", "Realizando login, aguarde...", false, false);
        }

        @Override
        protected Usuario doInBackground(String... strings) {
            try {
                String email = strings[0];
                String senha = strings[1];

                Usuario usuario = new UsuarioService().logar(email, senha);
                if (usuario != null) {
                    usuario.setSenha(senha);
                }
                return usuario;
            } catch (IOException e) {
                Log.e("ERRO", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            progressDialog.dismiss();

            if (usuario != null && usuario.getId() != null) {
                try {
                    DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
                    UsuarioDAO dao = new UsuarioDAO(dataBaseHelper.getConnectionSource());
                    dao.deletar();
                    dao.save(usuario);

                    Application.getInstance().setUsuario(usuario);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } catch (SQLException e) {
                    Log.i("Error", e.getMessage());
                }
            } else {
                AlertUtils.alert(LoginActivity.this, "Alerta", "Usuário não encontrado!");
            }
        }
    }
}
