package com.example.mirella.pokedex.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.mirella.pokedex.model.Pokemon;
import com.example.mirella.pokedex.datasource.DataSource;
import com.example.mirella.pokedex.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlds on 05/04/2016.
 */
public class PokemonDAO {

    DataSource ds;
    ContentValues values;

    public PokemonDAO(Context context){
        ds = new DataSource(context);
    }

    public boolean adicionar(Pokemon obj){

        boolean retorno = false;

        values = new ContentValues();

        values.put(DataModel.getNOME(),obj.getNome());
        values.put(DataModel.getTIPO(),obj.getTipo());
        values.put(DataModel.getPESO(),obj.getPeso());
        values.put(DataModel.getSEXO(),obj.getSexo());
        values.put(DataModel.getALTURA(),obj.getAltura());
        values.put(DataModel.getHABILIDADES(),obj.getHabilidades());
        values.put(DataModel.getSTATUS(),obj.getStatus());
        values.put(DataModel.getNivelEvolucao(),obj.getNivelEvolucao());
        values.put(DataModel.getUrlImagem(),obj.getUrlImagem());

        try {
            ds.persist(values,DataModel.getTABELA_POKEMON());
            retorno = true;
        }catch (Exception e){
        }

        return retorno;
    }

    public boolean update(Pokemon obj){
        boolean retorno = false;

        values = new ContentValues();

        values.put(DataModel.getNOME(),obj.getNome());
        values.put(DataModel.getTIPO(),obj.getTipo());
        values.put(DataModel.getPESO(),obj.getPeso());
        values.put(DataModel.getSEXO(),obj.getSexo());
        values.put(DataModel.getALTURA(),obj.getAltura());
        values.put(DataModel.getHABILIDADES(),obj.getHabilidades());
        values.put(DataModel.getSTATUS(),obj.getStatus());
        values.put(DataModel.getNivelEvolucao(),obj.getNivelEvolucao());
        //values.put(DataModel.getUrlImagem(),obj.getUrlImagem());

        try {
            ds.update(values, DataModel.getTABELA_POKEMON(), obj.getId());
            retorno = true;
        }catch (Exception e){
        }

        return retorno;
    }

    public List<Pokemon> listarTodos() {

        Cursor cursor = ds.find(DataModel.getTABELA_POKEMON(), null, null, null, null, null, null, null);

        List<Pokemon> lista = new ArrayList<Pokemon>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                Pokemon obj = new Pokemon();

                obj.setId(cursor.getInt(cursor.getColumnIndex(DataModel.getID())));
                obj.setNome(cursor.getString(cursor.getColumnIndex(DataModel.getNOME())));
                obj.setUrlImagem(cursor.getString(cursor.getColumnIndex(DataModel.getUrlImagem())));

                lista.add(obj);

                cursor.moveToNext();
            }
        }
        return lista;
    }

    public List<Pokemon> listAllFilter(String nome) {

        String[] campos = new String[] {DataModel.getNOME()};
        String where = DataModel.getNOME() + "='" + nome +"'";

        Cursor cursor = ds.find(DataModel.getTABELA_POKEMON(), null, where,null, null,null,null,null);

        List<Pokemon> lista = new ArrayList<Pokemon>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                Pokemon obj = new Pokemon();

                obj.setId(cursor.getInt(cursor.getColumnIndex(DataModel.getID())));
                obj.setNome(cursor.getString(cursor.getColumnIndex(DataModel.getNOME())));
                obj.setUrlImagem(cursor.getString(cursor.getColumnIndex(DataModel.getUrlImagem())));

                lista.add(obj);

                cursor.moveToNext();
            }
        }
        return lista;
    }

    public Pokemon listarConsulta(String nome) {
        return ds.listarPokemon(nome);
    }

    public boolean apagarPokemon (int id) {
        return ds.delete(id);
    }
}
