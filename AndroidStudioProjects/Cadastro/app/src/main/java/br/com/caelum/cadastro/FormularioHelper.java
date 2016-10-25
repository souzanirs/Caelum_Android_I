package br.com.caelum.cadastro;

import android.widget.EditText;
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

    public FormularioHelper(FormularioActivity formularioActivity) {

        this.aluno = new Aluno();

        this.nome = (EditText) formularioActivity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) formularioActivity.findViewById(R.id.formulario_telefone);
        this.endereco = (EditText) formularioActivity.findViewById(R.id.formulario_endereco);
        this.email = (EditText) formularioActivity.findViewById(R.id.formulario_email);
        this.nota = (RatingBar) formularioActivity.findViewById(R.id.formulario_ratio);

    }

    public Aluno criaAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setEmail(email.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setTelefone(telefone.getText().toString());

        return aluno;
    }

}
