package livroslembrete.com.br.livroslembrete.model;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;
import livroslembrete.com.br.livroslembrete.model.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.model.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.model.domain.Usuario;
import livroslembrete.com.br.livroslembrete.presenter.CriarContaPresenter;
import livroslembrete.com.br.livroslembrete.model.rest.UsuarioService;

public class CriarContaModel {
    private CriarContaPresenter presenter;

    public CriarContaModel(CriarContaPresenter presenter) {
        this.presenter = presenter;
    }

    public void criarConta(Usuario usuario) {
        new UsuarioTask().execute(usuario);
    }

    private class UsuarioTask extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            presenter.showProgressDialog("Cadastrando", "Realizando cadastro, aguarde...");
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
                presenter.showAlert("Alerta", "Aconteceu um erro ao tentar cadastrar o usuário!");
            }
        }
    }
}
