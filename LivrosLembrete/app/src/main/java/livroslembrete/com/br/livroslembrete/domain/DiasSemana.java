package livroslembrete.com.br.livroslembrete.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "diassemana")
public class DiasSemana {

    @DatabaseField(columnName = "id", generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    @DatabaseField
    private int diaSemana;

    @DatabaseField
    private boolean isDiaSelecionado;

    @DatabaseField
    private boolean isNotificado;

    @DatabaseField
    private Long idLembrete;

    public DiasSemana(int diaSemana, boolean isDiaSelecionado, boolean isNotificado) {
        this.diaSemana = diaSemana;
        this.isDiaSelecionado = isDiaSelecionado;
        this.isNotificado = isNotificado;
    }

    public DiasSemana() {
    }

    public int  getId() {
        return id;
    }

    public void setId(int  id) {
        this.id = id;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean isDiaSelecionado() {
        return isDiaSelecionado;
    }

    public void setDiaSelecionado(boolean diaSelecionado) {
        isDiaSelecionado = diaSelecionado;
    }

    public boolean isNotificado() {
        return isNotificado;
    }

    public void setNotificado(boolean notificado) {
        isNotificado = notificado;
    }

    public Long getIdLembrete() {
        return idLembrete;
    }

    public void setIdLembrete(Long idLembrete) {
        this.idLembrete = idLembrete;
    }
}
