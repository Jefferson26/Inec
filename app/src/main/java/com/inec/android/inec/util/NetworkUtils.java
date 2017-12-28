package com.inec.android.inec.util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


//Classe para realizar a comunicacao com a API
public class NetworkUtils {

    private final static String GITHUB_BASE_URL = "https://api.github.com/users/";

    /**
     * Constroi a URL usada para realizar requisicoes a API do GitHub.
     * @param githubUser O nome da conta de usuario do GitHub que será consultada.
     */
    public static URL buildUrl(String githubUser) {
        if(githubUser != null) {
            String url_user = GITHUB_BASE_URL + githubUser;
            Uri builtUri = Uri.parse(url_user).buildUpon().build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }
        return null;
    }


    //Responsavel por realizar conexao HTTP e carregar o JSON
    public static String getJSONFromAPI(String url){
        String response = "";
        try {
            if (url == null || url.isEmpty()){
                return response;
            }
            URL apiEnd = new URL(url);
            int code_response;
            HttpURLConnection connection;
            InputStream is;

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            code_response = connection.getResponseCode();
            if(code_response < HttpURLConnection.HTTP_BAD_REQUEST){
                is = connection.getInputStream();
            }else{
                is = connection.getErrorStream();
            }

            response = convertInputStreamToString(is);
            is.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return response;
        }catch (IOException e){
            e.printStackTrace();
            return response;
        }

        return response;
    }

    //Converte o retorno da API em String
    private static String convertInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String line;

            br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine())!=null){
                buffer.append(line);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}