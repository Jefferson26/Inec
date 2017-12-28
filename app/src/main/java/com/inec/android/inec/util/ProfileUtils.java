package com.inec.android.inec.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.inec.android.inec.model.Repository;
import com.inec.android.inec.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.ArrayList;

public class ProfileUtils {

    //Extrai os dados do JSON e adiciona no objeto User
    public static User parseJsonUser(String end){
        try {
            if(end != null) {
                String json_user = NetworkUtils.getJSONFromAPI(end);
                JSONObject jsonObj = new JSONObject(json_user);
                if (!jsonObj.isNull("login")) {
                    String login = jsonObj.getString("login");
                    String name;

                    if(jsonObj.getString("name").equals("null"))
                        name = "Name not Registered";
                    else
                        name = jsonObj.getString("name");

                    Bitmap image_profile = downloadImage(jsonObj.getString("avatar_url"));

                    ArrayList<Repository> repo = parseJsonRepository(end);
                    User profile = new User(login, name, image_profile);
                    profile.setRepository(repo);

                    return profile;
                }
                return null;
            }
            return null;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    //Extrai os dados do JSON e adiciona no objeto Repositorio
    private static ArrayList<Repository> parseJsonRepository(String end){
        try {
            if(end != null) {
                String json_repo = NetworkUtils.getJSONFromAPI(end + "/repos");
                JSONArray jsonArray = new JSONArray(json_repo);

                ArrayList<Repository> repositories = new ArrayList<>();
                if(jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                        String name_repo = jsonObj.getString("name");
                        String descripton_repo;

                        if(jsonObj.getString("description").equals("null"))
                            descripton_repo = "Description not Registered";
                        else
                            descripton_repo = jsonObj.getString("description");

                        String language_repo = jsonObj.getString("language");
                        Repository repository = new Repository(name_repo, descripton_repo, language_repo);
                        repositories.add(repository);
                    }
                    return repositories;
                }
                return null;
            }
            return null;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    //Extrai a imagem de perfil do usuario do GitHub
    private static Bitmap downloadImage(String url) {
        try{
            URL adress;
            InputStream inputStream;
            Bitmap image; adress = new URL(url);
            inputStream = adress.openStream();
            image = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return image;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
