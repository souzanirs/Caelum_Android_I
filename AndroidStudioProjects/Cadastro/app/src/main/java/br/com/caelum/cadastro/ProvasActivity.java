package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.classes.Prova;
import br.com.caelum.cadastro.fragment.DestalhesProvaFragment;
import br.com.caelum.cadastro.fragment.ListaProvasFragment;

/**
 * Created by android6275 on 28/10/16.
 */

public class ProvasActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(isTablet()){
            transaction.replace(R.id.provas_lista, new ListaProvasFragment());
            transaction.replace(R.id.provas_detalhes, new DestalhesProvaFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }


        transaction.commit();
    }

    public boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova selecionada) {

        FragmentManager manager = getSupportFragmentManager();

        if(isTablet()){
            DestalhesProvaFragment detalhesProva = (DestalhesProvaFragment) manager.findFragmentById( R.id.provas_detalhes );
            detalhesProva.populaCaomposComDados( selecionada );
        } else {
            Bundle argumentos = new Bundle(  );
            argumentos.putSerializable( "prova",selecionada );

            DestalhesProvaFragment detalhesProva = new DestalhesProvaFragment();
            detalhesProva.setArguments( argumentos );

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace( R.id.provas_view, detalhesProva );

            transaction.addToBackStack( null );

            transaction.commit();
        }
    }
}
