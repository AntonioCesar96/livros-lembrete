package livroslembrete.com.br.livroslembrete.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import livroslembrete.com.br.livroslembrete.view.activitys.MainActivity;
import livroslembrete.com.br.livroslembrete.presenter.LivroDetalhesPresenter;
import livroslembrete.com.br.livroslembrete.rest.LivroService;
import livroslembrete.com.br.livroslembrete.utils.IOUtils;
import livroslembrete.com.br.livroslembrete.utils.SDCardUtils;

public class LivroDetalhesModel {
    private LivroDetalhesPresenter presenter;

    public LivroDetalhesModel(LivroDetalhesPresenter presenter) {
        this.presenter = presenter;
    }

    public void deletarLivro(Long id) {
        new LivroExcluirTask().execute(id);
    }

    private class LivroExcluirTask extends AsyncTask<Long, Void, Long> {

        @Override
        protected void onPreExecute() {
            presenter.showProgressDialog("Excluindo", "Excluindo livro, aguarde...");
        }

        @Override
        protected Long doInBackground(Long... longs) {
            try {
                new LivroService().delete(longs[0]);
                return 1L;
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage(), e);
                return 0L;
            }
        }

        @Override
        protected void onPostExecute(Long cod) {
            if (cod == 0) {
                presenter.showProgressDialog("Alerta", "Aconteceu um erro ao tentar excluir o livro!");
            } else {
                presenter.closeProgressDialog();
                presenter.setResultActivity(MainActivity.RECRIAR_ACTIVITY);
            }
        }
    }

    public static class DownloadImagemTask extends AsyncTask<String, Void, File> {
        private Context activity;
        private CallbackDownloadImagem callbackDownloadImagem;

        public DownloadImagemTask(Context activity, CallbackDownloadImagem callbackDownloadImagem) {
            this.activity = activity;
            this.callbackDownloadImagem = callbackDownloadImagem;
        }

        @Override
        protected File doInBackground(String... urls) {
            String url = urls[0];
            File file = SDCardUtils.getPrivateFile(activity, "image_temp.jpg", Environment.DIRECTORY_PICTURES);
            IOUtils.downloadToFile(url, file);
            return file;
        }

        @Override
        protected void onPostExecute(File imagem) {
            callbackDownloadImagem.onCallbackDownloadImagem(imagem);
        }

        public interface CallbackDownloadImagem {
            void onCallbackDownloadImagem(File imagem);
        }
    }
}
