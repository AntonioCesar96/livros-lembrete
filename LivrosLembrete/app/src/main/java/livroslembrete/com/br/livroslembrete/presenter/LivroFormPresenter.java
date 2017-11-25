package livroslembrete.com.br.livroslembrete.presenter;

import java.io.File;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.model.LivroFormModel;
import livroslembrete.com.br.livroslembrete.model.domain.Livro;
import livroslembrete.com.br.livroslembrete.model.domain.Usuario;
import livroslembrete.com.br.livroslembrete.model.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.view.LivroFormView;

public class LivroFormPresenter extends BasePresenter {
    private LivroFormModel model;
    private LivroFormView view;
    private Livro livro;
    private File fileImage;

    public LivroFormPresenter(LivroFormView view) {
        super(view);
        this.view = view;
        model = new LivroFormModel(this);
    }

    public void setFileImage(File fileImage) {
        this.fileImage = fileImage;
    }

    public void setLivro(Livro livro) {
        Usuario usuario = Application.getInstance().getUsuario();
        if (livro != null) {
            this.livro = livro;
            view.updateEditsTextAndImage(livro);
        } else {
            this.livro = new Livro();
        }
        this.livro.setUsuario(usuario);
    }

    public void save(String nome, String totalPaginas) {
        if (nome.trim().length() == 0) {
            view.showToast("Informe o nome do livro");
            return;
        }

        if (totalPaginas.trim().length() == 0) {
            view.showToast("Informe a quantidade de páginas do livro");
            return;
        }

        livro.setNome(nome);
        livro.setTotalPaginas(Integer.parseInt(totalPaginas));

        if (AndroidUtils.isNetworkAvailable(getContext())) {
            model.salvar(livro, fileImage);
        } else {
            showAlert("Alerta", "Conexão com a internet indisponível!");
        }
    }

    public void deleteImage() {
        if (fileImage != null && fileImage.exists()) {
            fileImage.delete();
        }
    }
}
