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

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.classes.Aluno;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by android6275 on 28/10/16.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    LatLng posicao;

    @Override
    public void onResume(){
        super.onResume();

        getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        AlunoDAO dao = new AlunoDAO(getContext());
        List<Aluno> listaAlunos = dao.getLista();

        for(Aluno aluno : listaAlunos){

            String endereco = aluno.getEndereco();

            //Pega as coordenadas do endereço passado
            posicao = getCoordenadas(endereco);

            if(posicao != null){

                MarkerOptions marcador = new MarkerOptions();
                marcador.position(posicao);
                marcador.title(aluno.getNome());
                marcador.snippet(aluno.getTelefone());
                googleMap.addMarker(marcador);

            } else {

                Log.i("POSICAO NULA", String.valueOf(posicao));

            }

        }

        dao.close();

        /*Criamos nossa variavel com as coordenadas (latitude e longitude) e a informação de *zoom
                     ZOOM: O valor pode variar entre 0 a 18, sendo 0 o mais distante possível e 18 o mais próximo*/
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicao, 12);

        googleMap.moveCamera(update);
        //indicamos para o googleMap onde queremos posicionar a Camera (Tela)

        Log.i("Posição com endereço", String.valueOf(update));

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
}
