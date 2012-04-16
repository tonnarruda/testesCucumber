package com.fortes.rh.model.ws;

public class TItemTabelaEmpregados
{
	private String codigo = ""; // 	   Fcodigo: string;
	private String empresa = "";// Fempresa: string;
	private String cargo = "";// Fcargo: string;
	private String lotacao = "";//Flotacao: String;
	private String estabelecimento = "";//Festabelecimento: String;
	private String data = "";//Fdata: string;
	private Double valor = 0D;//Fvalor: double;
	private Double valor_alt = 0D;//Fvalor_alt: Double;
	private Integer rh_sep_id = 0;//Frh_sep_id: integer;
	private String saltipo = "";// Fsaltipo: string;
	private String indcodigosalario = ""; //Findcodigosalario: string;
	private Double indqtde = 0D;// Findqtde: double;
	private String FexpAgenteNocivo = ""; // FexpAgenteNocivo
	private String obs = "";//Fobs : String;
	private String dataRescisao;//FdataRescisao: TXSDateTime;
	
	private String expAgenteNocivo;//GFIP

	public String getCargo()
	{
		return cargo;
	}
	public void setCargo(String cargo)
	{
		this.cargo = cargo;
	}
	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public String getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(String empresa)
	{
		this.empresa = empresa;
	}
	public String getLotacao()
	{
		return lotacao;
	}
	public void setLotacao(String lotacao)
	{
		this.lotacao = lotacao;
	}
	public Integer getRh_sep_id()
	{
		return rh_sep_id;
	}
	public void setRh_sep_id(Integer rh_sep_id)
	{
		this.rh_sep_id = rh_sep_id;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public String getEstabelecimento()
	{
		return estabelecimento;
	}
	public void setEstabelecimento(String estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}
	public String getIndcodigosalario()
	{
		return indcodigosalario;
	}
	public void setIndcodigosalario(String indcodigosalario)
	{
		this.indcodigosalario = indcodigosalario;
	}
	public Double getIndqtde()
	{
		return indqtde;
	}
	public void setIndqtde(Double indqtde)
	{
		this.indqtde = indqtde;
	}
	public String getSaltipo()
	{
		return saltipo;
	}
	public void setSaltipo(String saltipo)
	{
		this.saltipo = saltipo;
	}
	public Double getValor_alt()
	{
		return valor_alt;
	}
	public void setValor_alt(Double valor_alt)
	{
		this.valor_alt = valor_alt;
	}
	public String getExpAgenteNocivo()
	{
		return expAgenteNocivo;
	}
	public void setExpAgenteNocivo(String expAgenteNocivo)
	{
		this.expAgenteNocivo = expAgenteNocivo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getDataRescisao() {
		return dataRescisao;
	}

	public void setDataRescisao(String dataRescisao) {
		this.dataRescisao = dataRescisao;
	}

	public String getFexpAgenteNocivo() {
		return FexpAgenteNocivo;
	}

	public void setFexpAgenteNocivo(String fexpAgenteNocivo) {
		FexpAgenteNocivo = fexpAgenteNocivo;
	}

}