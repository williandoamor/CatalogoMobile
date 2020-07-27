package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by TI on 08/01/2016.
 */
public class CampanhaSerial implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idcampnha;
    private String nomeCampanha;
    private String situacao;
    private ArrayList<ItensCampanhaSerial> itensCampanha;


    public CampanhaSerial() {


        this.idcampnha = 0;
        this.situacao = "";
        this.nomeCampanha = "";
        this.itensCampanha = new ArrayList<ItensCampanhaSerial>();

    }

    public int getIdcampnha() {
        return idcampnha;
    }

    public void setIdcampnha(int idcampnha) {
        this.idcampnha = idcampnha;
    }


    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }


    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public ArrayList<ItensCampanhaSerial> getItensCampanha() {
        return itensCampanha;
    }

    public void setItensCampanha(ArrayList<ItensCampanhaSerial> itensCampanha) {
        this.itensCampanha = itensCampanha;
    }


}
