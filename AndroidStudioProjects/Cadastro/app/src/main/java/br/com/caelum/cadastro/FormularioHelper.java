package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.classes.Aluno;

/**
 * Created by android6275 on 25/10/16.
 */

public class FormularioHelper {

    private Aluno aluno;

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText email;
    private RatingBar nota;
    private ImageView foto;
    private Button fotoBotao;

    public FormularioHelper(FormularioActivity formularioActivity) {

        this.aluno = new Aluno();

        this.nome = (EditText) formularioActivity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) formularioActivity.findViewById(R.id.formulario_telefone);
        this.endereco = (EditText) formularioActivity.findViewById(R.id.formulario_endereco);
        this.email = (EditText) formularioActivity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) formularioActivity.findViewById(R.id.formulario_ratio);
        this.foto = (ImageView) formularioActivity.findViewById(R.id.formulario_foto);
        this.fotoBotao = (Button) formularioActivity.findViewById(R.id.formulario_botao_foto);

    }

    public Aluno criaAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setSite(email.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setTelefone(telefone.getText().toString());
        aluno.setCaminhoFoto((String) foto.getTag());

        return aluno;
    }

    public boolean temNome() {
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro() {
        nome.setError("Campo nome obrigatório");
    }

    public void carregaAluno(Aluno aluno){

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        email.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());

        //Valida se o aluno possui foto, caso possua carrega na tela
        if(aluno.getCaminhoFoto() != null){
            carregaImagem(aluno.getCaminhoFoto());
        }

        this.aluno = aluno;
    }

    public Button getFotoBotao(){
        return fotoBotao;
    }

    public void carregaImagem(String localArquivoFoto) {
        Bitmap bitmapFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap bitmapFotoreduzida = Bitmap.createScaledBitmap(bitmapFoto, 300, 300, true);
        foto.setImageBitmap(bitmapFotoreduzida);
        foto.setTag(localArquivoFoto);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }

}
