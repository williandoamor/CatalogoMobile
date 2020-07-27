package br.com.loadti.catalogomobile.views;


import android.os.PersistableBundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;


import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.io.Serializable;

import br.com.loadti.catalogomobile.Fragments.FragFotoProduto;
import br.com.loadti.catalogomobile.R;

import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;


public class Principal extends AppCompatActivity {
    private static String TAG = "LOG";
    private Toolbar mToolbar;
    private UsuarioSerial usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           /*Faz com que o Android nao bloqueie a tela*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState != null) {

            usuario = (UsuarioSerial) savedInstanceState.getSerializable("user");

        } else {

            usuario = new UsuarioSerial();

        }

        if (getIntent().getExtras() != null) {

            usuario = (UsuarioSerial) getIntent().getSerializableExtra("usuario");
        }


        // FRAGMENT
        Fragment frag = (FragFotoProduto) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if (frag == null) {
            frag = new FragFotoProduto();
            /*Passa parametro para o fragment de fotos*/
            Bundle arg = new Bundle();
            arg.putSerializable("usuario", usuario);
            frag.setArguments(arg);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();


        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (outState == null) {

            outState = new Bundle();

        }

        outState.putSerializable("user", usuario);

        Log.d("Principal - SavedInsta", "usuario " + outState.getSerializable("user"));


        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //savedInstanceState.putSerializable("user", usuario);
        usuario = (UsuarioSerial) savedInstanceState.getSerializable("user");
    }

    /* Ação ao pressionar o botão voltar para sair da app*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AndroidUtils.sairActivity(Principal.this);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (usuario != null) {

            Log.d("Principal", "onResume " + usuario.getNomeUser());

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Principal", "onPause" + usuario.getNomeUser());


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Principal", "onDestroy");
    }

}
