package com.example.appdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appdemo.R;
import com.example.appdemo.adapter.UsuarioAdapter;
import com.example.appdemo.config.Globais;
import com.example.appdemo.controller.UsuarioController;
import com.example.appdemo.model.TipoUsuario;
import com.example.appdemo.model.Usuario;

import java.util.ArrayList;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class UsuarioActivity extends AppCompatActivity {

    Context context;
    Usuario objeto;
    TextView lblId;
    EditText txtLogin, txtSenha, txtEmail;
    Spinner spiTipoUsuario;
    MaskedEditText txtTelefone;
    UsuarioController usuarioController;
    Usuario objUsuario;
    Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        context = UsuarioActivity.this;

        lblId = findViewById(R.id.lblId_usuario);
        txtLogin = findViewById(R.id.txtLogin_usuario);
        txtSenha = findViewById(R.id.txtSenha_usuario);
        txtEmail = findViewById(R.id.txtEmail_usuario);
        txtTelefone = findViewById(R.id.txtTelefone_usuario);
        spiTipoUsuario = findViewById(R.id.spiTipoUsuario_usuario);
        btnExcluir = findViewById(R.id.btnExcluir_usuario);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioController = new UsuarioController(context);
                objeto.setId(Integer.parseInt(lblId.getText().toString()));
                boolean retorno = usuarioController.excluir(objeto);
                if(retorno){
                    Globais.exibirMensagem(context, "Usuário excluído com sucesso");
                    finish();
                }
            }
        });

        ArrayList<TipoUsuario> array_tipos = new ArrayList<>();

        array_tipos.add(new TipoUsuario(0, "Selecione..."));
        array_tipos.add(new TipoUsuario(1, "Administrador"));
        array_tipos.add(new TipoUsuario(2, "Usuário"));

        ArrayAdapter<TipoUsuario> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, array_tipos);

        spiTipoUsuario.setAdapter(adapter);

        //EXEMPLO COM LISTA DO STRINGS.XML
        //String[] array_tipos = getResources().getStringArray(R.array.tipo_usuario);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
        //       android.R.layout.simple_dropdown_item_1line, array_tipos);

        //EXEMPLO COM BUSCA NO BANCO DE DADOS
        /*UsuarioController controller = new UsuarioController(context);
        ArrayList<Usuario> lista = controller.lista();
        if(lista != null){

            ArrayAdapter adapter = new UsuarioAdapter(context, lista);

            spiTipoUsuario.setAdapter(adapter);

        }*/

        //capturar o id da tela de listagem
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getInt("id") > 0){
            int codigo = bundle.getInt("id");
            usuarioController = new UsuarioController(context);
            objUsuario = usuarioController.buscar(codigo);
            if(objUsuario != null){
                preencheCampos(objUsuario);
            }

        }else{
            btnExcluir.setVisibility(View.INVISIBLE);
        }

    }

    //Funcao para inflar o menu na tela
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Funcao que captura o clique em algum item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean retorno;

        switch (id){

            case R.id.action_ok:

                objUsuario = new Usuario();
                UsuarioController objController = new UsuarioController(context);

                TipoUsuario tipoUsuario = (TipoUsuario) spiTipoUsuario.getSelectedItem();
                if(tipoUsuario.getId() == 0){
                    Globais.exibirMensagem(context, "Informe o tipo do usuário");
                    break;
                }

                objUsuario.setLogin(txtLogin.getText().toString());
                objUsuario.setEmail(txtEmail.getText().toString());
                objUsuario.setTelefone(txtTelefone.getRawText());
                objUsuario.setTipo(tipoUsuario.getId());

                String senhaMd5 = Globais.md5(txtSenha.getText().toString());
                objUsuario.setSenha(senhaMd5);

                if(!lblId.getText().equals("ID")){
                    //alterar
                    objUsuario.setId(Integer.parseInt(lblId.getText().toString()));
                    retorno = objController.alterar(objUsuario);
                    if(retorno){
                        Globais.exibirMensagem(context, "Usuário alterado com sucesso");
                    }
                }else{
                    //incluir
                    retorno = objController.incluir(objUsuario);
                    if(retorno){
                        Globais.exibirMensagem(context, "Usuário incluído com sucesso");
                    }
                }

                finish();

            case R.id.action_cancel:
                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheCampos(Usuario objeto){
        try{

            lblId.setText(String.valueOf(objeto.getId()));
            txtLogin.setText(objeto.getLogin());
            txtLogin.setEnabled(false);

            txtTelefone.setText(objeto.getTelefone());
            txtEmail.setText(objeto.getEmail());

            for (int i = 0; i < spiTipoUsuario.getAdapter().getCount(); i++) {
                TipoUsuario wPos = (TipoUsuario) spiTipoUsuario.getItemAtPosition(i);
                if (wPos.getId() == objeto.getTipo()) {
                    spiTipoUsuario.setSelection(i);
                    break;
                }
            }

            //TipoUsuario objTipo = objControllerTipoUsuario.buscar(objeto.getTipo());
            //Globais.selecionaSpinner(spiTipoUsuario, objTipo);


        }catch (Exception e){
            Globais.exibirMensagem(context, e.getMessage());
        }
    }
}