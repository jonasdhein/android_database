package com.example.appdemo.controller;

import android.content.ContentValues;
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
                objeto.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
                objeto.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow("telefone")));
                objeto.setTipo(resultado.getInt(resultado.getColumnIndexOrThrow("tipo")));
            }

            return objeto;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return null;
        }
    }

    public Usuario buscar(int id){
        try{

            Usuario objeto = null;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(Tabelas.TB_USUARIOS);
            sql.append(" WHERE id = '"+ id +"'");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);
            if(resultado.moveToNext()){
                objeto = new Usuario();
                objeto.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
                objeto.setLogin(resultado.getString(resultado.getColumnIndexOrThrow("login")));
                objeto.setSenha(resultado.getString(resultado.getColumnIndexOrThrow("senha")));
                objeto.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
                objeto.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow("telefone")));
                objeto.setTipo(resultado.getInt(resultado.getColumnIndexOrThrow("tipo")));
            }

            return objeto;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return null;
        }
    }

    public boolean incluir(Usuario objeto){
        try{

            ContentValues valores = new ContentValues();
            valores.put("login", objeto.getLogin());
            valores.put("senha", objeto.getSenha());
            valores.put("email", objeto.getEmail());
            valores.put("telefone", objeto.getTelefone());
            valores.put("tipo", objeto.getTipo());

            conexao.insertOrThrow(Tabelas.TB_USUARIOS, null, valores);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
        }
    }

    public boolean alterar(Usuario objeto){
        try{

            ContentValues valores = new ContentValues();
            valores.put("login", objeto.getLogin());
            valores.put("senha", objeto.getSenha());
            valores.put("email", objeto.getEmail());
            valores.put("telefone", objeto.getTelefone());
            valores.put("tipo", objeto.getTipo());

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(objeto.getId());

            conexao.update(Tabelas.TB_USUARIOS, valores, "id = ?" , parametros);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
        }
    }

    public boolean excluir(Usuario objeto){
        try{

            String[] parametros = new String[1];
            parametros[0] = String.valueOf(objeto.getId());

            conexao.delete(Tabelas.TB_USUARIOS, "id = ?", parametros);

            return true;

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
            return false;
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
                    objeto.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
                    objeto.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow("telefone")));
                    objeto.setTipo(resultado.getInt(resultado.getColumnIndexOrThrow("tipo")));

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
