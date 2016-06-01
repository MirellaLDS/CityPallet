package com.example.mirella.pokedex.model;

/**
 * Created by mlds on 05/04/2016.
 */
public class Pokemon {

    private Integer id;
    private String nome;
    private String tipo;
    private String peso;
    private String sexo;
    private String altura;
    private String habilidades;
    private String status;
    private String nivelEvolucao;
    private String urlImagem;

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNivelEvolucao() {
        return nivelEvolucao;
    }

    public void setNivelEvolucao(String nivelEvolucao) {
        this.nivelEvolucao = nivelEvolucao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String url) {
        this.urlImagem = url;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
