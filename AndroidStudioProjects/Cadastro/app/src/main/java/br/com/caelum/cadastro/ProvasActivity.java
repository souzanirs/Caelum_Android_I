package br.com.caelum.cadastro;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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

        transaction.replace(R.id.provas_view, new ListaProvasFragment());

        transaction.commit();
    }
}
