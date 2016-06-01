package com.example.mirella.pokedex.datasource;

/**
 * Created by mlds on 05/04/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mirella.pokedex.datamodel.DataModel;
import com.example.mirella.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by maddo on 03/05/15.
 */
public class DataSource extends SQLiteOpenHelper{

    SQLiteDatabase db;

    public DataSource(Context context) {

        super(context, DataModel.getDB_NAME(), null, 1);

        db = this.getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataModel.criarTabelaLogin());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        onCreate(db);

    }

    /**
     * Método persiste - Incluir ou alterar os dados no Banco de Dados
     * Caso o ID seja nulo ou zero, será executado o método insert().
     * Caso contrário, será executado o método update().
     * @param values
     * @param tabela
     */
    public void persist(ContentValues values, String tabela) {

        if (values.containsKey("id") && values.getAsInteger("id") != null
                && values.getAsInteger("id") != 0) {
            Integer id = values.getAsInteger("id");
            db.update(tabela, values, "id = ?", new String[]{ Integer.toString(id)});
        } else {

            db.insert(tabela, null, values);

        }

    }

    public boolean update(ContentValues values, String tabela, int id) {

        if (id != 0) {
           return db.update(tabela, values, "id = "+ id, null) > 0;
        }
        return false;
    }

    /**
     * Método find utilizado para persquisar dados em qualquer tabela do banco
     * de dados local conforme os parâmetros informados.
     *
     * Retorna um DataSet com o resultado da busca.
     *
     * @return retorno
     *
     */
    public Cursor find(String tabela, String[] columns, String selection,
                       String[] selectionArgs, String groupBy, String having,
                       String orderBy, String limit) {
        Cursor retorno = db.query(tabela, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit);

        return retorno;
    }

    public Pokemon listarPokemon(String nome) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataModel.getTABELA_POKEMON() + " WHERE nome=?", new String[]{nome});

        if (cursor.getCount() > 0) {
            Pokemon pokemon = new Pokemon();
            cursor.moveToFirst();
            pokemon.setId(cursor.getInt(0));
            pokemon.setNome(cursor.getString(1));
            pokemon.setTipo(cursor.getString(2));
            pokemon.setPeso(cursor.getString(3));
            pokemon.setSexo(cursor.getString(4));
            pokemon.setAltura(cursor.getString(5));
            pokemon.setHabilidades(cursor.getString(6));
            pokemon.setStatus(cursor.getString(7));
            pokemon.setUrlImagem(cursor.getString(8));
            pokemon.setNivelEvolucao(cursor.getString(9));

            return pokemon;
        }
        return null;
    }

    public boolean delete (int id){
        if (id != 0){
            return db.delete(DataModel.getTABELA_POKEMON(), "id = ?", new String[]{ Integer.toString(id)}) > 0;
        }
        return false;
    }

}
