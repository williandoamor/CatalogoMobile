package br.com.loadti.catalogomobile.views;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.rengwuxian.materialedittext.MaterialEditText;


import br.com.loadti.catalogomobile.DAO.UsuarioDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;
import io.fabric.sdk.android.Fabric;

public class Login extends AppCompatActivity {

    private EditText edtusuario, edtsenha;
    private Button btnacessar, btncancelar;

    //private DB bd;
    // private SQLiteDatabase dbase;
    // private DatabaseManager bancoDados;
    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.login);
        /*Cria ou atualiza o banco de dados*/
        bancoDados();
        /*Instancia os componentes do Layout*/
        instanciaComponentes();
        /*Ativa o suporte a Toobar*/
        ativaToobar();
        /*Metodo Acessar*/
        acessar();
        /*Metodo cancelar*/
        cancelar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AndroidUtils.sairActivity(Login.this);
        }

        return super.onKeyDown(keyCode, event);
    }

    /*Ativa o suporte a Toobar*/
    public void ativaToobar() {

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tb_main_usuario);
        if (toolbar != null) {

            this.setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.telaLogin);

        }

    }


    /*Instancia os componentes do Layout*/
    public void instanciaComponentes() {

        edtusuario = (EditText) findViewById(R.id.edtUsuario);
        edtusuario.setSelection(edtusuario.getText().length());

        edtsenha = (EditText) findViewById(R.id.edtSenha);
        edtsenha.setSelection(edtsenha.getText().length());

        btnacessar = (Button) findViewById(R.id.btnLogin);
        btncancelar = (Button) findViewById(R.id.btCancelar);


    }

    /*Cria o banco de dados*/
    public void bancoDados() {

        try {

            // bd = new DB(this);
            // dbase = bd.getWritableDatabase();
            DatabaseManager.getInstance();
            DatabaseManager.initializeInstance(Login.this);
            bancoDados = DatabaseManager.getInstance().openDatabase();

        } catch (Exception e) {

            AndroidUtils.alertDialog(Login.this, "Ouve um erro ao criar o banco de dados." + e.toString() + " \n Isso impede o funcionamento do sistema. Por favor entre em contato com o suporte.");
            // finish();
        }

        // AndroidUtils.fechaBanco(dbase);
        //  bd.close();
        DatabaseManager.getInstance().closeDatabase();

    }


    /*
     * Ação do botão acessar
     */
    public void acessar() {

        btnacessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtusuario.getText().toString().equals("")) {

                    AndroidUtils.alertDialog(Login.this, "O usuário deve ser informado.");
                    edtusuario.requestFocus();

                } else if (edtsenha.getText().toString().equals("")) {

                    AndroidUtils.alertDialog(Login.this, "A senha deve ser informada.");
                    edtsenha.requestFocus();

                } else {

                    try {
                        UsuarioSerial usuario = new UsuarioSerial();
                        UsuarioDAO pesqUsuario = new UsuarioDAO(Login.this);
                        usuario = pesqUsuario.usuario(edtusuario.getText().toString().toUpperCase());

                                /*Verifica se o usuario exite*/
                        if (usuario.getNomeUser().equals("")) {

                            AndroidUtils.alertDialog(Login.this, "Usuário não existe.");


                        } else {


                                           /*Compara se a senha digitada é igual a senha no banco*/
                            if (usuario.getSenhaUser().toUpperCase().equals(edtsenha.getText().toString().toUpperCase())) {
                                /*Verifica se o usuário esta ativo no sistema*/
                                if (usuario.getStatusUser().equals("A")) {

                                    /*A tela de do Menu e chamada passando o usuario que fez login como parametro*/
                                    startActivity(new Intent(Login.this,
                                            Principal.class)
                                            .putExtra("tela", "login")
                                            .putExtra("usuario", usuario));
                                    finish();
                                    /*Else que informa que o usuairo esta inativo*/
                                } else {

                                    AndroidUtils.alertDialog(Login.this, "Usuário inátivo.");

                                }
                                /*Else que informa que a senha esta incorreta*/
                            } else {

                                AndroidUtils.alertDialog(Login.this, "Senha do usuário inválida");
                                edtsenha.requestFocus();


                            }


                        }


                    } catch (Exception e) {

                        AndroidUtils.alertDialog(Login.this, e.toString());


                    }


                }

            }
        });

    }

    /*
     * Ação do botão cancelar
     */
    public void cancelar() {
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AndroidUtils.sairActivity(Login.this);

            }
        });


    }

}
