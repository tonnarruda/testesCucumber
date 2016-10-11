package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.ConverterUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="candidatoeleicao_sequence", allocationSize=1)
public class CandidatoEleicao extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.LAZY)
	private Eleicao eleicao;
	@OneToOne
	private Colaborador candidato;
	@Transient
	private Cargo cargo;
	
	private Integer qtdVoto = 0;
	private boolean eleito = false;

	@Transient
	private Double percentualVoto=0.0;

	public CandidatoEleicao(Eleicao eleicao, Colaborador candidato)
	{
		this.eleicao = eleicao;
		this.candidato = candidato;
	}

	public CandidatoEleicao(Long eleicaoId, Integer qtdVotoNulo,Integer qtdVotoBranco,Long candidatoId,String candidatoNome,String candidatoNomeComercial, Date candidatoDataAdmissao,String candidatoFuncaoNome,Integer qtdVoto, boolean eleito)
	{
		eleicao =  new Eleicao();
		eleicao.setId(eleicaoId);
		setProjectionEleicaoQtdVotoNulo(qtdVotoNulo);
		setProjectionEleicaoQtdVotoBranco(qtdVotoBranco);
		candidato = new Colaborador();
		candidato.setId(candidatoId);
		candidato.setNome(candidatoNome);
		candidato.setNomeComercial(candidatoNomeComercial);
		candidato.setDataAdmissao(candidatoDataAdmissao);
		setEleito(eleito);
		Funcao funcao = new Funcao();
		funcao.setNome(candidatoFuncaoNome);
		candidato.setFuncao(funcao);
		this.qtdVoto = qtdVoto;
	}
	
	
	public CandidatoEleicao(Long id,Integer qtdVoto, boolean eleito, Long eleicaoId, Long candidatoId, String candidatoNome, String candidatoNomeComercial, String nomeCargo) {
		super();
		setId(id);
		this.qtdVoto = qtdVoto;
		this.eleito = eleito;
		eleicao = new Eleicao();
		eleicao.setId(eleicaoId);
		candidato = new Colaborador();
		candidato.setId(candidatoId);
		candidato.setNome(candidatoNome);
		candidato.setNomeComercial(candidatoNomeComercial);
		cargo = new Cargo();
		cargo.setNome(nomeCargo);
	}

	//Relatorio
	public String getPercentualVotoFmt()
	{
		return ConverterUtil.convertDoubleToString(percentualVoto);
	}

	public Double getPercentualVoto()
	{
		return percentualVoto;
	}

	public void setPercentualVoto(Double percentualVoto)
	{
		this.percentualVoto = percentualVoto;
	}

	//Projection
	public void setProjectionEleicaoId(Long eleicaoId)
	{
		newEleicao();
		eleicao.setId(eleicaoId);
	}
	public void setProjectionEleicaoInscricaoCandidatoIni(Date eleicaoInscricaoCandidatoIni)
	{
		if(eleicao == null)
			eleicao = new Eleicao();
		eleicao.setInscricaoCandidatoIni(eleicaoInscricaoCandidatoIni);
	}
	public void setProjectionCandidatoNomeComercial(String candidatoNomeComercial)
	{
		newCandidato();
		candidato.setNomeComercial(candidatoNomeComercial);
	}
	private void setProjectionEleicaoQtdVotoBranco(Integer qtdVotoBranco)
	{
		newEleicao();
		eleicao.setQtdVotoBranco(qtdVotoBranco);
	}
	private void setProjectionEleicaoQtdVotoNulo(Integer qtdVotoNulo)
	{
		newEleicao();
		eleicao.setQtdVotoNulo(qtdVotoNulo);
	}
	public void setProjectionCandidatoId(Long candidatoId)
	{
		newCandidato();
		candidato.setId(candidatoId);
	}
	public void setProjectionCandidatoNome(String candidatoNome)
	{
		newCandidato();
		candidato.setNome(candidatoNome);
	}
	private void newCandidato()
	{
		if(candidato == null)
			candidato = new Colaborador();
	}
	private void newEleicao()
	{
		if (eleicao == null)
			eleicao = new Eleicao();
	}

	public boolean isEleito()
	{
		return eleito;
	}

	public void setEleito(boolean eleito)
	{
		this.eleito = eleito;
	}

	public Integer getQtdVoto()
	{
		return qtdVoto == null ? 0 : qtdVoto;
	}

	public void setQtdVoto(Integer qtdVoto)
	{
		this.qtdVoto = qtdVoto;
	}

	public CandidatoEleicao()
	{
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}
	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}
	public Colaborador getCandidato()
	{
		return candidato;
	}
	public void setCandidato(Colaborador candidato)
	{
		this.candidato = candidato;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof CandidatoEleicao))
			return false;

		CandidatoEleicao candidatoEleicao = (CandidatoEleicao)object;
		if (candidatoEleicao.getCandidato() != null && candidatoEleicao.getEleicao() != null && this.getCandidato() != null && this.getEleicao() != null)
			return this.getCandidato().getId().equals(candidatoEleicao.getCandidato().getId()) && this.getEleicao().getId().equals(candidatoEleicao.getEleicao().getId());

		return false;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}