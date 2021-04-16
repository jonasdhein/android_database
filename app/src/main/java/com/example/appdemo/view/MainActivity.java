package com.example.appdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appdemo.R;

public class MainActivity extends AppCompatActivity {

    Button btnMenuUsuarios;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        btnMenuUsuarios = findViewById(R.id.btnMenuUsuarios_menu);

        btnMenuUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navegar para a tela de lista de usuários
                Intent tela = new Intent(context, ListaUsuariosActivity.class);
                startActivity(tela);
            }
        });
    }
}