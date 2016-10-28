package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.fragment.MapaFragment;

/**
 * Created by android6275 on 28/10/16.
 */
public class MostraAlunosActivity extends AppCompatActivity {
    private static MostraAlunosActivity ourInstance = new MostraAlunosActivity();

    protected void onCreate(Bundle bundle){

        super.onCreate( bundle );
        setContentView( R.layout.activity_mostra_alunos );
        MapaFragment mapaFragment = new MapaFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.mostra_alunos_mapa, mapaFragment );
        ft.commit();

    }

}
