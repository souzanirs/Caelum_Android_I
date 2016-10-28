package br.com.caelum.cadastro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.caelum.cadastro.R;

/**
 * Created by android6275 on 28/10/16.
 */

public class DestalhesProvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        return layout;
    }
}
