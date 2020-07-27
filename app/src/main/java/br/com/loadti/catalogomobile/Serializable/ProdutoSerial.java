package br.com.loadti.catalogomobile.Serializable;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdutoSerial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_produto;
	private String codProd;
    private String codigoProduto;
	private String descricao;
    private BigDecimal prevenda1;
    private BigDecimal prevenda2;
    private BigDecimal estoque;
    private String naoVender;

	private BigDecimal precusto;
	private BigDecimal customedio;
	private BigDecimal custoreal;
    private String observacaoProd;
    private String fotoProd;
    private String unidade;


    public ProdutoSerial() {
		super();

		this.id_produto = -1;
		this.codProd = "";
        this.codigoProduto = "";
		this.descricao = "";
		this.estoque = new BigDecimal(0);
		this.naoVender = "";
		this.prevenda1 = new BigDecimal(0);
		this.prevenda2 = new BigDecimal(0);
		this.precusto = new BigDecimal(0);
		this.customedio = new BigDecimal(0);
		this.custoreal = new BigDecimal(0);
        this.observacaoProd = "";
        this.fotoProd = "";
        this.unidade = "";

	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getcodProd() {
		return codProd;
	}

	public void setcodProd(String codProd) {
		this.codProd = codProd;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public String getNaoVender() {
		return naoVender;
	}

	public void setNaoVender(String naoVender) {
		this.naoVender = naoVender;
	}

	public BigDecimal getPrevenda1() {
		return prevenda1;
	}

	public void setPrevenda1(BigDecimal prevenda1) {
		this.prevenda1 = prevenda1;
	}

	public BigDecimal getPrevenda2() {
		return prevenda2;
	}

	public void setPrevenda2(BigDecimal prevenda2) {
		this.prevenda2 = prevenda2;
	}

	public BigDecimal getPrecusto() {
		return precusto;
	}

	public void setPrecusto(BigDecimal precusto) {
		this.precusto = precusto;
	}

	public BigDecimal getCustomedio() {
		return customedio;
	}

	public void setCustomedio(BigDecimal customedio) {
		this.customedio = customedio;
	}

	public BigDecimal getCustoreal() {
		return custoreal;
	}

	public void setCustoreal(BigDecimal custoreal) {
		this.custoreal = custoreal;
	}

    public String getFotoProd() {
        return fotoProd;
    }

    public void setFotoProd(String fotoProd) {
        this.fotoProd = fotoProd;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getObservacaoProd() {
        return observacaoProd;
    }

    public void setObservacaoProd(String observacaoProd) {
        this.observacaoProd = observacaoProd;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
