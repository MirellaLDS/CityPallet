package com.example.mirella.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirella.pokedex.dao.PokemonDAO;
import com.example.mirella.pokedex.model.Pokemon;

import java.util.Random;

public class Item2Activity extends Activity implements OnClickListener {

    /** RESULT_GALERIA */
    private static final int RESULT_GALERIA = 222;
    private static final int TELA_ALTERAR = 1;
    private static final int TOTAL_PROGRESS_TIME = 100;

    private Button botaoAddGaleria;
    private ImageView imagemCapturada;
    private Button botaoDeletarImagem;
    private Button botaoEditar;
    private TextView tvName;
    private TextView tvPeso;
    private TextView tvTipo;
    private TextView tvSexo;
    private TextView tvaltura;
    private TextView tvhabilidade;
    private TextView tvstatus;
    private TextView tvnivelEvolucao;
    private ProgressBar progressNivel;
    private ProgressBar pVida;
    //String imagemUrl;

    String pokeId;
    String pokeNome;
    PokemonDAO dao;
    Pokemon pokemon;

    String nome, tipo, peso, sexo, altura, habilidades, status, nivelEvolucao, urlImagem;
    int pokeIdEdit;

    @Override
    protected void onResume() {
        super.onResume();
        montarObjeto(pokeNome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        dao = new PokemonDAO(getApplicationContext());

        inicializarComponentes();

        Intent intent = getIntent();
        pokeNome = intent.getStringExtra("pokeNome");
        pokeId = intent.getStringExtra("pokeId");

        montarObjeto(pokeNome);

        String imagemUrl = intent.getStringExtra("imagemUrl");
        if (imagemUrl == null) {
//            Bitmap img = BitmapFactory.decodeFile("@drawable/sem_imagem");
//
//            imagemCapturada.setImageBitmap(img);
            Toast.makeText(Item2Activity.this, "SEM IMAGEM", Toast.LENGTH_SHORT).show();
        }else if (!imagemUrl.equals("")) {
            Bitmap img = BitmapFactory.decodeFile(imagemUrl);

            imagemCapturada.setImageBitmap(img);
        }
    }


    private void montarObjeto(String pokeNome){
        pokemon = dao.listarConsulta(pokeNome);
        pokeIdEdit = pokemon.getId();
        nome = pokemon.getNome();
        tipo = pokemon.getTipo();
        peso = pokemon.getPeso();
        sexo = pokemon.getSexo();
        altura = pokemon.getAltura();
        habilidades = pokemon.getHabilidades();
        status = pokemon.getStatus();
        nivelEvolucao = pokemon.getNivelEvolucao();
        urlImagem = pokemon.getUrlImagem();

        progressNivel.setMax(TOTAL_PROGRESS_TIME);
        progressNivel.setProgress(Integer.parseInt(pokemon.getNivelEvolucao()));
        int min = 10;

        Random r = new Random();

        pVida.setMax(TOTAL_PROGRESS_TIME);
        pVida.setProgress(r.nextInt(TOTAL_PROGRESS_TIME - min + 1) + min);

        tvName.setText("Nome: " + nome);
        tvPeso.setText("Peso: " + peso);
        tvTipo.setText("Tipo: " + tipo);
        tvSexo.setText("Sexo: " + sexo);
        tvaltura.setText("Altura: " + altura);
        tvhabilidade.setText("Habilidade: " + habilidades);
        tvstatus.setText("Status: " + status);
        tvnivelEvolucao.setText("Nivel de Evolucao: " + nivelEvolucao);
        //this.imagemUrl = urlImagem;
    }

    private void inicializarComponentes(){
        imagemCapturada = (ImageView) findViewById(R.id.foto);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPeso = (TextView) findViewById(R.id.tvPeso);
        tvTipo = (TextView) findViewById(R.id.tvtxtTipo);
        tvSexo = (TextView) findViewById(R.id.tvSexo);
        tvaltura = (TextView) findViewById(R.id.tvAltura);
        tvhabilidade = (TextView) findViewById(R.id.tvHabilidades);
        tvstatus = (TextView) findViewById(R.id.tvStatus);
        tvnivelEvolucao = (TextView) findViewById(R.id.tvNível_de_evolução);
        progressNivel = (ProgressBar) findViewById(R.id.progressNivel);
        pVida = (ProgressBar) findViewById(R.id.progressVida);

        botaoAddGaleria = (Button) findViewById(R.id.botao_capturar_galeria);
        botaoAddGaleria.setOnClickListener(this);

        botaoDeletarImagem = (Button) findViewById(R.id.botao_deletar);
        botaoDeletarImagem.setOnClickListener(this);

        botaoEditar = (Button) findViewById(R.id.btnCadastrar);
        botaoEditar.setOnClickListener(this);
    }

    public void voltar(View view){
        finish();
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

            //Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());

           Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());

           Toast.makeText(getApplication(),
                   "Entrou: "+foto,
                   Toast.LENGTH_LONG).show();

            if(foto != null){
                imagemCapturada.setImageBitmap(foto);
            }
        }else if(requestCode == TELA_ALTERAR){
           Bundle param = data.getExtras();
           if (param != null){
               pokeNome = param.getString("pokeNome");
           }
       }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        if (id == R.id.botao_capturar_galeria){
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_GALERIA);

        }else if (id == R.id.botao_deletar){
            //imagemCapturada.setImageBitmap(null);
            int idPokemon = Integer.parseInt(pokeId);
            if (dao.apagarPokemon(idPokemon)){
                Toast.makeText(Item2Activity.this, "Apagou!", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(Item2Activity.this, "Não apagou!", Toast.LENGTH_SHORT).show();
            }
        }else if (id == R.id.btnCadastrar){
//            Toast.makeText(getApplication(),
//                    "Botão",
//                    Toast.LENGTH_LONG).show();
            intent = new Intent(Item2Activity.this, Alterar.class);
            intent.putExtra("pokemonNome", pokeNome);
            startActivityForResult(intent, TELA_ALTERAR);
        }
    }

}
