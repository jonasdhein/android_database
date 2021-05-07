package com.example.appdemo.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.appdemo.view.ListaUsuariosActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Globais {

    public static void exibirMensagem(Context context, String mensagem){
        try{
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
        }catch (Exception ex){

        }
    }

    public static String md5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(texto.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void abrirActivity(Context context, Class destino, boolean fechar){
        try{
            Activity act = (Activity) context;

            Intent tela = new Intent(context, destino);
            context.startActivity(tela);
            if(fechar){
                act.finish();
            }

        }catch (Exception ex){

        }
    }

}
