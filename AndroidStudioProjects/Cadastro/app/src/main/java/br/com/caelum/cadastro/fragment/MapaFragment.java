package br.com.caelum.cadastro.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.classes.Aluno;
import br.com.caelum.cadastro.location.AtualizadorDeLocalizacao;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by android6275 on 28/10/16.
 */

public class MapaFragment extends SupportMapFragment {

    GoogleMap map;
    LatLng posicao;

    @Override
    public void onResume(){
        super.onResume();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                MapaFragment.this.map = googleMap;

                Localizador localizador = new Localizador(getActivity());
                LatLng local = localizador.getCoordenada("Rua Desembargador Julio Cesar da Silveira, 1117, Limeira");

                centralizaNo(local, googleMap);

                AlunoDAO alunoDAO = new AlunoDAO(getContext());
                List<Aluno> alunos = alunoDAO.getLista();
                alunoDAO.close();

                for(Aluno aluno : alunos){
                    LatLng coordenada = localizador.getCoordenada(aluno.getEndereco());

                    if(coordenada != null){
                        MarkerOptions marcador = new MarkerOptions()
                                                        .position(coordenada)
                                                        .title(aluno.getNome())
                                                        .snippet(aluno.getTelefone());

                        googleMap.addMarker(marcador);
                    }
                }

            }
        });

        AtualizadorDeLocalizacao atualizador = new AtualizadorDeLocalizacao(getActivity(), this, map);

    }

    private LatLng getCoordenadas(String endereco){

        try {

            Geocoder geo = new Geocoder(getContext()); //converte o endereço em coordenadas (latitude e longitude)

            /* Pega os resultados de acordo com o endereço passado
                    o número inteiro passado ao final corresponde ao numero de resultados esperados */
            List<Address> enderecos = geo.getFromLocationName(endereco, 1);

            if(!enderecos.isEmpty()){

                double latitude = enderecos.get(0).getLatitude();
                double longitude = enderecos.get(0).getLongitude();
                LatLng posicao = new LatLng(latitude, longitude);

                return posicao;

            }

        } catch (IOException e){ e.printStackTrace(); }

        return null;
    }

    public void centralizaNo(LatLng local, GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11));
    }
}
