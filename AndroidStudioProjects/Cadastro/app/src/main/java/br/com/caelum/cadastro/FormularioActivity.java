package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.classes.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private static final int cameraCODE = 123; //Codigo para o retorno da Camera
    private FormularioHelper helper; //Criando um atributo herper
    private String localArquivoFoto=""; //Criando variavel para armazenar endereço da foto no sistema

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this); //instanciando o Objeto helper

        //Nosso botão Salvar está no menu
//        Button btnSalvar = (Button) findViewById(R.id.formulario_botao);
//
//        //Escutando o clique no botão
//        btnSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Pegando o campo para extrair o nome
//                EditText viewNome = (EditText) findViewById(R.id.formulario_nome); //Pegar nome do aluno preenchido
//
//                //Extraindo o conteúdo do campo
//                String nome = viewNome.getText().toString();
//
//                //Exibe a mensagem de que o aluno foi cadastrado, utilizamos o texto extraído acima, ficando mais intuitivo
//                Toast.makeText(FormularioActivity.this, "Aluno "+nome+" salvo com sucesso",Toast.LENGTH_SHORT).show();
//
//                /*
//                *Ao invés de carregarmos uma nova Activity com nossa lista, finalizamos a Activity atual
//                *e voltamos a anterior, no caso a lista que está pausada
//                */
//
//                finish();
//            }
//        });

        //Pegando o aluno passado da Lista para a edição de seu cadastro
        final Intent intent = this.getIntent();
        if(intent.hasExtra("aluno")){
            Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
            helper.carregaAluno(aluno);
        }

        //Criando ação relizada quanto clicado no botão para adicionar foto
        Button foto = helper.getFotoBotao();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                    Indica o local de armazenamento e nome do arquivo a ser gerado
                      # "getExternalFilesDir" indica o local onde fica armazenado a aplicação
                      # pode-se ao invés de definir NULL, indicar o nome de uma subpasta
                 */
                localArquivoFoto = getExternalFilesDir(null)+"/"+System.currentTimeMillis()+".jpg";
                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));

                Intent intentCamera = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra( MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(intentCamera, cameraCODE);
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //É feita a chamada do menu em um metodo especifico

        //É inflado o menu e indicado o layout do mesmo a ser utilizado
        MenuInflater inflater = getMenuInflater();
        //Deve apontar o XML a ser utilizado e em qual menu será aplicado
        inflater.inflate(R.menu.formulario_menu, menu);

        /*
        * No retorno é indicado se o menu será exibido ou não
        * neste caso é possivel criar regra indicando se o menu será exibido (TRUE)
        * ou não (FALSE), interessante sua utilização onde o mesmo deva ser apresentado
        * somente para clientes logado na aplicação, por exmeplo.
        *
        * No caso aplicado abaixo o mesmo sempre será exibido
        */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Define as ações dependendo do item selecionado do menu

        switch (item.getItemId()){
            case R.id.menu_salvar: //Os comentários das ações do botão salvar estão no onCreate (inicio do curso)

                //Cria Aluno utilizando o Helper
                Aluno aluno = helper.criaAluno();

                if(helper.temNome()){

                    //Instancia um novo aluno DAO para fazer o insert na base
                    AlunoDAO alunoDao = new AlunoDAO(FormularioActivity.this);

                    if(aluno.getId() == 0){
                        //É feito a inserção do aluno na base de dados
                        alunoDao.insereAlunoDB(aluno);
                        Toast.makeText(FormularioActivity.this, "Aluno "+aluno.getNome()+" salvo",Toast.LENGTH_SHORT).show();
                    } else {
                        //É feito a alteração do aluno na base de dados
                        alunoDao.alteraAluno(aluno);
                        Toast.makeText(FormularioActivity.this, "Aluno "+aluno.getNome()+" alterado",Toast.LENGTH_SHORT).show();
                    }

                    //Sempre que houver interação com banco da dados é importante fechar o mesmo
                    alunoDao.close();

                    finish();

                    return true;

                } else {

                    helper.mostraErro();

                }

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == cameraCODE){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }
}
