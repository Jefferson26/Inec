package com.inec.android.inec.model;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class User {
    private String user_login;
    private String name;
    private Bitmap image_profile;
    private ArrayList<Repository> repository;

    /**
     * Os seguintes parametros sao requeridos para a construcao do objeto
     * user_login - atributo no qual sera usado para fazer a busca do usuario
     * name - nome da pessoa fisica
     * image_profile - imagem retornada pela API do GitHub
    */
    public User(String user_login, String name, Bitmap image_profile) {
        this.user_login = user_login;
        this.name = name;
        this.image_profile = image_profile;
        this.repository = new ArrayList<>();
    }

    //conjunto de getters e setters dos atributos da classe
    public String getUser_login() {
        return user_login;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage_profile() {
        return image_profile;
    }

    public ArrayList<Repository> getRepository() {
        return repository;
    }

    public void setRepository(ArrayList<Repository> repository) {
        this.repository = repository;
    }
}
