package livroslembrete.com.br.livroslembrete.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.domain.Usuario;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String dataBaseName = "livroslembrete.db";
    private static final int dataBaseVersion = 1;

    public DataBaseHelper(Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sd, ConnectionSource cs) {
        try {
            TableUtils.createTable(cs, Usuario.class);
            TableUtils.createTable(cs, Lembrete.class);
            TableUtils.createTable(cs, DiasSemana.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sd, ConnectionSource cs, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(cs, Usuario.class, true);
            TableUtils.dropTable(cs, Lembrete.class, true);
            TableUtils.dropTable(cs, DiasSemana.class, true);

            onCreate(sd, cs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        super.close();
    }
}