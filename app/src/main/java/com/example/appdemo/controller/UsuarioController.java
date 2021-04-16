package com.example.appdemo.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdemo.config.DadosOpenHelper;
import com.example.appdemo.config.Globais;
import com.example.appdemo.config.Tabelas;
import com.example.appdemo.model.Usuario;

import java.util.ArrayList;

public class UsuarioController {

    private SQLiteDatabase conexao;
    private Context context;

    public UsuarioController(Context context){
        DadosOpenHelper banco = new DadosOpenHelper(context);
        this.conexao = banco.getWritableDatabase();
        this.context = context;
    }

    //métodos (buscar, listar, alterar, incluir, excluir)
    public Usuario login(String usuario, String senha){
        try{

            Usuario objeto = null;

            senha = Globais.md5(senha);

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(Tabelas.TB_USUARIOS);
            sql.append(" WHERE login = '"+ usuario +"'");
            sql.append(" AND senha = '"+ senha +"'");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToNext()){
                objeto = new Usuario();
                objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                objeto.setLogin(resultado.getString(resultado.getColumnIndexOrThrow("login")));
                objeto.setSenha(resultado.getString(resultado.getColumnIndexOrThrow("senha")));
            }

            return objeto;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return null;
        }
    }

    public ArrayList<Usuario> lista(){

        ArrayList<Usuario> listagem = new ArrayList<>();
        try{

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(Tabelas.TB_USUARIOS);

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToFirst()){

                Usuario objeto;
                do{
                    objeto = new Usuario();
                    objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                    objeto.setLogin(resultado.getString(resultado.getColumnIndexOrThrow("login")));

                    listagem.add(objeto);

                }while (resultado.moveToNext());

            }

            return listagem;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return listagem;
        }
    }

}
