package livroslembrete.com.br.livroslembrete.services;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.models.ResponseWithURL;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.utils.HttpHelper;
import livroslembrete.com.br.livroslembrete.utils.IOUtils;

public class LivroService {

    private String url;

    public LivroService() {
        url = Application.getURL() + "livros";
    }

    public List<Livro> buscarLivros(int page, int max, Long usuario) throws IOException {
        String json = new HttpHelper().doGet(url + "?page=" + page + "&size=" + max + "&usuario=" + usuario);

        Type listType = new TypeToken<ArrayList<Livro>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

    public Livro save(Livro livro) throws Exception {
        String json = new Gson().toJson(livro);

        HttpHelper helper = new HttpHelper();
        helper.setContentType("application/json");
        String retorno = helper.doPost(url, json.getBytes(), "UTF-8");

        Gson gson = new Gson();
        return gson.fromJson(retorno, Livro.class);
    }

    public ResponseWithURL postFotoBase64(File file) throws IOException {
        String urlUpload = url + "/imagem-base64";

        byte[] bytes = IOUtils.toBytes(new FileInputStream(file));
        String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);

        Map<String, String> params = new HashMap<>();
        params.put("fileName", file.getName());
        params.put("base64", base64);

        HttpHelper http = new HttpHelper();
        http.setContentType("application/x-www-form-urlencoded");
        http.setCharsetToEncode("UTF-8");
        String json = http.doPost(urlUpload, params, "UTF-8");

        return new Gson().fromJson(json, ResponseWithURL.class);
    }
}
