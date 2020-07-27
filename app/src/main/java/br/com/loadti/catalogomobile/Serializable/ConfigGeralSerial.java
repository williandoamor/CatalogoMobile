package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;

/**
 * Created by TI on 22/04/2015.
 */
public class ConfigGeralSerial implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id_configGeral;
    private int portaHost;
    private String host;
    private int casaDecimalVenda;
    private String mostrarPreco;
    private int tabelaPadrao;
    private int visualizacao;
    private int codCampanhaPadrao;
    private int codTipoVisualizacao;


    public ConfigGeralSerial() {

        this.id_configGeral = 0;
        this.portaHost = 0;
        this.host = "";
        this.casaDecimalVenda = 0;
        this.mostrarPreco = "";
        this.tabelaPadrao = 0;
        this.visualizacao = 0;
        this.codCampanhaPadrao = 0;
        this.codTipoVisualizacao = 0;
    }


    public int getId_configGeral() {
        return id_configGeral;
    }

    public void setId_configGeral(int id_configGeral) {
        this.id_configGeral = id_configGeral;
    }

    public int getPortaHost() {
        return portaHost;
    }

    public void setPortaHost(int portaHost) {
        this.portaHost = portaHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getCasaDecimalVenda() {
        return casaDecimalVenda;
    }

    public void setCasaDecimalVenda(int casaDecimalVenda) {
        this.casaDecimalVenda = casaDecimalVenda;
    }


    public String getMostrarPreco() {
        return mostrarPreco;
    }

    public void setMostrarPreco(String mostrarPreco) {
        this.mostrarPreco = mostrarPreco;
    }

    public int getTabelaPadrao() {
        return tabelaPadrao;
    }

    public void setTabelaPadrao(int tabelaPadrao) {
        this.tabelaPadrao = tabelaPadrao;
    }

    public int getVisualizacao() {
        return visualizacao;
    }

    public void setVisualizacao(int visualizacao) {
        this.visualizacao = visualizacao;
    }

    public int getCodCampanhaPadrao() {
        return codCampanhaPadrao;
    }

    public void setCodCampanhaPadrao(int codCampanhaPadrao) {
        this.codCampanhaPadrao = codCampanhaPadrao;
    }

    public int getCodTipoVisualizacao() {
        return codTipoVisualizacao;
    }

    public void setCodTipoVisualizacao(int codTipoVisualizacao) {
        this.codTipoVisualizacao = codTipoVisualizacao;
    }
}
