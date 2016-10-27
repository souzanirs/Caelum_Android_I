package br.com.caelum.olamundo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ola_mundo);
        Log.i("CICLO DE VIDA", "OnCreate");

        //Declarando campos formulários
        final EditText entrada = (EditText) findViewById(R.id.main_entrada);
        Button btn = (Button) findViewById(R.id.main_botao);
        final TextView saida = (TextView) findViewById(R.id.main_saida);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saida.setText(entrada.getText().toString());
            }
        });
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        Log.i("CICLO DE VIDA","onStart");
//    }
//
//    public void onResume(){
//        super.onResume();
//        Log.i("CICLO DE VIDA","onResume");
//    }
//
//    public void onPause(){
//        super.onPause();
//        Log.i("CICLO DE VIDA","onPause");
//    }
//
//
//    public void onStop(){
//        super.onStop();
//        Log.i("CICLO DE VIDA","onStop");
//    }
//
//    public void onDestroy(){
//        super.onDestroy();
//        Log.i("CICLO DE VIDA","onDestroy");
//    }
}
