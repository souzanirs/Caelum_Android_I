package br.com.caelum.cadastro.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.ProvasActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.classes.Prova;

/**
 * Created by android6275 on 28/10/16.
 */

public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutProvas = inflater.inflate( R.layout.fragment_lista_provas, container, false);

        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        Prova prova1 = new Prova("20160620", "Matematica");
        prova1.setTopicos(Arrays.asList("Algebra Linear", "Calculo", "Estatistica"));

        Prova prova2 = new Prova("20160725","Portugues");
        prova2.setTopicos(Arrays.asList("Complemento nominal", "Oração Subordinada", "Analise Sintatica"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(),android.R.layout.simple_list_item_1, provas));

        this.listViewProvas.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {

                Prova selecionada = (Prova) adapterView.getItemAtPosition(posicao);

                Toast.makeText( getActivity(), "Prova Selecionada: "+selecionada, Toast.LENGTH_SHORT ).show();

                ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
                calendarioProvas.selecionaProva(selecionada);

            }
        } );

        return layoutProvas;
    }
}
