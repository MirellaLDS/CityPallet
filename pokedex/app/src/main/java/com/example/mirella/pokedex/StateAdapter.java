package com.example.mirella.pokedex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirella.pokedex.model.Pokemon;

import java.util.List;

/**
 * Created by Mirella on 11/04/2016.
 */
public class  StateAdapter extends ArrayAdapter<Pokemon> {
    private  Context context;
    private  List<Pokemon> pokemons = null;



    public StateAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
        this.context = context;
        this.pokemons = pokemons;
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Pokemon getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Pokemon pokemon = pokemons.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.linha_list_view, parent, false);

        TextView textViewNomePokemon = (TextView) view.findViewById(R.id.txtName);
        textViewNomePokemon.setText(pokemon.getNome());

        TextView textViewIdPokemon = (TextView) view.findViewById(R.id.txtId);
        textViewIdPokemon.setText("" + pokemon.getId());

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);

        imageView.setImageResource(R.drawable.ic_ok);

        return view;
    }
}
