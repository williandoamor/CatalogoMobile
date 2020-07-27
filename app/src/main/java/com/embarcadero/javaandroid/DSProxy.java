// 
// Created by the DataSnap proxy generator.
// 18/02/2016 15:17:01
// 

package com.embarcadero.javaandroid;

import java.util.Date;

public class DSProxy {
  public static class TServerMethodos1 extends DSAdmin {
    public TServerMethodos1(DSRESTConnection Connection) {
      super(Connection);
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_DSServerModuleCreate_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_DSServerModuleCreate_Metadata() {
      if (TServerMethodos1_DSServerModuleCreate_Metadata == null) {
        TServerMethodos1_DSServerModuleCreate_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Sender", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TObject"),
        };
      }
      return TServerMethodos1_DSServerModuleCreate_Metadata;
    }

    /**
     * @param Sender [in] - Type on server: TObject
     */
    public void DSServerModuleCreate(TJSONObject Sender) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.DSServerModuleCreate");
      cmd.prepare(get_TServerMethodos1_DSServerModuleCreate_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(Sender);
      getConnection().execute(cmd);
      return;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_SmoothResize_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_SmoothResize_Metadata() {
      if (TServerMethodos1_SmoothResize_Metadata == null) {
        TServerMethodos1_SmoothResize_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Src", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TBitmap"),
          new DSRESTParameterMetaData("Dst", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TBitmap"),
        };
      }
      return TServerMethodos1_SmoothResize_Metadata;
    }

    /**
     * @param Src [in] - Type on server: TBitmap
     * @param Dst [in] - Type on server: TBitmap
     */
    public void SmoothResize(TJSONObject Src, TJSONObject Dst) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.SmoothResize");
      cmd.prepare(get_TServerMethodos1_SmoothResize_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(Src);
      cmd.getParameter(1).getValue().SetAsJSONValue(Dst);
      getConnection().execute(cmd);
      return;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_EchoString_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_EchoString_Metadata() {
      if (TServerMethodos1_EchoString_Metadata == null) {
        TServerMethodos1_EchoString_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Value", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_EchoString_Metadata;
    }

    /**
     * @param Value [in] - Type on server: string
     * @return result - Type on server: string
     */
    public String EchoString(String Value) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.EchoString");
      cmd.prepare(get_TServerMethodos1_EchoString_Metadata());
      cmd.getParameter(0).getValue().SetAsString(Value);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsString();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_ReverseString_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_ReverseString_Metadata() {
      if (TServerMethodos1_ReverseString_Metadata == null) {
        TServerMethodos1_ReverseString_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Value", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_ReverseString_Metadata;
    }

    /**
     * @param Value [in] - Type on server: string
     * @return result - Type on server: string
     */
    public String ReverseString(String Value) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.ReverseString");
      cmd.prepare(get_TServerMethodos1_ReverseString_Metadata());
      cmd.getParameter(0).getValue().SetAsString(Value);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsString();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_InsereDados_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_InsereDados_Metadata() {
      if (TServerMethodos1_InsereDados_Metadata == null) {
        TServerMethodos1_InsereDados_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("nome", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("Erro", DSRESTParamDirection.Output, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.BooleanType, "Boolean"),
        };
      }
      return TServerMethodos1_InsereDados_Metadata;
    }

    /**
     * @param nome [in] - Type on server: string
     * @param Erro [out] - Type on server: string
     * @return result - Type on server: Boolean
     */
    public static class InsereDadosReturns {
      public String Erro;
      public boolean returnValue;
    }
    public InsereDadosReturns InsereDados(String nome) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.InsereDados");
      cmd.prepare(get_TServerMethodos1_InsereDados_Metadata());
      cmd.getParameter(0).getValue().SetAsString(nome);
      getConnection().execute(cmd);
      InsereDadosReturns ret = new InsereDadosReturns();
      ret.Erro = cmd.getParameter(1).getValue().GetAsString();
      ret.returnValue = cmd.getParameter(2).getValue().GetAsBoolean();
      return ret;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetVendedor_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetVendedor_Metadata() {
      if (TServerMethodos1_GetVendedor_Metadata == null) {
        TServerMethodos1_GetVendedor_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetVendedor_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetVendedor() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetVendedor");
      cmd.prepare(get_TServerMethodos1_GetVendedor_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetProduto_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetProduto_Metadata() {
      if (TServerMethodos1_GetProduto_Metadata == null) {
        TServerMethodos1_GetProduto_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetProduto_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetProduto() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetProduto");
      cmd.prepare(get_TServerMethodos1_GetProduto_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetCliente_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetCliente_Metadata() {
      if (TServerMethodos1_GetCliente_Metadata == null) {
        TServerMethodos1_GetCliente_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetCliente_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetCliente() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetCliente");
      cmd.prepare(get_TServerMethodos1_GetCliente_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetTipoSaida_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetTipoSaida_Metadata() {
      if (TServerMethodos1_GetTipoSaida_Metadata == null) {
        TServerMethodos1_GetTipoSaida_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetTipoSaida_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetTipoSaida() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetTipoSaida");
      cmd.prepare(get_TServerMethodos1_GetTipoSaida_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetRecebimento_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetRecebimento_Metadata() {
      if (TServerMethodos1_GetRecebimento_Metadata == null) {
        TServerMethodos1_GetRecebimento_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetRecebimento_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetRecebimento() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetRecebimento");
      cmd.prepare(get_TServerMethodos1_GetRecebimento_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetContaReceber_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetContaReceber_Metadata() {
      if (TServerMethodos1_GetContaReceber_Metadata == null) {
        TServerMethodos1_GetContaReceber_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetContaReceber_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetContaReceber() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetContaReceber");
      cmd.prepare(get_TServerMethodos1_GetContaReceber_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetCaixa_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetCaixa_Metadata() {
      if (TServerMethodos1_GetCaixa_Metadata == null) {
        TServerMethodos1_GetCaixa_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetCaixa_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetCaixa() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetCaixa");
      cmd.prepare(get_TServerMethodos1_GetCaixa_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetEmpresa_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetEmpresa_Metadata() {
      if (TServerMethodos1_GetEmpresa_Metadata == null) {
        TServerMethodos1_GetEmpresa_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetEmpresa_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetEmpresa() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetEmpresa");
      cmd.prepare(get_TServerMethodos1_GetEmpresa_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetConfDesconto_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetConfDesconto_Metadata() {
      if (TServerMethodos1_GetConfDesconto_Metadata == null) {
        TServerMethodos1_GetConfDesconto_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetConfDesconto_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetConfDesconto() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetConfDesconto");
      cmd.prepare(get_TServerMethodos1_GetConfDesconto_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetCondicaoPagamento_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetCondicaoPagamento_Metadata() {
      if (TServerMethodos1_GetCondicaoPagamento_Metadata == null) {
        TServerMethodos1_GetCondicaoPagamento_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetCondicaoPagamento_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetCondicaoPagamento() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetCondicaoPagamento");
      cmd.prepare(get_TServerMethodos1_GetCondicaoPagamento_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_setPedido_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_setPedido_Metadata() {
      if (TServerMethodos1_setPedido_Metadata == null) {
        TServerMethodos1_setPedido_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Pedido", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("ItensPedido", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONArray"),
          new DSRESTParameterMetaData("Financeiro", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_setPedido_Metadata;
    }

    /**
     * @param Pedido [in] - Type on server: TJSONObject
     * @param ItensPedido [in] - Type on server: TJSONArray
     * @param Financeiro [in] - Type on server: TJSONObject
     * @return result - Type on server: string
     */
    public String setPedido(TJSONObject Pedido, TJSONArray ItensPedido, TJSONObject Financeiro) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.setPedido");
      cmd.prepare(get_TServerMethodos1_setPedido_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(Pedido);
      cmd.getParameter(1).getValue().SetAsJSONValue(ItensPedido);
      cmd.getParameter(2).getValue().SetAsJSONValue(Financeiro);
      getConnection().execute(cmd);
      return  cmd.getParameter(3).getValue().GetAsString();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetPedido_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetPedido_Metadata() {
      if (TServerMethodos1_GetPedido_Metadata == null) {
        TServerMethodos1_GetPedido_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("numeroPedido", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetPedido_Metadata;
    }

    /**
     * @param numeroPedido [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetPedido(String numeroPedido) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetPedido");
      cmd.prepare(get_TServerMethodos1_GetPedido_Metadata());
      cmd.getParameter(0).getValue().SetAsString(numeroPedido);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetStatusPedido_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetStatusPedido_Metadata() {
      if (TServerMethodos1_GetStatusPedido_Metadata == null) {
        TServerMethodos1_GetStatusPedido_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("numeroPedido", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_GetStatusPedido_Metadata;
    }

    /**
     * @param numeroPedido [in] - Type on server: string
     * @return result - Type on server: string
     */
    public String GetStatusPedido(String numeroPedido) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetStatusPedido");
      cmd.prepare(get_TServerMethodos1_GetStatusPedido_Metadata());
      cmd.getParameter(0).getValue().SetAsString(numeroPedido);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsString();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetJpeg_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetJpeg_Metadata() {
      if (TServerMethodos1_GetJpeg_Metadata == null) {
        TServerMethodos1_GetJpeg_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("bitMap", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TBitmap"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.JsonValueType, "TJPEGImage"),
        };
      }
      return TServerMethodos1_GetJpeg_Metadata;
    }

    /**
     * @param bitMap [in] - Type on server: TBitmap
     * @return result - Type on server: TJPEGImage
     */
    public TJSONObject GetJpeg(TJSONObject bitMap) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.GetJpeg");
      cmd.prepare(get_TServerMethodos1_GetJpeg_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(bitMap);
      getConnection().execute(cmd);
      return (TJSONObject) cmd.getParameter(1).getValue().GetAsJSONValue();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetBitmap_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetBitmap_Metadata() {
      if (TServerMethodos1_GetBitmap_Metadata == null) {
        TServerMethodos1_GetBitmap_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("jpeg", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJPEGImage"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.JsonValueType, "TBitmap"),
        };
      }
      return TServerMethodos1_GetBitmap_Metadata;
    }

    /**
     * @param jpeg [in] - Type on server: TJPEGImage
     * @return result - Type on server: TBitmap
     */
    public TJSONObject GetBitmap(TJSONObject jpeg) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.GetBitmap");
      cmd.prepare(get_TServerMethodos1_GetBitmap_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(jpeg);
      getConnection().execute(cmd);
      return (TJSONObject) cmd.getParameter(1).getValue().GetAsJSONValue();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_setItensPedido_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_setItensPedido_Metadata() {
      if (TServerMethodos1_setItensPedido_Metadata == null) {
        TServerMethodos1_setItensPedido_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("ItensPedido", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONArray"),
        };
      }
      return TServerMethodos1_setItensPedido_Metadata;
    }

    /**
     * @param ItensPedido [in] - Type on server: TJSONArray
     */
    public void setItensPedido(TJSONArray ItensPedido) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.setItensPedido");
      cmd.prepare(get_TServerMethodos1_setItensPedido_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(ItensPedido);
      getConnection().execute(cmd);
      return;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_setFinanceiro_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_setFinanceiro_Metadata() {
      if (TServerMethodos1_setFinanceiro_Metadata == null) {
        TServerMethodos1_setFinanceiro_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Financeiro", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
        };
      }
      return TServerMethodos1_setFinanceiro_Metadata;
    }

    /**
     * @param Financeiro [in] - Type on server: TJSONObject
     */
    public void setFinanceiro(TJSONObject Financeiro) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethodos1.setFinanceiro");
      cmd.prepare(get_TServerMethodos1_setFinanceiro_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(Financeiro);
      getConnection().execute(cmd);
      return;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_EnviarFotoTablet_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_EnviarFotoTablet_Metadata() {
      if (TServerMethodos1_EnviarFotoTablet_Metadata == null) {
        TServerMethodos1_EnviarFotoTablet_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("codProd", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_EnviarFotoTablet_Metadata;
    }

    /**
     * @param codProd [in] - Type on server: string
     * @return result - Type on server: string
     */
    public String EnviarFotoTablet(String codProd) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.EnviarFotoTablet");
      cmd.prepare(get_TServerMethodos1_EnviarFotoTablet_Metadata());
      cmd.getParameter(0).getValue().SetAsString(codProd);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsString();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_TamanhoArquivo_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_TamanhoArquivo_Metadata() {
      if (TServerMethodos1_TamanhoArquivo_Metadata == null) {
        TServerMethodos1_TamanhoArquivo_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Arquivo", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.DoubleType, "Double"),
        };
      }
      return TServerMethodos1_TamanhoArquivo_Metadata;
    }

    /**
     * @param Arquivo [in] - Type on server: string
     * @return result - Type on server: Double
     */
    public double TamanhoArquivo(String Arquivo) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.TamanhoArquivo");
      cmd.prepare(get_TServerMethodos1_TamanhoArquivo_Metadata());
      cmd.getParameter(0).getValue().SetAsString(Arquivo);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsDouble();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_GetProdutoFoto_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_GetProdutoFoto_Metadata() {
      if (TServerMethodos1_GetProdutoFoto_Metadata == null) {
        TServerMethodos1_GetProdutoFoto_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("datamodificacao", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethodos1_GetProdutoFoto_Metadata;
    }

    /**
     * @param datamodificacao [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetProdutoFoto(String datamodificacao) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.GetProdutoFoto");
      cmd.prepare(get_TServerMethodos1_GetProdutoFoto_Metadata());
      cmd.getParameter(0).getValue().SetAsString(datamodificacao);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_CriaLog_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_CriaLog_Metadata() {
      if (TServerMethodos1_CriaLog_Metadata == null) {
        TServerMethodos1_CriaLog_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Linha", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("arquivo", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_CriaLog_Metadata;
    }

    /**
     * @param Linha [in] - Type on server: string
     * @param arquivo [in] - Type on server: string
     */
    public void CriaLog(String Linha, String arquivo) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.CriaLog");
      cmd.prepare(get_TServerMethodos1_CriaLog_Metadata());
      cmd.getParameter(0).getValue().SetAsString(Linha);
      cmd.getParameter(1).getValue().SetAsString(arquivo);
      getConnection().execute(cmd);
      return;
    }
    
    
    private DSRESTParameterMetaData[] TServerMethodos1_SetJPGCompression_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethodos1_SetJPGCompression_Metadata() {
      if (TServerMethodos1_SetJPGCompression_Metadata == null) {
        TServerMethodos1_SetJPGCompression_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("ACompression", DSRESTParamDirection.Input, DBXDataTypes.Int32Type, "Integer"),
          new DSRESTParameterMetaData("AInFile", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("AOutFile", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TServerMethodos1_SetJPGCompression_Metadata;
    }

    /**
     * @param ACompression [in] - Type on server: Integer
     * @param AInFile [in] - Type on server: string
     * @param AOutFile [in] - Type on server: string
     */
    public void SetJPGCompression(int ACompression, String AInFile, String AOutFile) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethodos1.SetJPGCompression");
      cmd.prepare(get_TServerMethodos1_SetJPGCompression_Metadata());
      cmd.getParameter(0).getValue().SetAsInt32(ACompression);
      cmd.getParameter(1).getValue().SetAsString(AInFile);
      cmd.getParameter(2).getValue().SetAsString(AOutFile);
      getConnection().execute(cmd);
      return;
    }
  }

}
