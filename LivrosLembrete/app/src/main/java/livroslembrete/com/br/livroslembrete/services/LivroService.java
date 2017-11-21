package livroslembrete.com.br.livroslembrete.services;

import java.util.ArrayList;
import java.util.List;

import livroslembrete.com.br.livroslembrete.models.Livro;

public class LivroService {

    public List<Livro> buscarLivros() {
        List<Livro> livros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            livros.add(new Livro("Nome " + i, i));
        }
        return livros;
    }
}
