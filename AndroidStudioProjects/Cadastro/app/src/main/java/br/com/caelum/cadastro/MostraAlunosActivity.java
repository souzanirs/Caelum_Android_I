package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

import br.com.caelum.cadastro.fragment.MapaFragment;

/**
 * Created by android6275 on 28/10/16.
 */
public class MostraAlunosActivity extends AppCompatActivity {
    private static MostraAlunosActivity ourInstance = new MostraAlunosActivity();

    protected void onCreate(Bundle bundle){

        super.onCreate( bundle );
        setContentView( R.layout.activity_mostra_alunos );

        FragmentManager manager = getSupportFragmentManager(); //Referencia para o Fragment
        FragmentTransaction ft = manager.beginTransaction(); //Toda operação com Frangment precisa de uma transição
        ft.replace( R.id.mostra_alunos_mapa, new MapaFragment()); //Aplicação do Mapa no espaço reservado para o Fragment.

        /* SupportMapFragment, possui todas as funcionalidade de mapa do Google Maps */
        /*Para podermos personalizar as informações na tela, criamos uma classe que extends de SupportMapFragment*/

        ft.commit();

    }

}
