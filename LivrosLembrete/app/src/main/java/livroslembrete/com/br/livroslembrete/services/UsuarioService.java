package livroslembrete.com.br.livroslembrete.services;

import com.google.gson.Gson;

import java.io.IOException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.utils.HttpHelper;

public class UsuarioService {
    private String url;

    public UsuarioService() {
        url = Application.getURL() + "usuarios";
    }

    public Usuario logar(String email, String senha) throws IOException {
        String json = new HttpHelper().doGet(url + "/login?email=" + email + "&senha=" + senha);
        Gson gson = new Gson();
        return gson.fromJson(json, Usuario.class);
    }

    public Usuario save(Usuario usuario) throws Exception {
        String json = new Gson().toJson(usuario);

        HttpHelper helper = new HttpHelper();
        helper.setContentType("application/json");
        String retorno = helper.doPost(url, json.getBytes(), "UTF-8");

        Gson gson = new Gson();
        return gson.fromJson(retorno, Usuario.class);
    }
}
