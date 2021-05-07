package com.example.appdemo.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2; //versão do banco de dados
    private static final String NM_BANCO = "banco";
    private Context context;

    public DadosOpenHelper(Context context) {
        super(context, NM_BANCO, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            StringBuilder sqlUsuarios = new StringBuilder();
            sqlUsuarios.append(" CREATE TABLE IF NOT EXISTS ");
            sqlUsuarios.append(Tabelas.TB_USUARIOS);
            sqlUsuarios.append("(");
            sqlUsuarios.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlUsuarios.append("login VARCHAR(30) NOT NULL, ");
            sqlUsuarios.append("senha VARCHAR(100) NOT NULL, ");
            sqlUsuarios.append("email VARCHAR(100) NULL, ");
            sqlUsuarios.append("telefone VARCHAR(20) NULL ");
            sqlUsuarios.append(")");
            db.execSQL(sqlUsuarios.toString());

            StringBuilder sqlUserAdmin = new StringBuilder();
            sqlUserAdmin.append("INSERT INTO ");
            sqlUserAdmin.append(Tabelas.TB_USUARIOS);
            sqlUserAdmin.append("(login, senha) VALUES('jonas', '"+ Globais.md5("abc123") +"')");
            db.execSQL(sqlUserAdmin.toString());

        }catch (Exception ex){
            Globais.exibirMensagem(context, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{

            if(oldVersion == 1 && newVersion == 2) {
                StringBuilder sqlUpdade = new StringBuilder();
                sqlUpdade.append(" ALTER TABLE usuarios ADD COLUMN email VARCHAR(100) ");
                db.execSQL(sqlUpdade.toString());

                sqlUpdade = new StringBuilder();
                sqlUpdade.append(" ALTER TABLE usuarios ADD COLUMN telefone VARCHAR(20) ");
                db.execSQL(sqlUpdade.toString());
            }

        }catch (Exception ex){

        }
    }
}
