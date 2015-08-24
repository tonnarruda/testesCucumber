package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TCargo implements Serializable
{
	private Long id = 0L;
	private String empresaCodigoAC;
	private String codigo;//codigoac
	private Long cargoId;//rh, não vai  ser editada
	private String cargoDescricao;//rh, não vai  ser editada
	private String descricao;//faixa no rh
	private String descricaoACPessoal;//ac cargo
	private String cboCodigo;
	private String cboDescricao;
	private String grupoAC;
	private TFeedbackPessoalWebService feedback;

	public TCargo()
	{
	}
	
	public TCargo(String empresaCodigoAC, String grupoAC, String codigo, String descricao)
	{
		super();
		this.empresaCodigoAC = empresaCodigoAC;
		this.grupoAC = grupoAC;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public TCargo(Long id, String codigo, String descricao)
	{
		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public TCargo(Long idCargo, String nomeCargo)
	{
		super();
		this.cargoId = idCargo;
		this.cargoDescricao = nomeCargo;
	}
	
	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}


	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricaoACPessoal()
	{
		return descricaoACPessoal;
	}

	public void setDescricaoACPessoal(String descricaoACPessoal)
	{
		this.descricaoACPessoal = descricaoACPessoal;
	}

	public String getEmpresaCodigoAC()
	{
		return empresaCodigoAC;
	}

	public void setEmpresaCodigoAC(String empresaCodigoAC)
	{
		this.empresaCodigoAC = empresaCodigoAC;
	}

	public String getCargoDescricao()
	{
		return cargoDescricao;
	}

	public void setCargoDescricao(String cargoDescricao)
	{
		this.cargoDescricao = cargoDescricao;
	}

	public String getCboCodigo()
	{
		return cboCodigo;
	}

	public void setCboCodigo(String cboCodigo)
	{
		this.cboCodigo = cboCodigo;
	}

	public Long getCargoId()
	{
		return cargoId;
	}

	public void setCargoId(Long cargoId)
	{
		this.cargoId = cargoId;
	}

	public TFeedbackPessoalWebService getFeedback() {
		return feedback;
	}

	public void setFeedback(TFeedbackPessoalWebService feedback) {
		this.feedback = feedback;
	}

	public String getCboDescricao() {
		return cboDescricao;
	}

	public void setCboDescricao(String cboDescricao) {
		this.cboDescricao = cboDescricao;
	}
}