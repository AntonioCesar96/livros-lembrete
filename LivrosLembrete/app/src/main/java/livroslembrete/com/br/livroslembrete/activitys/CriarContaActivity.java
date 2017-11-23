package livroslembrete.com.br.livroslembrete.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.services.UsuarioService;
import livroslembrete.com.br.livroslembrete.utils.AlertUtils;
import livroslembrete.com.br.livroslembrete.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;

public class CriarContaActivity extends AppCompatActivity {
    private TextView txtNome, txtSenha, txtEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }

    public void criarConta(View view) {
        if (txtNome.getText().toString().trim().length() == 0) {
            txtNome.setError("Digite um nome");
            return;
        }

        if (txtEmail.getText().toString().trim().length() == 0) {
            txtEmail.setError("Digite um e-mail");
            return;
        }

        if (txtSenha.getText().toString().trim().length() == 0) {
            txtSenha.setError("Digite uma senha");
            return;
        }

        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        Usuario usuario = new Usuario(nome, email, senha);

        if (AndroidUtils.isNetworkAvailable(getApplicationContext())) {
            new UsuarioTask().execute(usuario);
        } else {
            AlertUtils.alert(CriarContaActivity.this, "Alerta", "Conexão com a internet indisponível!");
        }
    }

    private class UsuarioTask extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CriarContaActivity.this, "Cadastrando", "Realizando cadastro, aguarde...", false, false);
        }

        @Override
        protected Usuario doInBackground(Usuario... usuarios) {
            try {
                Usuario usuarioRetorno = new UsuarioService().save(usuarios[0]);
                usuarioRetorno.setSenha(usuarios[0].getSenha());
                return usuarioRetorno;
            } catch (Exception e) {
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
                    startActivity(new Intent(CriarContaActivity.this, MainActivity.class));
                    finish();
                } catch (SQLException e) {
                    Log.i("Error", e.getMessage());
                }
            } else {
                AlertUtils.alert(CriarContaActivity.this, "Alerta", "Aconteceu um erro ao tentar cadastrar o usuário!");
            }
        }
    }
}
