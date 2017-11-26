package livroslembrete.com.br.livroslembrete.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;

public class DiasSemanaDAO extends BaseDaoImpl<DiasSemana, Integer> {
    private ConnectionSource cs;

    public DiasSemanaDAO(ConnectionSource cs) throws SQLException {
        super(DiasSemana.class);
        this.cs = cs;
        setConnectionSource(cs);
        initialize();
    }

    public void save(DiasSemana diasSemana) {
        try {
            if (diasSemana.getId() != 0 && idExists(diasSemana.getId())) {
                update(diasSemana);
            } else {
                create(diasSemana);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DiasSemana> queryForAll(Long idLembrete) {
        try {
            QueryBuilder<DiasSemana, Integer> builder = queryBuilder();
            builder.where().eq("idLembrete", idLembrete.intValue());
            return query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletar(Long idLembrete) {
        try {
            DeleteBuilder<DiasSemana, Integer> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("idLembrete", idLembrete.intValue());
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}