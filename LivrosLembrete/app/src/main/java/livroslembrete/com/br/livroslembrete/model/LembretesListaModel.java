package livroslembrete.com.br.livroslembrete.model;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.domain.Usuario;
import livroslembrete.com.br.livroslembrete.presenter.CriarContaPresenter;
import livroslembrete.com.br.livroslembrete.presenter.LembretesListaPresenter;
import livroslembrete.com.br.livroslembrete.rest.UsuarioService;
import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;

public class LembretesListaModel {
    private LembretesListaPresenter presenter;

    public LembretesListaModel(LembretesListaPresenter presenter) {
        this.presenter = presenter;
    }

    public List<Lembrete> buscarLembretess() {
        try {
            DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
            LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(Lembrete lembrete) {
        try {
            DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
            LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
            dao.save(lembrete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(Long idLivro) {
        try {
            DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
            LembreteDAO dao = new LembreteDAO(dataBaseHelper.getConnectionSource());
            dao.deletar(idLivro);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
