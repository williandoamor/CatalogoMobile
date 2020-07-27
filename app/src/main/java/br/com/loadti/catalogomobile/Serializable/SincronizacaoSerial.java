package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;

/**
 * Created by TI on 18/02/2016.
 */
public class SincronizacaoSerial implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idsincronizacao;
    private String dataSincronizacao;
    private String horaSincronizacao;


    public SincronizacaoSerial() {

        this.idsincronizacao = 0;
        this.dataSincronizacao = "";
        this.horaSincronizacao = "";

    }

    public int getIdsincronizacao() {
        return idsincronizacao;
    }

    public void setIdsincronizacao(int idsincronizacao) {
        this.idsincronizacao = idsincronizacao;
    }

    public String getDataSincronizacao() {
        return dataSincronizacao;
    }

    public void setDataSincronizacao(String dataSincronizacao) {
        this.dataSincronizacao = dataSincronizacao;
    }

    public String getHoraSincronizacao() {
        return horaSincronizacao;
    }

    public void setHoraSincronizacao(String horaSincronizacao) {
        this.horaSincronizacao = horaSincronizacao;
    }
}
