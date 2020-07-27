package br.com.loadti.catalogomobile.views;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.loadti.catalogomobile.Fragments.FragDadosUser;
import br.com.loadti.catalogomobile.Fragments.FragFotoProduto;
import br.com.loadti.catalogomobile.R;


/**
 * Created by TI on 26/03/2015.
 */
public class ContainerUsuario extends AppCompatActivity {

    private static String TAG = "LOG";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_usuario);

        mToolbar = (Toolbar) findViewById(R.id.tb_main_usuario);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // FRAGMENT
        FragDadosUser frag = (FragDadosUser) getSupportFragmentManager().findFragmentByTag("UserFrag");
        if (frag == null) {
            frag = new FragDadosUser();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container_usuario, frag, "UserFrag");
            ft.commit();
        }


    }


}









