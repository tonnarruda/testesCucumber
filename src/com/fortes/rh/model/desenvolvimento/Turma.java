package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MathUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="turma_sequence", allocationSize=1)
public class Turma extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=100)
	private String descricao;
	@Column(length=100)
	private String instrutor;
	@Column(length=100)
	private String instituicao;
	@Column(length=20)
	private String horario;
	private Double custo;
	@Temporal(TemporalType.DATE)
	private Date dataPrevIni;
	@Temporal(TemporalType.DATE)
	private Date dataPrevFim;
	@ManyToOne
	private Empresa empresa;
	@ManyToOne
	private Curso curso;
	private boolean realizada = false;
	private Integer qtdParticipantesPrevistos;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<AvaliacaoTurma> avaliacaoTurmas;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="turma")
	private Collection<ColaboradorTurma> colaboradorTurmas;

	// Utilizado na Matriz de Qualificação
	@Transient
	private Integer pontuacao;
	@Transient
	private Integer qtdPessoas;
	@Transient
	private Integer qtdAvaliacoes;
	@Transient
	private Double diasEstimadosParaAprovacao;
	//utilizado para não deixar informações sejam alteradas após alguma frequencia ser informada
	@Transient
	private Boolean temPresenca;

	public Turma()
	{
	}
	
	public Turma(Long cursoId, String cursoNome, Long id, String descricao, Date dataPrevIni, Date dataPrevFim, Double custo, Integer qtdPessoas)
	{
		setCursoId(cursoId);
		curso.setNome(cursoNome);
		setId(id);
		this.descricao = descricao;
		this.dataPrevIni = dataPrevIni;
		this.dataPrevFim = dataPrevFim;
		this.custo = custo;
		this.qtdPessoas = qtdPessoas;
	}

	public Turma(Long id, Double diasEstimadosParaAprovacao)
	{
		this.setId(id);
		if(diasEstimadosParaAprovacao == null)
			this.diasEstimadosParaAprovacao = 0.0;
		else
			this.diasEstimadosParaAprovacao = diasEstimadosParaAprovacao;	
	}
	
	private void inicializaCurso()
	{
		if(curso == null)
			curso = new Curso();
	}

	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
		   e.printStackTrace();
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto, turma.");
	   }
	}
	
	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();

		this.empresa.setId(projectionEmpresaId);
	}

	public void setCursoId(Long cursoId)
	{
		inicializaCurso();
		curso.setId(cursoId);
	}

	public void setCursoNome(String cursoNome)
	{
		inicializaCurso();
		curso.setNome(cursoNome);
	}

	public void setProjectionCursoCargaHoraria(Integer cargaHoraria)
	{
		inicializaCurso();
		if(cargaHoraria != null)
			curso.setCargaHoraria(cargaHoraria);
	}

	public void setProjectionCursoConteudoProgramatico(String conteudoProgramatico)
	{
		inicializaCurso();
		curso.setConteudoProgramatico(conteudoProgramatico);
	}

	public void setProjectionCursoCriterioAvaliacao(String criterioAvaliacao)
	{
		inicializaCurso();
		curso.setCriterioAvaliacao(criterioAvaliacao);
	}
	
	public void setProjectionCursoPercentualMinimoFrequencia(Double percentualMinimoFrequencia)
	{
		inicializaCurso();
		curso.setPercentualMinimoFrequencia(percentualMinimoFrequencia);
	}

	public Double getCusto()
	{
		return custo;
	}
	public void setCusto(Double custo)
	{
		this.custo = custo;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public String getInstrutor()
	{
		return instrutor;
	}
	public void setInstrutor(String instrutor)
	{
		this.instrutor = instrutor;
	}
	public Integer getPontuacao()
	{
		return pontuacao;
	}
	public void setPontuacao(Integer pontuacao)
	{
		this.pontuacao = pontuacao;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("descricao", this.descricao);
		string.append("dataPrevIni", this.dataPrevIni);
		string.append("dataPrevFim", this.dataPrevFim);
		string.append("custo", this.custo);
		string.append("instrutor", this.instrutor);
		string.append("pontuacao", this.pontuacao);
		return string.toString();
	}
	public Curso getCurso()
	{
		return curso;
	}
	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public Integer getQtdPessoas()
	{
		return qtdPessoas;
	}

	public void setQtdPessoas(Integer qtdPessoas)
	{
		this.qtdPessoas = qtdPessoas;
	}

	public Boolean getTemPresenca()
	{
		return temPresenca;
	}

	public void setTemPresenca(Boolean temPresenca)
	{
		this.temPresenca = temPresenca;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		this.colaboradorTurmas = colaboradorTurmas;
	}

	public Date getDataPrevFim()
	{
		return dataPrevFim;
	}

	public void setDataPrevFim(Date dataPrevFim)
	{
		this.dataPrevFim = dataPrevFim;
	}

	public Date getDataPrevIni()
	{
		return dataPrevIni;
	}

	public void setDataPrevIni(Date dataPrevIni)
	{
		this.dataPrevIni = dataPrevIni;
	}

	public String getHorario()
	{
		return horario;
	}

	public void setHorario(String horario)
	{
		this.horario = horario;
	}

	public String getInstituicao()
	{
		return instituicao;
	}

	public void setInstituicao(String instituicao)
	{
		this.instituicao = instituicao;
	}

	public Integer getQtdParticipantesPrevistos()
	{
		return qtdParticipantesPrevistos;
	}

	public void setQtdParticipantesPrevistos(Integer qtdParticipantesPrevistos)
	{
		this.qtdParticipantesPrevistos = qtdParticipantesPrevistos;
	}

	public void setEmpresaId(Long empresaDestinoId) 
	{
		if(empresa == null)
			empresa = new Empresa();
		
		empresa.setId(empresaDestinoId);
		
	}
	
	public boolean getRealizada()
	{
		return realizada;
	}

	public void setRealizada(boolean realizada)
	{
		this.realizada = realizada;
	}

	public String getPeriodoFormatado()
	{
		String periodo = "";
		if (dataPrevIni != null)
			periodo += DateUtil.formataDiaMesAno(dataPrevIni);
		if (dataPrevFim != null)
			periodo += " a " + DateUtil.formataDiaMesAno(dataPrevFim);

		return periodo;
	}

	public String getData()
	{
		String periodo = "";
		if (dataPrevIni != null)
			periodo += DateUtil.formataDiaMesAno(dataPrevIni);
		if (dataPrevFim != null && (DateUtil.diferencaEntreDatas(dataPrevIni, dataPrevFim) > 0))
			periodo += " a " + DateUtil.formataDiaMesAno(dataPrevFim);

		return periodo;
	}

	public String getDescricaoCurso()
	{
		String nome = "";
		if(curso != null && curso.getNome() != null && !curso.getNome().equals(""))
			nome = curso.getNome();
		if(descricao != null && !descricao.equals(""));
			nome += " / " + descricao;

		return nome;
	}

	public String getCustoFormatado()
	{
		String custoFmt = "";
		if (custo != null)
			custoFmt = MathUtil.formataValor(custo);

		return custoFmt;
	}

	public Double getDiasEstimadosParaAprovacao() {
		return diasEstimadosParaAprovacao;
	}

	public void setDiasEstimadosParaAprovacao(Double diasEstimadosParaAprovacao) {
		this.diasEstimadosParaAprovacao = diasEstimadosParaAprovacao;
	}

	public Collection<AvaliacaoTurma> getAvaliacaoTurmas() {
		return avaliacaoTurmas;
	}

	public void setAvaliacaoTurmas(Collection<AvaliacaoTurma> avaliacaoTurmas) {
		this.avaliacaoTurmas = avaliacaoTurmas;
	}

	public Integer getQtdAvaliacoes() {
		return qtdAvaliacoes;
	}

	public void setQtdAvaliacoes(Integer qtdAvaliacoes) {
		this.qtdAvaliacoes = qtdAvaliacoes;
	}
}