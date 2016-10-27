package br.com.caelum.cadastro.support;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android6275 on 27/10/16.
 */

public class WebClient {

    public final MediaType tipo = MediaType.parse("application/json; charset=utf-8");
    public final String urlWebClient = "https://www.caelum.com.br/mobile"; //Sendo o servidor https é necessário informa-lo

    public String post(String json){

        try {

            RequestBody body =  RequestBody.create(tipo,json);
            Request req = new Request.Builder().url(urlWebClient).post(body).build();

            OkHttpClient ok = new OkHttpClient();
            Response rsp = ok.newCall(req).execute();

            return rsp.body().string();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }


}
