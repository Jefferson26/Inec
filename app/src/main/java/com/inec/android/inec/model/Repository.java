package com.inec.android.inec.model;

public class Repository {
    private String name;
    private String description;
    private String language;

    /**
     * Os seguintes parametros sao requeridos para a construcao do objeto
     * name - nome do repositorio de um determinado usuario
     * description - descricao do repositorio fornecida pelo usuario
     * language - linguagem de programacao usado em um repositorio
     */
    public Repository(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    //conjunto de getters e setters dos atributos da classe
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
