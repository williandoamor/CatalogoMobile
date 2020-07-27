package br.com.loadti.catalogomobile.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.Adapter.CardProdutoAdapter;
import br.com.loadti.catalogomobile.DAO.PerfilDAO;
import br.com.loadti.catalogomobile.DAO.UsuarioDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.PerfilUserSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.Sincronizador;
import br.com.loadti.catalogomobile.views.CadastrarCampanha;
import br.com.loadti.catalogomobile.views.ContainerConfiguracao;
import br.com.loadti.catalogomobile.views.ContainerUsuario;
import br.com.loadti.catalogomobile.views.PesquisaUsuario;


/**
 * Created by TI on 26/03/2015.
 */
public class FragDadosUser extends Fragment {

    private MaterialEditText edtNomeUser, edtDataCadUser, edtLoginUser, edtSenhaUser, edtConfirmaSenhaUser;
    private android.widget.CheckBox inativaUser;
    private Spinner spnPerfilUser;
    private PerfilUserSerial perfil;
    private ArrayList<PerfilUserSerial> lPerfil;
    private boolean mInicializa = false;
    private UsuarioSerial usuario;
    private Menu menu;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastro_usuario, container, false);
        setHasOptionsMenu(true);

        /*Edits*/
        edtNomeUser = (MaterialEditText) view.findViewById(R.id.edtNomeUser);
        edtDataCadUser = (MaterialEditText) view.findViewById(R.id.edtDataCadUser);
        edtLoginUser = (MaterialEditText) view.findViewById(R.id.edtLoginUser);
        edtSenhaUser = (MaterialEditText) view.findViewById(R.id.edtSenhaUser);
        edtConfirmaSenhaUser = (MaterialEditText) view.findViewById(R.id.edtConfirmaSenhaUser);


        /*InicializaObjetos*/
        usuario = new UsuarioSerial();


        /*Chebox*/
        inativaUser = (android.widget.CheckBox) view.findViewById(R.id.checkBoxInativaUser);

        /*Spinner*/
        spnPerfilUser = (Spinner) view.findViewById(R.id.spnPerfilUser);


        /*Instancia a lista de perfil*/
        lPerfil = new ArrayList<PerfilUserSerial>();

        /*seta true na vairavel mInicializa para que o spinner possa
        * realizar a busca e setar os nomes dos perfis*/
        mInicializa = true;

        /*Preenche o spinner com os perfils existentes*/
        preencheSpinner();

        Log.v("FragDadosUser", "OnCreate do FragDadosUser foi executado.");
        return view;
    }


    private void buscaUsuario() {


        startActivityForResult(new Intent(getActivity(), PesquisaUsuario.class), 4);

    }


    private void preencheSpinner() {

        lPerfil = new PerfilDAO(getActivity()).buscarPerfil(0, "%%", 0);

        ArrayAdapter<PerfilUserSerial> perfilAdapter = new ArrayAdapter<PerfilUserSerial>(getActivity(), android.R.layout.simple_spinner_item, lPerfil);
        perfilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPerfilUser.setPrompt("Seleciona o perfil");
        spnPerfilUser.setAdapter(perfilAdapter);

        spnPerfilUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mInicializa) {

                    ArrayList<PerfilUserSerial> aPerfil = new ArrayList<PerfilUserSerial>();
                    aPerfil = new PerfilDAO(getActivity()).buscarPerfil(0, "%%", 0);
                    perfil = aPerfil.get(position);
                    usuario.setPerfil(perfil);

                }
                mInicializa = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4 && resultCode == FragmentActivity.RESULT_OK) {

            if (data.getExtras() != null) {

                usuario = (UsuarioSerial) data.getSerializableExtra("usuario");
                setDados();


            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu = menu;
        inflater.inflate(R.menu.menu_usuario, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.salvar_usuario:
                getDados();
                salvarDados();

                return true;

            case R.id.cancelar_salvar_usuario:
                cancelarSalvar();
                return true;

            case R.id.pesquisar_usuario:
                buscaUsuario();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cancelarSalvar() {

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
        alerta.setCancelable(false);
        alerta.setTitle("CONFIRMA");
        alerta.setMessage("Deseja realmente sair sem salvar?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getActivity().finish();

            }
        });

        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alt = alerta.create();
        alt.show();

    }

    /*Salva o usuario*/
    private void salvarDados() {
      /*Verifica se o nome do usuario foi informado*/
        if (!usuario.getNomeUser().equals("")) {

            /*Verifica se o login do usuario foi informado*/
            if (!usuario.getLoginUser().equals("")) {

                if (!usuario.getSenhaUser().toString().toUpperCase().equals("")) {

                    if (!usuario.getConfirmaSenhaUser().toUpperCase().equals("")) {

                        if (!usuario.getSenhaUser().toString().toUpperCase().equals(usuario.getConfirmaSenhaUser().toString().toUpperCase())) {

                            AndroidUtils.alertDialog(getActivity(), "As senhas não conferem. Por favor confira e tente novamente.");
                        }/*Fim do if que verifica se a senha e diferente*/ else if (usuario.getSenhaUser().toString().toUpperCase().equals(usuario.getConfirmaSenhaUser().toString().toUpperCase())) {

                            salvarUsuario();

                        }


                    } else if (usuario.getConfirmaSenhaUser().toString().toUpperCase().equals("")) {

                        AndroidUtils.alertDialog(getActivity(), "A senha do usuário deve ser confirmada.");

                    }


                } else if (usuario.getSenhaUser().toString().toUpperCase().equals("")) {

                    AndroidUtils.alertDialog(getActivity(), "A senha do usuário deve ser informada.");
                }


            } else if (usuario.getLoginUser().equals("")) {


                AndroidUtils.alertDialog(getActivity(), "O login do usuário deve ser informado.");
            }

        } else if (usuario.getNomeUser().equals("")) {

            AndroidUtils.alertDialog(getActivity(), "O nome do usuário deve ser informado.");

        }


    }

    private void getDados() {

        usuario.setDataCadUser(usuario.getDataCadUser());
        usuario.setNomeUser(edtNomeUser.getText().toString().toUpperCase());
        usuario.setLoginUser(edtLoginUser.getText().toString().toUpperCase());
        usuario.setSenhaUser(edtSenhaUser.getText().toString().toUpperCase());
        usuario.setConfirmaSenhaUser(edtConfirmaSenhaUser.getText().toString().toUpperCase());

        if (inativaUser.isChecked()) {

            usuario.setStatusUser("I");

        } else {

            usuario.setStatusUser("A");

        }


    }

    private void setDados() {
        /*Seta false para a vairavel mInicializa, para que o spinner nao
       * chama o setOnItemSelectedListener automaticamente*/
        mInicializa = false;

        try {

            if (usuario.getId_usuario() == 0) {

                usuario.setDataCadUser(AndroidUtils.getDate("yyyy/MM/dd"));
                edtDataCadUser.setText(AndroidUtils.setdataPedido(usuario.getDataCadUser()));


            } else if (usuario.getId_usuario() > 0) {

                edtDataCadUser.setText(AndroidUtils.setdataPedido(usuario.getDataCadUser()));
                edtNomeUser.setText(usuario.getNomeUser());
                edtLoginUser.setText(usuario.getLoginUser());
                edtSenhaUser.setText(usuario.getSenhaUser());
                edtConfirmaSenhaUser.setText(usuario.getConfirmaSenhaUser());

                if (usuario.getStatusUser().equals("A")) {

                    inativaUser.setChecked(false);

                } else if (usuario.getStatusUser().equals("I")) {

                    inativaUser.setChecked(true);

                }

                /*Monta o adapter para mostrar o perfil do usuario*/
                ArrayAdapter<PerfilUserSerial> perfilAdapter = new ArrayAdapter<PerfilUserSerial>(getActivity(), android.R.layout.simple_spinner_item, lPerfil);
                perfilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnPerfilUser.setAdapter(perfilAdapter);


                for (int i = 0; i < perfilAdapter.getCount(); i++) {

                    if (perfilAdapter.getItem(i).getId_perfilUser() == usuario.getPerfil().getId_perfilUser()) {

                        spnPerfilUser.setSelection(i);

                    }
                }

            }

        } catch (Exception e) {

            Log.e("FragDadosUser - ", "Erro ao setar dados " + e.toString());
            AndroidUtils.alertDialog(getActivity(), "Erro ao salvar dados do Usuário " + e.toString());

        }


    }

    private void salvarUsuario() {

        try {

            if (usuario.getId_usuario() == 0) {

                Toast.makeText(getActivity(), "Dados Salvos", Toast.LENGTH_LONG).show();
                UsuarioDAO dao = new UsuarioDAO(getActivity());
                long id = dao.salvaUsuario(usuario);

                if (id > 0) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                    alerta.setCancelable(false);
                    alerta.setTitle("INFORMACAO");
                    alerta.setMessage("Dados salvos salvo com sucesso!");
                    alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getActivity().finish();
                        }
                    });

                    AlertDialog alt = alerta.create();
                    alt.show();

                }
            } else if (usuario.getId_usuario() > 0) {

               
                UsuarioDAO dao = new UsuarioDAO(getActivity());
                dao.updateUsuario(getActivity(), usuario);


                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                alerta.setCancelable(false);
                alerta.setTitle("INFORMACAO");
                alerta.setMessage("Dados atualizados com sucesso!");
                alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getActivity().finish();
                    }
                });

                AlertDialog alt = alerta.create();
                alt.show();


            }

        } catch (Exception e) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setCancelable(false);
            alerta.setTitle("INFORMACAO");
            alerta.setMessage("Erro ao salvar dados do usuário " + e.toString() + ", os dados não foram salvos.");
            alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    getActivity().finish();
                }
            });

            AlertDialog alt = alerta.create();
            alt.show();

        }


    }

}
