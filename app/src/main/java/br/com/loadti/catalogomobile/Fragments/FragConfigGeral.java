package br.com.loadti.catalogomobile.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.views.PesquisaCampanha;

/**
 * Created by TI on 15/12/2015.
 */
public class FragConfigGeral extends Fragment {

    private static final String TAG = "ConfiGeral";
    private MaterialEditText edtHost, edtPortaConexao, edtCompanhaPadrao;
    private Spinner spnCasaDecPreco, spntabelaPadrao, spnVisualizacaoPadrao;
    private android.widget.CheckBox chkmostrarprecovenda;
    private Adapter adapterTabelaPreco;
    private Adapter adapterCasasDecVenda;
    private Adapter adapterVisualizacao;
    private ConfigGeralSerial configGeral;
    private boolean mInicializarVisualizacao = false;
    private boolean mInicializarCadasDecimais = false;
    private boolean mInicializarTabela = false;
    private TextView tvTabelaPadrao, tvCadasDecimais;
    private Button btnPesquisaCampanha, btnRemoverCampanha;

    callbackConfigGeral iConfGeral;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_config_geral, container, false);

        /*Componentes*/
        edtHost = (MaterialEditText) view.findViewById(R.id.edtHostConexao);
        edtPortaConexao = (MaterialEditText) view.findViewById(R.id.edtPortaConexao);
        tvTabelaPadrao = (TextView) view.findViewById(R.id.tvTabelaPadrao);
        tvCadasDecimais = (TextView) view.findViewById(R.id.tvcasasdecimaispreco);
        edtCompanhaPadrao = (MaterialEditText) view.findViewById(R.id.edtCompanhaPadrao);

        /*Spinners*/
        spnCasaDecPreco = (Spinner) view.findViewById(R.id.spnCasasDecimais);
        spntabelaPadrao = (Spinner) view.findViewById(R.id.spnTabelaPradao);
        spnVisualizacaoPadrao = (Spinner) view.findViewById(R.id.spnVisualizacaoPadrao);

        /*checkbox*/
        chkmostrarprecovenda = (CheckBox) view.findViewById(R.id.chkmostraprecovenda);

        /*Button*/
        btnPesquisaCampanha = (Button) view.findViewById(R.id.btnPesquisaCampanha);
        btnRemoverCampanha = (Button) view.findViewById(R.id.btnRemoverCampanha);


        /*Inicializar objetos*/
        configGeral = new ConfigGeralSerial();

        /*Preenche o spinner com a as tabelas de preco existentes*/
        escolherTabela();

        /*Preenche o spiner com as casas decimais das tabelas de preco*/
        formarPreco();

        /*Exibi as views para formacao de preco*/
        mostrarPreco();

        /*Exibi as views para preencher visualizacao*/
        escolherVisualizacao();

        /*Busca as campanha cadastradas*/
        pesquisarCampanha();

        /*Remove a campanha padrao*/
        removerCampanhaPadrao();

        return view;
    }

    /*Remove a campanha padrao*/
    private void removerCampanhaPadrao() {

        btnRemoverCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt = new AlertDialog.Builder(getActivity());
                alt.setCancelable(false);
                alt.setTitle("CONFIRMA");
                alt.setMessage("Deseja mesmo remover a campanha padrão?");
                alt.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        configGeral.setCodCampanhaPadrao(0);
                        edtCompanhaPadrao.setText("");


                    }
                });

                alt.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = alt.create();
                dialog.show();


            }
        });

    }

    private void mostrarPreco() {

/*
        chkmostrarprecovenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    setDadosConfGeral();
                    getActivity().findViewById(R.id.tvTabelaPadrao).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.spnTabelaPradao).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.tvcasasdecimaispreco).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.spnCasasDecimais).setVisibility(View.VISIBLE);

                } else {

                    if (tvTabelaPadrao.getVisibility() == View.VISIBLE) {

                        getActivity().findViewById(R.id.tvTabelaPadrao).setVisibility(View.GONE);

                    }
                    // getActivity().findViewById(R.id.spnTabelaPradao).setVisibility(View.GONE);
                    // getActivity().findViewById(R.id.tvcasasdecimaispreco).setVisibility(View.GONE);
                    //  getActivity().findViewById(R.id.spnCasasDecimais).setVisibility(View.GONE);
                    // setDadosConfGeral();

                }

            }
        });*/

    }


    public void setDadosConfGeral() {

         /*Seta false para a vairavel mInicializa, para que o spinner nao
       * chama o setOnItemSelectedListener automaticamente*/
        mInicializarTabela = false;
        mInicializarCadasDecimais = false;
        mInicializarVisualizacao = false;

        try {

            configGeral.setId_configGeral(iConfGeral.getConfGeral().getId_configGeral());
            configGeral.setHost(iConfGeral.getConfGeral().getHost());
            configGeral.setPortaHost(iConfGeral.getConfGeral().getPortaHost());
            configGeral.setMostrarPreco(iConfGeral.getConfGeral().getMostrarPreco());
            configGeral.setTabelaPadrao(iConfGeral.getConfGeral().getTabelaPadrao());
            configGeral.setCasaDecimalVenda(iConfGeral.getConfGeral().getCasaDecimalVenda());
            configGeral.setVisualizacao(iConfGeral.getConfGeral().getVisualizacao());
            configGeral.setCodCampanhaPadrao(iConfGeral.getConfGeral().getCodCampanhaPadrao());


            edtPortaConexao.setText(String.valueOf(configGeral.getPortaHost()));
            edtHost.setText(configGeral.getHost());


            if (configGeral.getMostrarPreco().equals("S")) {

                chkmostrarprecovenda.setChecked(true);
                getActivity().findViewById(R.id.tvTabelaPadrao).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.spnTabelaPradao).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.tvcasasdecimaispreco).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.spnCasasDecimais).setVisibility(View.VISIBLE);


            } else if (configGeral.getMostrarPreco().equals("N")) {

                chkmostrarprecovenda.setChecked(false);
                getActivity().findViewById(R.id.tvTabelaPadrao).setVisibility(View.GONE);
                getActivity().findViewById(R.id.spnTabelaPradao).setVisibility(View.GONE);
                getActivity().findViewById(R.id.tvcasasdecimaispreco).setVisibility(View.GONE);
                getActivity().findViewById(R.id.spnCasasDecimais).setVisibility(View.GONE);

            }

            adapterTabelaPreco = ArrayAdapter.createFromResource(getActivity(), R.array.tabelaPrecos, android.R.layout.simple_spinner_item);
            ((ArrayAdapter) adapterTabelaPreco).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spntabelaPadrao.setAdapter((android.widget.SpinnerAdapter) adapterTabelaPreco);

            for (int i = 0; i < adapterTabelaPreco.getCount(); i++) {

                Log.v("ConfGeral", " Posicao do adapter Tabela " + i);
                Log.v("ConfFatu", " Tabela de Preco no objeto " + configGeral.getTabelaPadrao());
                if (adapterTabelaPreco.getItemId(i) == configGeral.getTabelaPadrao()) {

                    Log.v("FragConfGeral ", " Casas Dec Qtde no adapter" + adapterTabelaPreco.getItemId(i));
                    spntabelaPadrao.setSelection(i);
                }

            }

            adapterCasasDecVenda = ArrayAdapter.createFromResource(getActivity(), R.array.casadecformacaoPreco, android.R.layout.simple_spinner_item);
            ((ArrayAdapter) adapterCasasDecVenda).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCasaDecPreco.setAdapter((android.widget.SpinnerAdapter) adapterCasasDecVenda);

            for (int i = 0; i < adapterCasasDecVenda.getCount(); i++) {

                Log.v("FragConfGeral ", " Casas Decimais Preco" + configGeral.getCasaDecimalVenda());
                Log.v("FragConfGeral ", " Casas Dec Adapter Preco" + adapterCasasDecVenda.getItemId(i));
                if (adapterCasasDecVenda.getItem(i).equals(String.valueOf(configGeral.getCasaDecimalVenda()))) {
                    Log.v("FragConfGeral ", " Casas Preco " + adapterCasasDecVenda.getItemId(i));
                    spnCasaDecPreco.setSelection(i);

                }


            }

            /*Adapter para mostrar a visualizacao da quantidade de imagem a exibir*/
            adapterVisualizacao = ArrayAdapter.createFromResource(getActivity(), R.array.vizualizarimagem, android.R.layout.simple_spinner_item);
            ((ArrayAdapter) adapterVisualizacao).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnVisualizacaoPadrao.setAdapter((android.widget.SpinnerAdapter) adapterVisualizacao);

            for (int i = 0; i < adapterVisualizacao.getCount(); i++) {

                Log.v("FragConfGeral ", " Casas Decimais Preco" + configGeral.getVisualizacao());
                Log.v("FragConfGeral ", " Casas Dec Adapter Preco" + adapterVisualizacao.getItemId(i));
                if (adapterVisualizacao.getItem(i).equals(String.valueOf(configGeral.getVisualizacao()))) {
                    Log.v("FragConfGeral ", " Casas Preco " + adapterVisualizacao.getItemId(i));
                    spnVisualizacaoPadrao.setSelection(i);

                }


            }

            /*Seta a campanha padrao no edt*/
            buscarCampanhaid();


        } catch (Exception e) {

            Log.e("FragConfGeral - ", "Erro ao copiar dados " + e.toString());
            AndroidUtils.alertDialog(getActivity(), "ConfGeral - Erro ao copiar os dados " + e.toString());

        }

    }

    public void getDadosConfGeral() {

        configGeral.setHost(edtHost.getText().toString().toUpperCase());
        configGeral.setPortaHost(Integer.parseInt(edtPortaConexao.getText().toString()));
        if (chkmostrarprecovenda.isChecked()) {

            configGeral.setMostrarPreco("S");

        } else {

            configGeral.setMostrarPreco("N");

        }

        iConfGeral.setConfGeral(configGeral);

    }


    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "Geral on Resume");
        setDadosConfGeral();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.v(TAG, "Geral on Pause.");
        getDadosConfGeral();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        try {

            if (context instanceof Activity) {


                activity = (Activity) context;
                iConfGeral = (callbackConfigGeral) activity;

            }


        } catch (ClassCastException e) {


            throw new ClassCastException("Falta implementar o médodo callbackConfGeral" + e.toString());
        }


    }

    /*Buscar o nome da campanha pelo id*/
    private void buscarCampanhaid() {

        ArrayList<CampanhaSerial> aCamp = new ArrayList<CampanhaSerial>();
        CampanhaSerial camp = new CampanhaSerial();

        CampanhaDAO dao = new CampanhaDAO(getActivity());
        aCamp = dao.buscaCampanhaConsulta("%%", 0, configGeral.getCodCampanhaPadrao());

        for (int i = 0; i < aCamp.size(); i++) {

            camp.setIdcampnha(aCamp.get(i).getIdcampnha());
            camp.setNomeCampanha(aCamp.get(i).getNomeCampanha());
            camp.setSituacao(aCamp.get(i).getSituacao());
        }

        edtCompanhaPadrao.setText(camp.getNomeCampanha());

    }

    /*Preenche o Spinner com os valores de casas decimais*/
    private void formarPreco() {

        adapterCasasDecVenda = ArrayAdapter.createFromResource(getActivity(), R.array.casadecformacaoPreco, android.R.layout.simple_spinner_item);
        ((ArrayAdapter) adapterCasasDecVenda).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCasaDecPreco.setAdapter((android.widget.SpinnerAdapter) adapterCasasDecVenda);
        spnCasaDecPreco.setPrompt("Seleciona a quantidade de casas decimais.");
        spnCasaDecPreco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInicializarCadasDecimais) {
                    int casas = Integer.parseInt(spnCasaDecPreco.getSelectedItem().toString());
                    configGeral.setCasaDecimalVenda(casas);
                    spnCasaDecPreco.setSelection(position);
                    Toast.makeText(getActivity(), "Casas selecionadas " + casas, Toast.LENGTH_LONG).show();
                }
                mInicializarCadasDecimais = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*Pesquisar campanha*/
    private void pesquisarCampanha() {

        btnPesquisaCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(getActivity(), PesquisaCampanha.class), 1);
            }
        });


    }

    //
    private void escolherTabela() {

        adapterTabelaPreco = ArrayAdapter.createFromResource(getActivity(), R.array.tabelaPrecos, android.R.layout.simple_spinner_dropdown_item);
        ((ArrayAdapter) adapterTabelaPreco).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spntabelaPadrao.setAdapter((android.widget.SpinnerAdapter) adapterTabelaPreco);
        spntabelaPadrao.setPrompt("Seleciona a quantidade de casas decimais.");
        spntabelaPadrao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mInicializarTabela) {
                    spntabelaPadrao.setSelection(position);
                    configGeral.setTabelaPadrao(position);
                    //spntabelaPadrao.setSelection(position);
                    Toast.makeText(getActivity(), "Casas selecionadas " + position, Toast.LENGTH_LONG).show();
                }
                mInicializarTabela = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*pega os dados vindos das telas abertas como result*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {

            if (data.getExtras() != null) {

                CampanhaSerial campanha = (CampanhaSerial) data.getSerializableExtra("campanha");
                configGeral.setCodCampanhaPadrao(campanha.getIdcampnha());
                edtCompanhaPadrao.setText(campanha.getNomeCampanha());

            }

        }
    }


    /*Adapter para preencher a quantidade de  visualizacao de imagem*/
    private void escolherVisualizacao() {

        adapterVisualizacao = ArrayAdapter.createFromResource(getActivity(), R.array.vizualizarimagem, android.R.layout.simple_spinner_item);
        ((ArrayAdapter) adapterVisualizacao).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVisualizacaoPadrao.setAdapter((android.widget.SpinnerAdapter) adapterVisualizacao);
        spnVisualizacaoPadrao.setPrompt("Seleciona a quantidade de casas decimais.");
        spnVisualizacaoPadrao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInicializarVisualizacao) {
                    int visualizar = Integer.parseInt(spnVisualizacaoPadrao.getSelectedItem().toString());
                    configGeral.setVisualizacao(visualizar);
                    spnVisualizacaoPadrao.setSelection(position);
                    Toast.makeText(getActivity(), "Visualizacao padrao " + visualizar, Toast.LENGTH_LONG).show();
                }
                mInicializarVisualizacao = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public interface callbackConfigGeral {

        public ConfigGeralSerial getConfGeral();

        public void setConfGeral(ConfigGeralSerial confGeral);

    }
}
