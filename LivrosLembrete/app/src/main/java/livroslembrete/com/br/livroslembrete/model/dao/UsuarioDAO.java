package livroslembrete.com.br.livroslembrete.model.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.model.domain.Usuario;

public class UsuarioDAO extends BaseDaoImpl<Usuario, Long> {
    private ConnectionSource cs;

    public UsuarioDAO(ConnectionSource cs) throws SQLException {
        super(Usuario.class);
        this.cs = cs;
        setConnectionSource(cs);
        initialize();
    }

    public void save(Usuario usuario) {
        try {
            if (idExists(usuario.getId())) {
                update(usuario);
            } else {
                create(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario() {
        try {
            List<Usuario> usuarios = queryForAll();
            if (usuarios != null && usuarios.size() != 0) {
                return usuarios.get(0);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletar() {
        try {
            DeleteBuilder<Usuario, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}