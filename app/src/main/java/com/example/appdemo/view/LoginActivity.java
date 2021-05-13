package com.example.appdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.config.Globais;
import com.example.appdemo.controller.UsuarioController;
import com.example.appdemo.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsuario, txtSenha;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        btnLogin = findViewById(R.id.btnLogin_login);
        txtUsuario = findViewById(R.id.txtUsuario_login);
        txtSenha = findViewById(R.id.txtSenha_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar dados de acesso
                if(!txtUsuario.getText().toString().equals("") &&
                        !txtSenha.getText().toString().equals("")) {

                    UsuarioController controller = new UsuarioController(context);
                    Usuario user = controller.login(txtUsuario.getText().toString(), txtSenha.getText().toString());
                    if (user != null) {

                        Intent tela = new Intent(context, MainActivity.class);
                        startActivity(tela);
                        finish();

                    } else {
                        Globais.exibirMensagem(context, "Usuário/Senha inválido");
                    }
                }else{
                    Globais.exibirMensagem(context, "Informe corretamente usuário e senha");
                }
            }
        });
    }
}