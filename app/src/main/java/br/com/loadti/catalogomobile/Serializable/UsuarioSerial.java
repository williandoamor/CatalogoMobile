package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;


public class UsuarioSerial implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id_usuario;
    private String nomeUser;
    private String loginUser;
    private String senhaUser;
    private String confirmaSenhaUser;
    private String statusUser;
    private PerfilUserSerial perfil;
    private String dataCadUser;


    public UsuarioSerial() {

        this.id_usuario = 0;
        this.nomeUser = "";
        this.loginUser = "";
        this.senhaUser = "";
        this.confirmaSenhaUser = "";
        this.statusUser = "";
        this.perfil = new PerfilUserSerial();

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getSenhaUser() {
        return senhaUser;
    }

    public void setSenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }

    public String getConfirmaSenhaUser() {
        return confirmaSenhaUser;
    }

    public void setConfirmaSenhaUser(String confirmaSenhaUser) {
        this.confirmaSenhaUser = confirmaSenhaUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public PerfilUserSerial getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUserSerial perfil) {
        this.perfil = perfil;
    }


    public String getDataCadUser() {
        return dataCadUser;
    }

    public void setDataCadUser(String dataCadUser) {
        this.dataCadUser = dataCadUser;
    }


}