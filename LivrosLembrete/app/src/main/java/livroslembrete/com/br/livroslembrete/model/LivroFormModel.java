package livroslembrete.com.br.livroslembrete.model;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

import livroslembrete.com.br.livroslembrete.presenter.LivroDetalhesPresenter;
import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;
import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.domain.RespostaUploadImagem;
import livroslembrete.com.br.livroslembrete.presenter.LivroFormPresenter;
import livroslembrete.com.br.livroslembrete.rest.LivroService;

public class LivroFormModel {
    private LivroFormPresenter presenter;

    public LivroFormModel(LivroFormPresenter presenter) {
        this.presenter = presenter;
    }

    public void salvar(Livro livro, File fileImage) {
        new LivroTask(livro, fileImage).execute();
    }

    private class LivroTask extends AsyncTask<Void, Void, Livro> {
        private Livro livro;
        private File fileImage;

        public LivroTask(Livro livro, File fileImage) {
            this.livro = livro;
            this.fileImage = fileImage;
        }

        @Override
        protected void onPreExecute() {
            if (livro.getId() != null) {
                presenter.showProgressDialog("Atualizando", "Realizando a atualização dos dados do livro, aguarde...");
            } else {
                presenter.showProgressDialog("Cadastrando", "Realizando o cadastro do livro, aguarde...");
            }
        }

        @Override
        protected Livro doInBackground(Void... voids) {
            LivroService service = new LivroService();

            try {
                if (fileImage != null && fileImage.exists()) {
                    RespostaUploadImagem respostaUploadImagem = service.postFotoBase64(fileImage);
                    if (respostaUploadImagem != null && respostaUploadImagem.isOk()) {
                        livro.setUrlImagem(respostaUploadImagem.getUrl());
                    }
                }
                return service.save(livro);
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Livro livroUpdate) {
            presenter.closeProgressDialog();
            if (livroUpdate != null && livroUpdate.getId() != null) {
                if (livro.getId() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("livro", livroUpdate);
                    presenter.setResultActivity(LivroDetalhesPresenter.ATUALIZAR_DADOS, intent);
                } else {
                    presenter.openActivity(MainActivity.class);
                }
            } else {
                presenter.showProgressDialog("Alerta", "Aconteceu algum erro desconhecido!");
            }
        }
    }
}
