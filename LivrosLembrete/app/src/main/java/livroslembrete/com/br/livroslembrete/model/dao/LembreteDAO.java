package livroslembrete.com.br.livroslembrete.model.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.model.domain.Lembrete;

public class LembreteDAO extends BaseDaoImpl<Lembrete, Long> {
    private ConnectionSource cs;

    public LembreteDAO(ConnectionSource cs) throws SQLException {
        super(Lembrete.class);
        this.cs = cs;
        setConnectionSource(cs);
        initialize();
    }

    public void save(Lembrete lembrete) {
        try {
            if (idExists(lembrete.getIdLivro())) {
                update(lembrete);
            } else {
                create(lembrete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lembrete getById(Long idLivro) {
        try {
            List<Lembrete> lembretes = queryForEq("idLivro", idLivro);
            if (lembretes != null && lembretes.size() > 0) {
                return lembretes.get(0);
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public void deletar(Long idLivro) {
        try {
            DeleteBuilder<Lembrete, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("idLivro", idLivro);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}