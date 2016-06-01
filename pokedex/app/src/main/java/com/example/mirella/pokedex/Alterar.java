package com.example.mirella.pokedex;

/**
 * Created by Mirella on 17/05/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirella.pokedex.dao.PokemonDAO;
import com.example.mirella.pokedex.model.Pokemon;


public class Alterar extends Activity implements View.OnClickListener {

    private static final int RESULT_GALERIA = 222;


    PokemonDAO dao;
    String nomePesquisa;
    Pokemon pokemon;
    String urlImage;

    int pokeIdEdit;
    EditText editNome;
//    EditText editTipo;
    Spinner spinnerTipo;
    Spinner spinnerSexo;
    EditText editPeso;
    //EditText editSexo;
    EditText editHabilidade;
    EditText editAltura;
    EditText editStatus;
    EditText editNivelEvolucao;
    private TextView txtStausVida;
    private ProgressBar progressVida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);
        dao = new PokemonDAO(getApplicationContext());

        nomePesquisa = this.getIntent().getStringExtra("pokemonNome");

        pokemon = dao.listarConsulta(nomePesquisa);
        pokeIdEdit = pokemon.getId();

        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        txtNome.setText(pokemon.getNome());
//        TextView txtTipo = (TextView) findViewById(R.id.txtTipo);
//        txtTipo.setText(pokemon.getTipo());
        EditText editPeso = (EditText) findViewById(R.id.txtPeso);
        editPeso.setText(pokemon.getPeso());
//        EditText editSexo = (EditText) findViewById(R.id.txtSexo);
//        editSexo.setText(pokemon.getSexo());
        EditText editHabilidade = (EditText) findViewById(R.id.txtHabilidades);
        editHabilidade.setText(pokemon.getAltura());
        EditText editAltura = (EditText) findViewById(R.id.txtAltura);
        editAltura.setText(pokemon.getAltura());
        EditText editStatus = (EditText) findViewById(R.id.txtStatus);
        editStatus.setText(pokemon.getStatus());
        EditText editNivelEvolucao = (EditText) findViewById(R.id.txtNivelEvolucao);
        editNivelEvolucao.setText(pokemon.getNivelEvolucao());
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnGaleria = (Button) findViewById(R.id.btnGaleria);

        btnUpdate.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);

        carregarComponentes();

        String[] listaTipo = getResources().getStringArray(R.array.tipos);
        String[] listaSexo = getResources().getStringArray(R.array.sexo);
        SetSpinnerSelectionTipo(spinnerTipo, listaTipo, pokemon.getTipo());
        SetSpinnerSelectionSexo(spinnerSexo, listaSexo, pokemon.getSexo());

        //progressVida.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);

        //Toast.makeText(Alterar.this, teste.toString(), Toast.LENGTH_SHORT).show();

    }

    public void SetSpinnerSelectionTipo(Spinner spinnerTipo, String[] array, String text) {
        for(int i=0;i<array.length;i++) {
            if(array[i].equals(text)) {
                spinnerTipo.setSelection(i);
                break;
            }
        }
    }

    public void SetSpinnerSelectionSexo(Spinner spinner, String[] array, String text) {
        for(int i=0;i<array.length;i++) {
            if(array[i].equals(text)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void carregarComponentes(){

        editNome = (EditText) findViewById(R.id.txtNome);
//        editTipo = (EditText) findViewById(R.id.txtTipo);
        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        editPeso = (EditText) findViewById(R.id.txtPeso);
//        editSexo = (EditText) findViewById(R.id.txtSexo);
        editHabilidade = (EditText) findViewById(R.id.txtHabilidades);
        editAltura = (EditText) findViewById(R.id.txtAltura);
        editStatus = (EditText) findViewById(R.id.txtStatus);
        editNivelEvolucao = (EditText) findViewById(R.id.txtNivelEvolucao);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == RESULT_GALERIA){
            Uri imageUri = data.getData();
            String[] colunaArquivo = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri, colunaArquivo, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(colunaArquivo[0]);
            String picturePath = cursor.getString(columnIndex);

            urlImage = picturePath.toString();

            if(urlImage != null || !urlImage.equals("")){
                clickBtnDash(urlImage);

//                Toast.makeText(getApplication(),
//                        "Entrou: "+urlImage,
//                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplication(),
                        "Imagem não entrou!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void clickBtnDash(String url){
        Intent it = new Intent(Alterar.this,imagemActivity.class);
        it.putExtra("imagemUrl", url);
        startActivity(it);
        //setContentView(R.layout.activity_detalhes);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGaleria:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GALERIA);
                //clickBtnDash();
                break;
            case R.id.btnUpdate:
                ///Regra de negocio btnSalvar
                Pokemon pokemon = new Pokemon();

                pokemon.setId(pokeIdEdit);
                pokemon.setNome(editNome.getText().toString());
                pokemon.setTipo(spinnerTipo.getSelectedItem().toString());
                pokemon.setPeso(editPeso.getText().toString());
                pokemon.setSexo(spinnerSexo.getSelectedItem().toString());
                pokemon.setAltura(editAltura.getText().toString());
                pokemon.setHabilidades(editHabilidade.getText().toString());
                pokemon.setStatus(editStatus.getText().toString());
                pokemon.setNivelEvolucao(editNivelEvolucao.getText().toString());
                //pokemon.setUrlImagem(urlImage);

                PokemonDAO dao = new PokemonDAO(getApplicationContext());

                if(dao.update(pokemon)){
                    Toast.makeText(getApplication(),
                            "Dados adicionado com Sucesso ao Banco de Dados!",
                            Toast.LENGTH_LONG).show();
                    Intent it = new Intent();
                    it.putExtra("pokeNome", editNome.getText().toString());
                    setResult(1, it);
                    finish();
                }else{
                    Toast.makeText(getApplication(),
                            "Erro ao gravar os dados no Banco de Dados!",
                            Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
}
