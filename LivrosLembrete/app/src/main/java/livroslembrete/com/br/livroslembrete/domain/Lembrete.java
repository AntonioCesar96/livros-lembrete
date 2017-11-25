package livroslembrete.com.br.livroslembrete.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Calendar;

@DatabaseTable(tableName = "lembrete")
public class Lembrete implements Serializable {
    private static final long serialVersionUID = 1L;

    @DatabaseField(id = true)
    private Long idLivro;

    @DatabaseField
    private String nomeLivro;

    @DatabaseField
    private Integer totalPaginasLivro;

    @DatabaseField
    private String urlImagemLivro;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Calendar dataHora;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private String[] diasSemana;

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public Integer getTotalPaginasLivro() {
        return totalPaginasLivro;
    }

    public void setTotalPaginasLivro(Integer totalPaginasLivro) {
        this.totalPaginasLivro = totalPaginasLivro;
    }

    public String getUrlImagemLivro() {
        return urlImagemLivro;
    }

    public void setUrlImagemLivro(String urlImagemLivro) {
        this.urlImagemLivro = urlImagemLivro;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }

    public String[] getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String[] diasSemana) {
        this.diasSemana = diasSemana;
    }
}
