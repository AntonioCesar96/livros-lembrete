package livroslembrete.com.br.livroslembrete.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;

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

            for (DiasSemana d : lembrete.getDiasSemana()) {
                d.setIdLembrete(lembrete.getIdLivro());
                new DiasSemanaDAO(cs).save(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lembrete getById(Long idLivro) {
        try {
            List<Lembrete> lembretes = queryForEq("idLivro", idLivro);
            if (lembretes != null && lembretes.size() > 0) {
                List<DiasSemana> list = new DiasSemanaDAO(cs).queryForAll(lembretes.get(0).getIdLivro());
                lembretes.get(0).setDiasSemana(list);
                return lembretes.get(0);
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public void deletar(Long idLivro) {
        try {
            for (DiasSemana d : getById(idLivro).getDiasSemana()) {
                new DiasSemanaDAO(cs).deletar(idLivro);
            }

            DeleteBuilder<Lembrete, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("idLivro", idLivro);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}