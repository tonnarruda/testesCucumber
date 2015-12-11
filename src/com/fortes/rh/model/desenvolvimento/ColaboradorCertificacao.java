package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorCertificacao_sequence", allocationSize=1)
public class ColaboradorCertificacao extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.LAZY)
	private Colaborador colaborador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Certificacao certificacao;
	
	@Temporal(TemporalType.DATE)
	private Date data;

	public ColaboradorCertificacao() {
		// TODO Auto-generated constructor stub
	}
	
	//usado por colabNaCertificacaoNaoCertificados
	public ColaboradorCertificacao(Long id, String nome, String nomeComercial, String matricula, String cargoNome, Long certificacaoId, String certificacaoNome, Long cursoId, String cursoNome, 
			Date tumaDataPrevIni, Date tumaDataPrevFim,	Boolean turmaRealizada, Boolean aprovadoNaTurma, Boolean certificado)
	{
		
		
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatada() 
	{
		return DateUtil.formataDiaMesAno(data);
	}
}
