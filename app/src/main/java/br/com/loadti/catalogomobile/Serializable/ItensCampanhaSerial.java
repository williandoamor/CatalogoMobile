package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by TI on 09/01/2016.
 */
public class ItensCampanhaSerial implements Serializable {

    private static final long serialVersionUI = 1L;
    private int idCampanha;
    private int idItemCampanha;
    private ProdutoSerial produto;

    public ItensCampanhaSerial() {

        this.idCampanha = 0;
        this.idItemCampanha = 0;
        this.produto = new ProdutoSerial();
    }

    public int getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(int idCampanha) {
        this.idCampanha = idCampanha;
    }

    public int getIdItemCampanha() {
        return idItemCampanha;
    }

    public void setIdItemCampanha(int idItemCampanha) {
        this.idItemCampanha = idItemCampanha;
    }

    public ProdutoSerial getProduto() {
        return produto;
    }

    public void setProduto(ProdutoSerial produto) {
        this.produto = produto;
    }
}
