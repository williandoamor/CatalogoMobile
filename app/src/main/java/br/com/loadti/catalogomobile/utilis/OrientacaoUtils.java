package br.com.loadti.catalogomobile.utilis;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by TI on 11/02/2016.
 */
public class OrientacaoUtils {

    /*Verifica qual a orientacao da tela*/
    public static int getOrientacao(Context context) {

        int orientacao = context.getResources().getConfiguration().orientation;
        return orientacao;
    }

    /*verifica se a orientacao da tela esta na vertical*/
    public static boolean isVertical(Context context) {


        int orientacao = context.getResources().getConfiguration().orientation;
        boolean vertical = orientacao == Configuration.ORIENTATION_PORTRAIT;
        return vertical;

    }

    /*Verifica se a orientacao da tela esta na horizontal*/
    public static boolean isHorizontal(Context context) {

        int orientacao = context.getResources().getConfiguration().orientation;
        boolean horizontal = orientacao == Configuration.ORIENTATION_LANDSCAPE;
        return horizontal;


    }
}
