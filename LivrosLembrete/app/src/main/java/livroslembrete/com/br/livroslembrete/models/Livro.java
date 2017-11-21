package livroslembrete.com.br.livroslembrete.models;

public class Livro {
    private String nome;
    private Integer totalPaginas;
    private String imagemBase64;

    public Livro() {
    }

    public Livro(String nome, Integer totalPaginas) {
        this.nome = nome;
        this.totalPaginas = totalPaginas;
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

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }
}
