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
	@Transient
	private Date dataCertificado;

	public ColaboradorCertificacao() {
	}
	
	//usado por colabNaCertificacaoNaoCertificados
	public ColaboradorCertificacao(Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorMatricula, 
			Long cargoId, String cargoNome,	Long certificacaoId, String certificacaoNome, Long cursoId, String cursoNome, 
			Date turmaDataPrevIni, Date turmaDataPrevFim,	Boolean turmaRealizada, Boolean aprovadoNaTurma, Date dataCertificado,
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
		this.dataCertificado = dataCertificado;
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

	public Date getDataCertificado() {
		return dataCertificado;
	}

	public void setDataCertificado(Date dataCertificado) {
		this.dataCertificado = dataCertificado;
	}
	
	public String getDataCertificadoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.dataCertificado);
	}
	
	public String getDataVencimentoCertificacao()
	{
		Date vencimento = DateUtil.incrementaMes(this.dataCertificado, this.certificacao.getPeriodicidade());
		return DateUtil.formataDiaMesAno(vencimento);
	}
}
