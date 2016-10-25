package br.com.caelum.cadastro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

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

                EditText viewNome = (EditText) findViewById(R.id.formulario_nome);
                String nome = viewNome.getText().toString();
                Toast.makeText(FormularioActivity.this, "Aluno "+nome+" salvo com sucesso",Toast.LENGTH_SHORT).show();

                finish();

                return false;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
