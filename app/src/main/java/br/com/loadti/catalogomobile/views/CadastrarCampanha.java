package br.com.loadti.catalogomobile.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;

/**
 * Created by TI on 23/12/2015.
 */
public class CadastrarCampanha extends AppCompatActivity {

    private Button btnVoltar, btnProximo, btnBuscarCampanhaCadastro;
    private MaterialEditText edtNomecampanha;
    private CampanhaSerial campanha;
    private String tela = "campanha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cadastrar_campanha);

        /*Botoes*/
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnProximo = (Button) findViewById(R.id.btnProximo);
        btnBuscarCampanhaCadastro = (Button) findViewById(R.id.btnBuscarCampanhaCadastro);

        /*Objetos*/
        campanha = new CampanhaSerial();

        /*Edits*/
        edtNomecampanha = (MaterialEditText) findViewById(R.id.edtNomeCampanha);

        /*Chama a tela para escolher os produtos da campanha*/
        actionCadastrarCamapanha();

        /*Action Voltar*/
        actionVoltar();

        /*Buscar Campanha*/
        actionBuscarCampanha();

    }

    private void actionBuscarCampanha() {

        btnBuscarCampanhaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CadastrarCampanha.this, PesquisaCampanha.class), 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {

                campanha = (CampanhaSerial) data.getSerializableExtra("campanha");
                edtNomecampanha.setText(campanha.getNomeCampanha());
                //edtNomecampanha.setFocusable(false);

            }

        }
    }

    /*cancela a inclusao de uma nova campanha*/
    private void actionVoltar() {

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertVoltar = new AlertDialog.Builder(CadastrarCampanha.this);
                alertVoltar.setCancelable(false);
                alertVoltar.setTitle("CONFIRMA");
                alertVoltar.setMessage(R.string.btnvoltar);
                alertVoltar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /*Verifica se foi informado um nome para a campanha*/
                        finish();

                    }
                });

                alertVoltar.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog al = alertVoltar.create();
                al.show();

            }
        });

    }

    /*chama a tela de cadastro de campanha*/
    private void actionCadastrarCamapanha() {

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtNomecampanha.getText().toString().equals("")) {

                    Toast.makeText(CadastrarCampanha.this, "Deve ser informado um nome para a campanha. ", Toast.LENGTH_LONG).show();
                    edtNomecampanha.requestFocus();

                } else {
                    /*Se o id da campanha for igual a zero, esta inserindo uma nova*/
                    if (campanha.getIdcampnha() == 0) {

                        campanha.setNomeCampanha(edtNomecampanha.getText().toString().toUpperCase());
                        startActivity(new Intent(CadastrarCampanha.this, ContainerCadastrarCampanha.class).putExtra("campanha", campanha));
                        finish();
                    } else if (campanha.getIdcampnha() > 0) {

                        startActivity(new Intent(CadastrarCampanha.this, ContainerCadastrarCampanha.class).putExtra("campanha", campanha));
                        finish();

                    }


                }
            }
        });

    }

    /*
* Ação ao pressionar o botão voltar para sair da app
*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarCampanha.this);
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
