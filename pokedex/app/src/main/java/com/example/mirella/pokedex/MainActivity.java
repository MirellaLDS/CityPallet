package com.example.mirella.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnCadastrar, btnConsultar, btnCadTipo;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.este);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnCadTipo = (Button) findViewById(R.id.btnCadastrarTipo);

        btnCadastrar.setOnClickListener(this);
        btnConsultar.setOnClickListener(this);
        btnCadTipo.setOnClickListener(this);
    }

    Intent intent;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCadastrar:
                intent = new Intent(MainActivity.this, Item1Activity.class);
                startActivity(intent);
                break;
            case R.id.btnConsultar:
                intent = new Intent(MainActivity.this, ListaActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCadastrarTipo:
                intent = new Intent(MainActivity.this, cad_usuario.class);
                startActivity(intent);
                break;
        }
    }
}