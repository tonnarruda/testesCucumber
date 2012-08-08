package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;


public class ComissaoReuniaoPresencaMatriz
{
	private Long colaboradorId;
	private String colaboradorNome;
	private Long comissaoReuniaoId;
	private String comissaoReuniaoTipo;
	private Date comissaoReuniaoData;
	private Boolean comissaoReuniaoPresencaPresente;
	private String comissaoReuniaoPresencaJustificativa;

	public ComissaoReuniaoPresencaMatriz(Long colaboradorId, String colaboradorNome, Long comissaoReuniaoId, String comissaoReuniaoTipo, Date comissaoReuniaoData,
											Boolean comissaoReuniaoPresencaPresente, String comissaoReuniaoPresencaJustificativa) 
	{
		this.colaboradorId = colaboradorId;
		this.colaboradorNome = colaboradorNome;
		this.comissaoReuniaoId = comissaoReuniaoId;
		this.comissaoReuniaoTipo = comissaoReuniaoTipo;
		this.comissaoReuniaoData = comissaoReuniaoData;
		this.comissaoReuniaoPresencaPresente = comissaoReuniaoPresencaPresente;
		this.comissaoReuniaoPresencaJustificativa = comissaoReuniaoPresencaJustificativa;
	}
	
	public Long getColaboradorId() {
		return colaboradorId;
	}
	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}
	public String getColaboradorNome() {
		return colaboradorNome;
	}
	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}
	public Long getComissaoReuniaoId() {
		return comissaoReuniaoId;
	}
	public void setComissaoReuniaoId(Long comissaoReuniaoId) {
		this.comissaoReuniaoId = comissaoReuniaoId;
	}
	public String getComissaoReuniaoTipo() {
		return comissaoReuniaoTipo;
	}
	public void setComissaoReuniaoTipo(String comissaoReuniaoTipo) {
		this.comissaoReuniaoTipo = comissaoReuniaoTipo;
	}
	public Date getComissaoReuniaoData() {
		return comissaoReuniaoData;
	}
	public void setComissaoReuniaoData(Date comissaoReuniaoData) {
		this.comissaoReuniaoData = comissaoReuniaoData;
	}
	public Boolean getComissaoReuniaoPresencaPresente() {
		return comissaoReuniaoPresencaPresente;
	}
	public void setComissaoReuniaoPresencaPresente(Boolean comissaoReuniaoPresencaPresente) {
		this.comissaoReuniaoPresencaPresente = comissaoReuniaoPresencaPresente;
	}
	public String getComissaoReuniaoPresencaJustificativa() {
		return comissaoReuniaoPresencaJustificativa;
	}
	public void setComissaoReuniaoPresencaJustificativa(String comissaoReuniaoPresencaJustificativa) {
		this.comissaoReuniaoPresencaJustificativa = comissaoReuniaoPresencaJustificativa;
	}
}
