package com.example.appdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdemo.R;
import com.example.appdemo.controller.UsuarioController;
import com.example.appdemo.model.Usuario;

import java.util.ArrayList;

public class ListaUsuariosActivity extends AppCompatActivity {

    ListView ltvUsuarios;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        context = ListaUsuariosActivity.this;
        ltvUsuarios = findViewById(R.id.ltvUsuarios_listaUsuarios);

        atualizarLista();

    }

    private void atualizarLista(){
        try{

            //buscar todos os usuarios e preencher em um List
            UsuarioController controller = new UsuarioController(context);
            ArrayList<Usuario> lista = controller.lista();
            if(lista != null){
                ArrayAdapter<Usuario> adapter =
                        new ArrayAdapter<Usuario>(context, android.R.layout.simple_list_item_1, lista);

                ltvUsuarios.setAdapter(adapter);

            }

        }catch (Exception ex){

        }
    }
}