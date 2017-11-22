package com.antonio.livroslembreteapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
	public final int TIMEOUT_MILLIS = 15000;
	public static final String URL_UPLOAD = "https://api.imgur.com/3/image";

	public String uploadImage(String data, String tipoAutorizacao) throws IOException {

		URL u = new URL(URL_UPLOAD);
		HttpURLConnection conn = null;
		String s = null;
		try {
			conn = (HttpURLConnection) u.openConnection();

			conn.setRequestMethod("POST");
			conn.setConnectTimeout(TIMEOUT_MILLIS);
			conn.setReadTimeout(TIMEOUT_MILLIS);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Authorization", tipoAutorizacao);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.connect();

			if (data != null) {
				OutputStream out = conn.getOutputStream();
				OutputStreamWriter wr = new OutputStreamWriter(out);
				wr.write(data);
				wr.flush();
				out.close();
			}

			InputStream in = null;
			int status = conn.getResponseCode();
			if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
				in = conn.getErrorStream();
			} else {
				in = conn.getInputStream();
			}
			s = IOUtils.toString(in, "UTF-8");

			in.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return s;
	}
}
