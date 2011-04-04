package com.fortes.rh.model.ws;

public class TItemTabelaEmpregados
{
	private String codigo = "";
	private String empresa = "";
	private String cargo = "";
	private String lotacao = "";
	private String estabelecimento = "";
	private String data = "";
	private Double valor = 0D;
	private Double valor_alt = 0D;
	private Integer rh_sep_id = 0;
	private String saltipo = "";
	private String indcodigosalario = "";
	private Double indqtde = 0D;
	private String grupoAC = "";

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

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

}