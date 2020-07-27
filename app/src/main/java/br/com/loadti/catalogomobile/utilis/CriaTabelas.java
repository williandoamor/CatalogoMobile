package br.com.loadti.catalogomobile.utilis;

import android.util.Log;

import java.util.Vector;

/**
 * Created by TI on 16/10/2015.
 */
public class CriaTabelas {

    private Vector<String> vCriaTabelas;

    public CriaTabelas() {


        Log.d("LoadSystem - Tabelas", "Sql para Criação das Tabelas");

        vCriaTabelas = new Vector<String>();

        // Chama os metodos q contem as SQL
        createTable();


    }

    public Vector<String> pegaTabelas() {

        Log.d("LoadSystem - Tabels", "Pegando as tabelas");
        return this.vCriaTabelas;
    }

    // Cria as tabelas e coloca no vertor de tabelas
    private void createTable() {
        // Cria a tabela Produto
        String Produto = "CREATE TABLE PRODUTO (ID_PRODUTO INTEGER NOT NULL PRIMARY KEY,"
                + " CODPROD CHAR(9) NOT NULL,"
                + " CODIGO VARCHAR(20) NOT NULL,"
                + " NOMEPROD VARCHAR(50) NOT NULL,"
                + " PREVENDA1 NUMERIC NOT NULL,"
                + " PREVENDATAB2 NUMERIC,"
                + " ESTATU NUMERIC NOT NULL,"
                + " NAOVENDER CHAR(1),"
                + " PRECUSTO NUMERIC," /*Campo da versao 3 do banco*/
                + " INATIVO CHAR(1)," /*Campo da Versao 2 do banco de dados*/
                + " CUSTOMEDIO NUMERIC," /*Campo da versao 3 do bando de dados*/
                + " CUSTOREAL NUMERIC,"  /*Campo da versao 3 do bando de dados*/
                + " UNIDADE VARCHAR(20)," /*Campo da versao 5 do banco de dados*/
                + " OBSERVACAO_PROD BLOB," /*Campo da versão 5 do banco de dados*/
                + " FOTO BLOB)"; /*Campo da versao 5 do banco de dados*/

        vCriaTabelas.add(Produto);

           	/*Cria a tabela empresa na base de dados*/
        String CONFIGEMPRESA = " CREATE TABLE CONFEMP (ID_EMPRESA INTEGER NOT NULL PRIMARY KEY,"
                + " CONFEMP_RAZA_EMP VARCHAR(50),"
                + " CONFEMP_FANT_EMP VARCHAR(40),"
                + " CONFEMP_ESTADO_EMP CHAR(2),"
                + " CONFEMP_END_EMP VARCHAR(40),"
                + " CONFEMP_FONE_EMP VARCHAR(20),"
                + " CONFEMP_CNPJCPF_EMP VARCHAR(14),"
                + " CONFEMP_INSCRG_EMP VARCHAR(14),"
                + " CONFEMP_INSCMUN_EMP VARCHAR(20),"
                + " CONFEMP_SITE_EMP VARCHAR(35),"
                + " CONFEMP_LOGO_EMP BLOB)";

        vCriaTabelas.add(CONFIGEMPRESA);

               /*Cria tabela geral de configuracao*/
        String TABELAGERAL = " CREATE TABLE CONFGERAL (ID_CONFGERAL INTEGER NOT NULL PRIMARY KEY,"
                + "CONFGERAL_PORTA INTEGER," /*Porta utilizada para conexao*/
                + "CONFGERAL_HOST VARCHAR (100)," /*Host utilizado para conexao*/
                + "CONFGERAL_CASADECVENDA  INTEGER," /*Casas decimais para preco de venda*/
                + "CONFGERAL_MOSTRARPRECO CHAR (1),"
                + "CONFIGERAL_TABVENDAPRADAO INTEGER," /**/
                + "CONFGERAL_VISUALIZAR INTEGER," /*Configurar visualizacao*/
                + "CONFGERAL_CASADECQTDE INTEGER," /*Casas decimais para quantidade*/
                + "CONFGERAL_ID_CAMPANHA_PADRAO INTEGER,"/*Codigo da campanha padrao*/
                + "CONFGERAL_TIPOVISUALIZACAO INTEGER)"; /*Codigo do tipo de visualizacao de imagens*/
        vCriaTabelas.add(TABELAGERAL);

           /*Insere os dados basicos da configuracao geral no banco de dados*/
        String INSERTCONFGERAL = " INSERT INTO CONFGERAL (CONFGERAL_PORTA, CONFGERAL_HOST, CONFGERAL_CASADECVENDA, CONFGERAL_MOSTRARPRECO, CONFIGERAL_TABVENDAPRADAO, CONFGERAL_VISUALIZAR, CONFGERAL_CASADECQTDE,CONFGERAL_ID_CAMPANHA_PADRAO)"
                + " VALUES ('8080', '10.10.10.204', '2', 'N', '0', '2', '2', '0')";

        vCriaTabelas.add(INSERTCONFGERAL);


        String TABELACAMPANHA = "CREATE TABLE CAMPANHA (ID_CAMPANHA INTEGER NOT NULL PRIMARY KEY,"
                + "NOME_CAMPANHA VARCHAR(100),"
                + "SITU_CAMPANHA CHAR(1))";

        vCriaTabelas.add(TABELACAMPANHA);

        String TABELAITENSCAMPANHA = "CREATE TABLE ITENSCAMPANHA (ID_ITEMCAMPANHA INTEGER NOT NULL PRIMARY KEY,"
                + "ID_CAMPANHA INTEGER,"
                + "COD_PRODUTO VARCHAR(9))";

        vCriaTabelas.add(TABELAITENSCAMPANHA);

                  /*Cria tabela perfil no banco de dados*/
        String TABELAPERFIL = " CREATE TABLE PERFIL (ID_PERFIL INTEGER NOT NULL PRIMARY KEY,"
                + " NOME_PER VARCHAR(10),"
                + " ACESSO_PER CHAR(1),"
                + " ALTERAR_PER CHAR(1),"
                + " STATUS_PER CHAR(1))";

        vCriaTabelas.add(TABELAPERFIL);

                 /*Insere o perfil padrao no banco de dados*/
        String INSERTPERFIL1 = " INSERT INTO PERFIL (NOME_PER, ACESSO_PER, ALTERAR_PER, STATUS_PER)"
                + " VALUES ('ADMIN', 'S', 'S', 'A')";

        vCriaTabelas.add(INSERTPERFIL1);

        String INSERTPERFIL2 = " INSERT INTO PERFIL (NOME_PER, ACESSO_PER, ALTERAR_PER, STATUS_PER)"
                + " VALUES ('USUARIO', 'S', 'S','A')";

        vCriaTabelas.add(INSERTPERFIL2);

        /*Cria tabela usuario*/
        String TABELAUSUARIO = " CREATE TABLE USUARIO (ID_USUARIO INTEGER NOT NULL PRIMARY KEY,"
                + " USUARIO_NOMEUSER VARCHAR(50)," /*Nome do usuario*/
                + " USUARIO_LOGINUSER VARCHAR(10)," /*Login do usuario*/
                + " USUARIO_SENHAUSER VARCHAR(8)," /*Senha usuario*/
                + " USUARIO_CONFIRMASENHAUSER VARCHAR(8)," /*Confirma Senha do usuario*/
                + " USUARIO_DATACAD DATETEXT," /*Data de cadastro do Usuario*/
                + " ID_PERFIL INTEGER CONSTRAINT USUARIO_PERFIL REFERENCES PERFIL,"/*Chave estrangeira da tabela perfil*/
                + " USUARIO_STATUSUSER CHAR(1))"; /*Status do usuario*/

        vCriaTabelas.add(TABELAUSUARIO);

        /*Insere o usuario padrao no banco de dados*/
        String INSERTUSUARIO = " INSERT INTO USUARIO (USUARIO_NOMEUSER, USUARIO_LOGINUSER, USUARIO_SENHAUSER,"
                + " ID_PERFIL, USUARIO_STATUSUSER) VALUES ('ADMINISTRADOR', 'ADMIN', 'LOADTI01', 1, 'A')";

        vCriaTabelas.add(INSERTUSUARIO);


        String TABELASINCRONIZACAO = "CREATE TABLE SINCRONIZACAO (ID_SINC INTEGER NOT NULL PRIMARY KEY,"
                + " SINC_DATASINC DATETEXT,"
                + " SINC_HORASINC TIME)";

        vCriaTabelas.add(TABELASINCRONIZACAO);


        String INSERESINCRONISMO = " INSERT INTO SINCRONIZACAO (SINC_DATASINC, SINC_HORASINC) VALUES ('1900/01/01','00:00')";

        vCriaTabelas.add(INSERESINCRONISMO);

    }
}
