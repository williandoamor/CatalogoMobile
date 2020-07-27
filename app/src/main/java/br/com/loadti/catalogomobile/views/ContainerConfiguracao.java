package br.com.loadti.catalogomobile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.Externalizable;
import java.util.ArrayList;

import br.com.loadti.catalogomobile.DAO.ConfigGeralDAO;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Fragments.FragConfigGeral;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import br.com.loadti.catalogomobile.Fragments.FragConfigGeral.callbackConfigGeral;

/**
 * Created by TI on 15/12/2015.
 */
public class ContainerConfiguracao extends AppCompatActivity implements MaterialTabListener, callbackConfigGeral {

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapterConfiguracao adapter;
    private FragConfigGeral fragConfigGeral;

    /*Objetos Configracao Geral*/
    private ConfigGeralSerial configGeral;
    private ArrayList<ConfigGeralSerial> aConfGeral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.container_configuracao);

        inicializarObjetos();

        preencherConfiguracaoGeral();

        // FRAGMENT
        //FragConfigGeral frag = (FragConfigGeral) getSupportFragmentManager().findFragmentByTag("fragConfigGeral");
        //if (frag == null) {
        //   frag = new FragConfigGeral();
        //   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //   ft.replace(R.id.rl_fragment_container_configuracao, frag, "fragConfigGeral");
        //   ft.commit();
        // }

        ativarTabs();
    }

    private void inicializarObjetos() {

        configGeral = new ConfigGeralSerial();
        aConfGeral = new ArrayList<ConfigGeralSerial>();
        aConfGeral = new ConfigGeralDAO(ContainerConfiguracao.this).pesqConfigGeral();


    }

    /*Preencher o objeto configuracao Geral*/
    private void preencherConfiguracaoGeral() {

        for (int i = 0; i < aConfGeral.size(); i++) {

            configGeral.setId_configGeral(aConfGeral.get(i).getId_configGeral());
            configGeral.setHost(aConfGeral.get(i).getHost());
            configGeral.setPortaHost(aConfGeral.get(i).getPortaHost());
            configGeral.setCasaDecimalVenda(aConfGeral.get(i).getCasaDecimalVenda());
            configGeral.setTabelaPadrao(aConfGeral.get(i).getTabelaPadrao());
            configGeral.setMostrarPreco(aConfGeral.get(i).getMostrarPreco());
            configGeral.setVisualizacao(aConfGeral.get(i).getVisualizacao());
            configGeral.setCodCampanhaPadrao(aConfGeral.get(i).getCodCampanhaPadrao());

        }

    }


    /*Verifica se o usuario apertou o botao voltar do teclado*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(ContainerConfiguracao.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_salvar_cancelar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.salvar:

                salvarConfiguracao();
                return true;

            case R.id.cancelar:

                cancelarIncluir();

                return true;
        }

        return false;
    }

    /*Cancela o salvamento das configuracoes realizadas*/
    private void cancelarIncluir() {

        AlertDialog.Builder alert = new AlertDialog.Builder(ContainerConfiguracao.this);
        alert.setTitle("Atenção");
        alert.setMessage(R.string.sair_sem_salvar);
        alert.setCancelable(false);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();


            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alerta = alert.create();
        alerta.show();


    }

    private void salvarConfiguracao() {

        AlertDialog.Builder aler = new AlertDialog.Builder(ContainerConfiguracao.this);
        aler.setTitle("Informação");
        aler.setMessage(R.string.confirmaSalvar);
        aler.setCancelable(false);
        aler.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {

                    if (fragConfigGeral != null) {

                        if (fragConfigGeral.isVisible()) {

                            fragConfigGeral.getDadosConfGeral();
                            Log.v("ContConfiguracao - ", " Host de destino " + configGeral.getHost());

                        }

                        salvaDados();
                    }

                } catch (Exception e) {

                    AndroidUtils.alertDialog(ContainerConfiguracao.this, "Erro ao salvar as configuracões do sistema" + e.toString());


                }

            }
        });

        aler.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alerta = aler.create();
        alerta.show();


    }

    /*Salva os dados da configuracao*/
    private void salvaDados() throws Exception {

        try {
            /*Salva a configuracao Geral*/
            ConfigGeralDAO salvarconfig = new ConfigGeralDAO(ContainerConfiguracao.this, configGeral);
            salvarconfig.updateConfigGeral();

            /*Cria um alerta para informar ao usuaro que as configuracoes foram salvas*/
            AlertDialog.Builder builder = new AlertDialog.Builder(ContainerConfiguracao.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.informação);
            builder.setMessage(R.string.registroAtualizado);
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    setResult(RESULT_OK);
                    finish();
                }
            });

            AlertDialog alerta = builder.create();
            alerta.show();


        } catch (Exception e) {

            throw e;
        }


    }

    private void ativarTabs() {

        Toolbar barConfiguracao = (Toolbar) this.findViewById(R.id.tb_main_configuracao);
        if (barConfiguracao != null) {
            this.setSupportActionBar(barConfiguracao);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setTitle(R.string.configuracoes);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            tabHost = (MaterialTabHost) this.findViewById(R.id.tabHostConfiguracao);
            pager = (ViewPager) this.findViewById(R.id.pagerConfiguracao);

            // inicializa o pager view pager
            adapter = new ViewPagerAdapterConfiguracao(getSupportFragmentManager());
            pager.setAdapter(adapter);

            pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {


                @Override
                public void onPageSelected(int position) {
                      /*Quando o usuario cliar muda a guia*/
                    tabHost.setSelectedNavigationItem(position);


                }

            });


            // insert all tabs from pagerAdapter data
            for (int i = 0; i < adapter.getCount(); i++) {
                tabHost.addTab(tabHost.newTab().setText(adapter.getPageTitle(i)).setTabListener(this));

            }

        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {

        pager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public ConfigGeralSerial getConfGeral() {
        return configGeral;
    }

    @Override
    public void setConfGeral(ConfigGeralSerial confGeral) {

        this.configGeral = confGeral;

    }

    private class ViewPagerAdapterConfiguracao extends FragmentPagerAdapter {


        public ViewPagerAdapterConfiguracao(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    Log.d("ContainerConfiguracao", "Retorno FragConfig " + num);
                    fragConfigGeral = new FragConfigGeral();
                    return fragConfigGeral;

                default:
            }
            return null;

        }


        @Override
        public int getCount() {
            return 1;
        }


        @Override
        public CharSequence getPageTitle(int position) {


            switch (position) {
                case 0:
                    Log.d("ContainerConfiguracao", "Retorno" + position);
                    return "";


                default:

            }

            return null;

        }


    }


}
