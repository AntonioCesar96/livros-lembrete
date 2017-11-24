package livroslembrete.com.br.livroslembrete.tasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import java.io.File;
import livroslembrete.com.br.livroslembrete.utils.IOUtils;
import livroslembrete.com.br.livroslembrete.utils.SDCardUtils;

public class DownloadImagemTask extends AsyncTask<String, Void, File> {
    private AppCompatActivity activity;
    private CallbackDownloadImagem callbackDownloadImagem;

    public DownloadImagemTask(AppCompatActivity activity, CallbackDownloadImagem callbackDownloadImagem) {
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