package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.classes.Aluno;

/**
 * Created by android6275 on 27/10/16.
 */

public class ListaAlunoAdapter extends BaseAdapter{

    private final List<Aluno> alunos; //Precisamos da lista de alunos para popular nossa viewItem
    private final Activity activity; //Precisamos da activity onde será carregada a listView

    public ListaAlunoAdapter(Activity activity, List<Aluno> alunos) {
        this.alunos = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() { //Tamanho do nosso array de alunos
        return alunos.size();
    }

    @Override
    public Object getItem(int posicao) { //devolve o item na posição
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) { //devolve o id do aluno na posição
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View desconsiderar, ViewGroup viewGroup) { //Infla o layout e popula o mesmo

        //Criamos a view que receberá a foto, nome e telefone. Carrega o layout do Item
        View viewItem = activity.getLayoutInflater().inflate( R.layout.item, viewGroup, false);

        //Instancia o aluno a ser populado no layout
        Aluno aluno = alunos.get(posicao);

        //Pegamos o item da nossa view onde será carregado o nome
        TextView nome = (TextView) viewItem.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome()); //populamos o nome com o nome do aluno

        TextView telefone = (TextView) viewItem.findViewById(R.id.item_telefone);
        telefone.setText(aluno.getTelefone());

        //Intanciamos uma imagem Bitmap que receberá a foto
        Bitmap bmFoto;

        //Validamos se o aluno possui foto para carregamos na tela
        if(aluno.getCaminhoFoto() != null){
            bmFoto = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            bmFoto = BitmapFactory.decodeResource(activity.getResources(),R.drawable.ic_no_image);
        }

        //Ajuda as proporções da imagem
        bmFoto = Bitmap.createScaledBitmap(bmFoto, 100, 100, true);

        //Pegamos o item na nossa view onde será carrega a imagem
        ImageView fotoAluno = (ImageView) viewItem.findViewById(R.id.item_foto);
        fotoAluno.setImageBitmap(bmFoto); //atribuimos a foto ao campo

        return viewItem;
    }
}