package com.antonio.livroslembreteapi.services;

import java.io.IOException;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.antonio.livroslembreteapi.util.HttpHelper;

@Component
public class UploadService {
	public static String client_id = "6d52de9f810ab22";

	public String upload(String base64) throws Exception {
		if (base64 == null) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}

		String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(base64, "UTF-8");

		HttpHelper http = new HttpHelper();
		String retorno;
		JSONObject object;
		JSONObject dataJson;
		String tipoAutorizacao = "Client-ID " + client_id;

		try {
			retorno = http.uploadImage(data, tipoAutorizacao);
			object = new JSONObject(retorno);
			dataJson = object.getJSONObject("data");

			if (!object.getBoolean("success")) {
				JSONObject error = dataJson.getJSONObject("error");
				throw new RuntimeException(error.getString("message"));
			}

			return dataJson.getString("link");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
