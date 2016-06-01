package com.example.mirella.pokedex;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirella.pokedex.dao.PokemonDAO;
import com.example.mirella.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends ListActivity implements View.OnClickListener {

    PokemonDAO dao;

    private StateAdapter adpPokemon;
    List<Pokemon> registros;
    Button btnBuscar;
    TextView txtBuscar;
    String textoPesquisa;

    @Override
    protected void onResume() {
        super.onResume();

        registros = dao.listarTodos();

        this. adpPokemon = new StateAdapter(this, registros);
        adpPokemon.notifyDataSetChanged();
        this.setListAdapter(adpPokemon);

        this.adpPokemon.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        btnBuscar = (Button) findViewById(R.id.buscar);
        txtBuscar = (TextView) findViewById(R.id.editText);

        btnBuscar.setOnClickListener(this);

        dao = new PokemonDAO(getApplicationContext());

        txtBuscar.setText("");

        registros = dao.listarTodos();

        this.carregarLista(registros);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Pokemon pokemon = adpPokemon2.getItem(position);
                //Toast.makeText(ListaActivity.this, "Ola", Toast.LENGTH_SHORT).show();
                String url = registros.get(position).getUrlImagem();
                intent = new Intent(ListaActivity.this, Item2Activity.class);
                intent.putExtra("imagemUrl", url);
                intent.putExtra("pokeNome", registros.get(position).getNome());
                intent.putExtra("pokeId", registros.get(position).getId().toString());
                startActivity(intent);
            }
        });
    }

    private void carregarLista(List<Pokemon> lista){
        this.adpPokemon = new StateAdapter(this, registros);
        adpPokemon.notifyDataSetChanged();
        this.setListAdapter(adpPokemon);
    }

    Intent intent;
    public void sair(View view){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_cadastrar) {
            intent = new Intent(this, Item1Activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.buscar):
                if(txtBuscar.getText() != "" || txtBuscar.getText() != null){
                    textoPesquisa = txtBuscar.getText().toString();
                    this.registros = dao.listAllFilter(textoPesquisa);

                    this.carregarLista(this.registros);

//                    String n = registros.
//                    intent.putExtra("pokeNome", registros.getNome());
//                    intent.putExtra("pokeId", registros.getId().toString());
//                    intent.putExtra("imagemUrl", "/storage/emulated/0/Download/025Pikachu_Pokemon_Mystery_Dungeon_Red_and_Blue_Rescue_Teams.png");
//                    startActivity(intent);
                }
                break;

        }
    }
}