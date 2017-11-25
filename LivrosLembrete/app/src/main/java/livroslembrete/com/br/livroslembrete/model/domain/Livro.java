package livroslembrete.com.br.livroslembrete.model.domain;

import java.io.Serializable;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private Integer totalPaginas;
    private String urlImagem;
    private Usuario usuario;

    public Livro(String nome, Integer totalPaginas, Usuario usuario) {
        this.nome = nome;
        this.totalPaginas = totalPaginas;
        this.usuario = usuario;
    }

    public Livro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}