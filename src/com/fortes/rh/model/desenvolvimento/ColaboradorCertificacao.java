package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

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
	
	@Transient
	private Turma turma; 
	@Transient
	private Boolean aprovadoNaTurma;

	public ColaboradorCertificacao() {
	}
	
	//usado por colaboradoresCertificados
	public ColaboradorCertificacao(Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorMatricula, 
			Long cargoId, String cargoNome,	Long certificacaoId, String certificacaoNome, Long cursoId, String cursoNome, 
			Date turmaDataPrevIni, Date turmaDataPrevFim,	Boolean turmaRealizada, Boolean aprovadoNaTurma, Date data,
			Integer periodicidade)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
		this.colaborador.setMatricula(colaboradorMatricula);
		this.colaborador.setHistoricoColaborador(new HistoricoColaborador());
		
		this.colaborador.getHistoricoColaborador().setCargoId(cargoId);
		this.colaborador.getHistoricoColaborador().setCargoNome(cargoNome);
		
		this.certificacao = new Certificacao();
		this.certificacao.setId(certificacaoId);
		this.certificacao.setNome(certificacaoNome);
		this.certificacao.setPeriodicidade(periodicidade);
		
		this.turma = new Turma();
		this.turma.setDataPrevIni(turmaDataPrevIni);
		this.turma.setDataPrevFim(turmaDataPrevFim);
		this.turma.setRealizada(turmaRealizada);
		
		this.turma.setCurso(new Curso());
		this.turma.getCurso().setId(cursoId);
		this.turma.getCurso().setNome(cursoNome);
		
		this.aprovadoNaTurma = aprovadoNaTurma;
		this.data = data;
	}
	
	//usado por certificacoesByColaboradorTurmaId
	public ColaboradorCertificacao(Long certificacaoId, Long colaboradorId)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);

		this.certificacao = new Certificacao();
		this.certificacao.setId(certificacaoId);
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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Boolean getAprovadoNaTurma() {
		return aprovadoNaTurma;
	}
	
	public String getAprovadoNaTurmaString() {
		if(aprovadoNaTurma != null && aprovadoNaTurma)
			return "Sim";
		
		return "Não";
	}

	public void setAprovadoNaTurma(Boolean aprovadoNaTurma) {
		this.aprovadoNaTurma = aprovadoNaTurma;
	}
	
	public String getDataCertificadoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.data);
	}
	
	public String getDataVencimentoCertificacao()
	{
		Date vencimento = DateUtil.incrementaMes(this.data, this.certificacao.getPeriodicidade());
		return DateUtil.formataDiaMesAno(vencimento);
	}
	
	public void setCargoNome(String cargoNome)
	{
		inicializaColaborador();
		this.colaborador.setCargoNomeProjection(cargoNome);
	}

	public void setFaixaSalarialNome(String FaixaSalarialNome)
	{
		inicializaColaborador();
		this.colaborador.setFaixaSalarialNomeProjection(FaixaSalarialNome);
	}
	
	public void setColaboradorEmail(String colaboradorEmail)
	{
		inicializaColaborador();
		this.colaborador.setEmailColaborador(colaboradorEmail);
	}
	
	public void setColaboradorNome(String colaboradorNome)
	{
		inicializaColaborador();
		this.colaborador.setNome(colaboradorNome);
	}
	
	public void setAreaOrganizacionalAreaMaeId(Long areaOrganizacionalAreaMaeId)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalAreaMaeId(areaOrganizacionalAreaMaeId);
	}
	
	public void setAreaOrganizacionalNome(String areaOrganizacionalNome)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalNome(areaOrganizacionalNome);
	}
	
	public void setAreaOrganizacionalId(Long areaOrganizacionalId)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
	}

	private void inicializaColaborador() 
	{
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
	} 
	
	public void setCertificacaoPeriodicidade(Integer certificacaoPeriodicidade)
	{
		iniciaCertificacao();
		this.certificacao.setPeriodicidade(certificacaoPeriodicidade);
	}
	
	public void setCertificacaoNome(String CertificacaoNome)
	{
		iniciaCertificacao();
		this.certificacao.setNome(CertificacaoNome);
	}

	private void iniciaCertificacao() 
	{
		if(this.certificacao == null)
			this.certificacao = new Certificacao();
	}
}
