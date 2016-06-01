package com.example.mirella.pokedex.datamodel;

/**
 * Created by mlds on 05/04/2016.
 */
public class DataModel {

    private static final String DB_NAME = "db_curso_android.sqlite";
    private static final String TABELA_POKEMON = "pokemon";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String TIPO = "tipo";
    private static final String PESO = "peso";
    private static final String SEXO = "sexo";
    private static final String ALTURA = "altura";
    private static final String HABILIDADES = "habilidades";
    private static final String STATUS = "status";
    private static final String NIVEL_EVOLUCAO = "nivelEvolucao";
    private static final String URL_IMAGEM = "urlImagem";
    private static final String TIPO_TEXTO = "TEXT";
    private static final String TIPO_INTEIRO = "INTEGER";
    private static final String TIPO_INTEIRO_PK = "INTEGER PRIMARY KEY ";

    public static String criarTabelaLogin() {

        String query = "CREATE TABLE " + getTABELA_POKEMON();

        query += " (";
        query += getID() + " " + TIPO_INTEIRO_PK + ", ";
        query += getNOME() + " " + TIPO_TEXTO + ", ";
        query += getTIPO() + " " + TIPO_TEXTO + ", ";
        query += getPESO() + " " + TIPO_TEXTO + ", ";
        query += getSEXO() + " " + TIPO_TEXTO + ", ";
        query += getALTURA() + " " + TIPO_TEXTO + ", ";
        query += getHABILIDADES() + " " + TIPO_TEXTO + ", ";
        query += getSTATUS() + " " + TIPO_TEXTO + ", ";
        query += getUrlImagem() + " " + TIPO_TEXTO + ", ";
        query += getNivelEvolucao() + " " + TIPO_TEXTO + " ";
        query += ")";

        return query;
    }

    public static String getPESO() {
        return PESO;
    }

    public static String getSEXO() {
        return SEXO;
    }

    public static String getALTURA() {
        return ALTURA;
    }

    public static String getHABILIDADES() {
        return HABILIDADES;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static String getNivelEvolucao() {
        return NIVEL_EVOLUCAO;
    }

    public static String getDB_NAME() {
        return DB_NAME;
    }

    public static String getTABELA_POKEMON() {
        return TABELA_POKEMON;
    }

    public static String getID() {
        return ID;
    }

    public static String getNOME() {
        return NOME;
    }

    public static String getTIPO() {
        return TIPO;
    }

    public static String getUrlImagem() { return URL_IMAGEM; }

}
