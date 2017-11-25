package livroslembrete.com.br.livroslembrete.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;
import livroslembrete.com.br.livroslembrete.model.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.model.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.model.domain.Usuario;
import livroslembrete.com.br.livroslembrete.presenter.LoginPresenter;
import livroslembrete.com.br.livroslembrete.model.rest.UsuarioService;

public class LoginModel {
    private LoginPresenter presenter;

    public LoginModel(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    public void logarAutomaticamente() {
        DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
        try {
            Usuario usuario = new UsuarioDAO(dataBaseHelper.getConnectionSource()).getUsuario();
            if (usuario != null) {
                Application.getInstance().setUsuario(usuario);
                presenter.openActivity(MainActivity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logar(String email, String senha) {
        new LoginTask().execute(email, senha);
    }

    private class LoginTask extends AsyncTask<String, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            presenter.showProgressDialog("Alerta", "Realizando login, aguarde...");
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
            presenter.closeProgressDialog();

            if (usuario != null && usuario.getId() != null) {
                try {
                    DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
                    UsuarioDAO dao = new UsuarioDAO(dataBaseHelper.getConnectionSource());
                    dao.deletar();
                    dao.save(usuario);

                    Application.getInstance().setUsuario(usuario);
                    presenter.openActivity(MainActivity.class);
                } catch (SQLException e) {
                    Log.i("Error", e.getMessage());
                }
            } else {
                presenter.showAlert("Alerta", "Usuário não encontrado!");
            }
        }
    }
}
