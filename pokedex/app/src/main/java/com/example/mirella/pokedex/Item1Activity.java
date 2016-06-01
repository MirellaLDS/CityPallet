package com.example.mirella.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirella.pokedex.dao.PokemonDAO;
import com.example.mirella.pokedex.model.Pokemon;

public class Item1Activity extends Activity implements View.OnClickListener {

    Button btnCadastrar;
    Button btnGaleria;
    EditText editNome;
    EditText editPeso;
    EditText editHabilidade;
    EditText editAltura;
    EditText editStatus;
    //EditText editNivelEvolucao;
    Spinner spinnerTipo;
    Spinner spinnerSexo;

    private SeekBar seekBarNivel;
    private TextView textSeekBarNivel;

    String urlImage;
    int progress = 0;

    private static final int RESULT_GALERIA = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editNome = (EditText) findViewById(R.id.txtNome);
        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        editPeso = (EditText) findViewById(R.id.txtPeso);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        editHabilidade = (EditText) findViewById(R.id.txtHabilidades);
        editAltura = (EditText) findViewById(R.id.txtAltura);
        editStatus = (EditText) findViewById(R.id.txtStatus);
        //editNivelEvolucao = (EditText) findViewById(R.id.txtNivelEvolucao);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnGaleria = (Button) findViewById(R.id.btnGaleria);

        seekBarNivel = (SeekBar) findViewById(R.id.seekBarNivelEvolucao);
        textSeekBarNivel = (TextView) findViewById(R.id.txtSeekBarNivelEvolucao);

        btnCadastrar.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);

        textSeekBarNivel.setText("Covered: " + seekBarNivel.getProgress() + "/" + seekBarNivel.getMax());

        seekBarNivel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textSeekBarNivel.setText("Covered: " + progress + "/" + seekBar.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void voltar(View view){
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCadastrar:
                ///Regra de negocio btnSalvar
                Pokemon pokemon = new Pokemon();
                pokemon.setNome(editNome.getText().toString());
                pokemon.setTipo(spinnerTipo.getSelectedItem().toString());
                pokemon.setPeso(editPeso.getText().toString());
                pokemon.setSexo(spinnerSexo.getSelectedItem().toString());
                pokemon.setAltura(editAltura.getText().toString());
                pokemon.setHabilidades(editHabilidade.getText().toString());
                pokemon.setStatus(editStatus.getText().toString());
                pokemon.setNivelEvolucao(this.progress+"");
                pokemon.setUrlImagem(urlImage);

                PokemonDAO dao = new PokemonDAO(getApplicationContext());

                if(dao.adicionar(pokemon)){
                    Toast.makeText(getApplication(),
                            "Dados adicionado com Sucesso ao Banco de Dados!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplication(),
                            "Erro ao gravar os dados no Banco de Dados!",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnGaleria:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GALERIA);
                //clickBtnDash();
                break;

        }

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
        Intent it = new Intent(Item1Activity.this,imagemActivity.class);
        it.putExtra("imagemUrl", url);
        startActivity(it);
//        setContentView(R.layout.activity_item2);
    }
}
