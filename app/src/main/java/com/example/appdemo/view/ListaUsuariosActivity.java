package com.example.appdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdemo.R;
import com.example.appdemo.adapter.UsuarioAdapter;
import com.example.appdemo.config.Globais;
import com.example.appdemo.controller.UsuarioController;
import com.example.appdemo.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaUsuariosActivity extends AppCompatActivity {

    ListView ltvUsuarios;
    FloatingActionButton btnAddUsuario;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        context = ListaUsuariosActivity.this;
        ltvUsuarios = findViewById(R.id.ltvUsuarios_listaUsuarios);
        btnAddUsuario = findViewById(R.id.btnAddUsuario_lista_usuario);

        btnAddUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globais.abrirActivity(context, UsuarioActivity.class, false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista(){
        try{

            //buscar todos os usuarios e preencher em um List
            UsuarioController controller = new UsuarioController(context);
            ArrayList<Usuario> lista = controller.lista();
            if(lista != null){

                //ArrayAdapter<Usuario> adapter =
                //      new ArrayAdapter<Usuario>(context, android.R.layout.simple_list_item_1, lista);

                ArrayAdapter adapter = new UsuarioAdapter(context, lista);

                ltvUsuarios.setAdapter(adapter);

            }

        }catch (Exception ex){

        }
    }
}