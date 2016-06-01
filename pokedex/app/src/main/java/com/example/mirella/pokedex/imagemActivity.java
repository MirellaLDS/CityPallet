package com.example.mirella.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class imagemActivity extends Activity implements View.OnClickListener {
    /** RESULT_GALERIA */
    private static final int RESULT_GALERIA = 222;

    private Button botaoAddGaleria;
    private ImageView imagemCapturada;
    private Button botaoDeletarImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);

        inicializarComponentes();

        Intent intent = getIntent();
        String name = intent.getStringExtra("imagemUrl");
        if (!name.equals("")) {
            Bitmap img = BitmapFactory.decodeFile(name);

            imagemCapturada.setImageBitmap(img);
        }
    }

    private void inicializarComponentes(){
        imagemCapturada = (ImageView) findViewById(R.id.foto);

        botaoAddGaleria = (Button) findViewById(R.id.botao_capturar_galeria);
        botaoAddGaleria.setOnClickListener(this);

        botaoDeletarImagem = (Button) findViewById(R.id.botao_deletar);
        botaoDeletarImagem.setOnClickListener(this);
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
                    "Entrou: " + foto,
                    Toast.LENGTH_LONG).show();

            if(foto != null){
                imagemCapturada.setImageBitmap(foto);

                Toast.makeText(getApplication(),
                        "Entrou foto != null",
                        Toast.LENGTH_LONG).show();
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
            imagemCapturada.setImageBitmap(null);
        }
    }
}
