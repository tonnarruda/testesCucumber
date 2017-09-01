package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class TSituacao implements Serializable
{
	private Integer id;
	private String data;
	private String estabelecimentoCodigoAC;
	private String lotacaoCodigoAC;
	private String cargoCodigoAC;
	private String tipoSalario;
	private Double valor;
	private Double valorAnterior;
	private String indiceCodigoAC;
	private Double indiceQtd;
	private String empresaCodigoAC;
	private String empregadoCodigoAC;
	private String empregadoCodigoACDestino;
	private String expAgenteNocivo;//GFIP
	private Integer movimentoSalarialId;//usado pelo reajuste salarial em lote
	private String grupoAC;
	private String obs;
	private String categoriaESocial;

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getCargoCodigoAC()
	{
		return cargoCodigoAC;
	}
	public void setCargoCodigoAC(String cargoCodigoAC)
	{
		this.cargoCodigoAC = cargoCodigoAC;
	}
	public String getEstabelecimentoCodigoAC()
	{
		return estabelecimentoCodigoAC;
	}
	public void setEstabelecimentoCodigoAC(String estabelecimentoCodigoAC)
	{
		this.estabelecimentoCodigoAC = estabelecimentoCodigoAC;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getIndiceCodigoAC()
	{
		return indiceCodigoAC;
	}
	public void setIndiceCodigoAC(String indiceCodigoAC)
	{
		this.indiceCodigoAC = indiceCodigoAC;
	}
	public Double getIndiceQtd()
	{
		return indiceQtd;
	}
	public void setIndiceQtd(Double indiceQtd)
	{
		this.indiceQtd = indiceQtd;
	}
	public String getLotacaoCodigoAC()
	{
		return lotacaoCodigoAC;
	}
	public void setLotacaoCodigoAC(String lotacaoCodigoAC)
	{
		this.lotacaoCodigoAC = lotacaoCodigoAC;
	}
	public String getTipoSalario()
	{
		return tipoSalario;
	}
	public void setTipoSalario(String tipoSalario)
	{
		this.tipoSalario = tipoSalario;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public String getEmpresaCodigoAC()
	{
		return empresaCodigoAC;
	}
	public void setEmpresaCodigoAC(String empresaCodigoAC)
	{
		this.empresaCodigoAC = empresaCodigoAC;
	}
	public String getEmpregadoCodigoAC()
	{
		return empregadoCodigoAC;
	}
	public void setEmpregadoCodigoAC(String empregadoCodigoAC)
	{
		this.empregadoCodigoAC = empregadoCodigoAC;
	}

	public String getEmpregadoCodigoACDestino() {
		return empregadoCodigoACDestino;
	}
	
	public void setEmpregadoCodigoACDestino(String empregadoCodigoACDestino) {
		this.empregadoCodigoACDestino = empregadoCodigoACDestino;
	}
	
	public Double getValorAnterior()
	{
		return valorAnterior;
	}
	public void setValorAnterior(Double valorAnterior)
	{
		this.valorAnterior = valorAnterior;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	
	public Date getDataFormatada()
	{
		return DateUtil.montaDataByString(this.data);
	}
	public String getExpAgenteNocivo()
	{
		return expAgenteNocivo;
	}
	public void setExpAgenteNocivo(String expAgenteNocivo)
	{
		this.expAgenteNocivo = expAgenteNocivo;
	}
	public Integer getMovimentoSalarialId()
	{
		return movimentoSalarialId;
	}
	public void setMovimentoSalarialId(Integer movimentoSalarialId)
	{
		this.movimentoSalarialId = movimentoSalarialId;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getCategoriaESocial() {
		return categoriaESocial;
	}

	public void setCategoriaESocial(String categoriaESocial) {
		this.categoriaESocial = categoriaESocial;
	}
}