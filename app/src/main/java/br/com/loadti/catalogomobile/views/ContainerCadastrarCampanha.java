package br.com.loadti.catalogomobile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import br.com.loadti.catalogomobile.Fragments.FragFotoProduto;


import br.com.loadti.catalogomobile.Fragments.FragcadastrareditarCampanha;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;

/**
 * Created by TI on 06/01/2016.
 */
public class ContainerCadastrarCampanha extends AppCompatActivity {
    private Toolbar mToolbarCadCampanha;
    private CampanhaSerial campanha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actcontainer_cadastar_campanha);

        mToolbarCadCampanha = (Toolbar) findViewById(R.id.tb_main_cadCampanha);
        this.setSupportActionBar(mToolbarCadCampanha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.cadastroCampanha);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getIntent().getExtras() != null) {

            campanha = (CampanhaSerial) getIntent().getSerializableExtra("campanha");
            Log.v("CadCampanha - onCreate", " Nome campanha passado por parametro " + campanha.getNomeCampanha());


        }


        // FRAGMENT
        FragcadastrareditarCampanha frag = (FragcadastrareditarCampanha) getSupportFragmentManager().findFragmentByTag("CampanhaFrag");
        if (frag == null) {
            frag = new FragcadastrareditarCampanha();
            Bundle arg = new Bundle();
            arg.putSerializable("campanha", campanha);
            frag.setArguments(arg);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragmentcadCampanha_container, frag, "CampanhaFrag");
            ft.commit();
        }
    }


    /*
* Ação ao pressionar o botão voltar para sair da app
*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {


            AlertDialog.Builder alerta = new AlertDialog.Builder(ContainerCadastrarCampanha.this);
            alerta.setCancelable(false);
            alerta.setTitle("CONFIRMA");
            alerta.setMessage("Deseja realmente sair sem salvar?");
            alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();


                }
            });

            alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = alerta.create();
            alert.show();
        }


        return super.onKeyDown(keyCode, event);
    }


}
