package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;

/**
 * Created by TI on 19/03/2015.
 */
public class PerfilUserSerial implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id_perfilUser;
    private String nomePerfil;
    private String acessoPerfil;
    private String alterarPerfil;
    private String statusPerfil;


    public PerfilUserSerial() {

        this.id_perfilUser = 0;
        this.nomePerfil = "";
        this.acessoPerfil = "";
        this.alterarPerfil = "";
        this.statusPerfil = "";
    }

    public int getId_perfilUser() {
        return id_perfilUser;
    }

    public void setId_perfilUser(int id_perfilUser) {
        this.id_perfilUser = id_perfilUser;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getAcessoPerfil() {
        return acessoPerfil;
    }

    public void setAcessoPerfil(String acessoPerfil) {
        this.acessoPerfil = acessoPerfil;
    }

    public String getAlterarPerfil() {
        return alterarPerfil;
    }

    public void setAlterarPerfil(String alterarPerfil) {
        this.alterarPerfil = alterarPerfil;
    }

    public String getStatusPerfil() {
        return statusPerfil;
    }

    public void setStatusPerfil(String statusPerfil) {
        this.statusPerfil = statusPerfil;
    }

    @Override
    public String toString() {
        return nomePerfil;
    }
}
