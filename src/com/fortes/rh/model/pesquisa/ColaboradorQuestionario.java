package com.fortes.rh.model.pesquisa;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.BooleanUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorquestionario_sequence", allocationSize=1)
public class ColaboradorQuestionario extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date respondidaEm;
    private Boolean respondida = false;
    private boolean respondidaParcialmente;
	@ManyToOne
    private Colaborador colaborador; // em Avaliações, este é o Avaliado
	@ManyToOne(fetch = FetchType.LAZY)
	private Candidato candidato;
    @ManyToOne
    private Questionario questionario;
    @ManyToOne
    private Turma turma;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Avaliacao avaliacao;
    @ManyToOne(fetch=FetchType.LAZY)
    private Solicitacao solicitacao;
    @ManyToOne(fetch=FetchType.LAZY)
    private AvaliacaoCurso avaliacaoCurso;
    
    private Double performance; // desempenho de Avaliações
    private Double performanceNivelCompetencia;
    @Lob
    private String observacao;
    
    // Vínculos para Avaliação de Desempenho
    @ManyToOne
    private Colaborador avaliador;
    @ManyToOne
    private AvaliacaoDesempenho avaliacaoDesempenho;
    @OneToOne
    private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
    private Integer pesoAvaliador = 1;
    
    @Transient
    private String pessoaNome;
	@Transient
    private String pessoaVinculo;
    @Transient
    private String nomeCursoTurmaAvaliacao;
    @Transient
    private Integer qtdPeriodoExperiencia;
    @Transient
    private Integer qtdAvaliacaoDesempenho;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="colaboradorQuestionario")
    private Collection<ColaboradorResposta> colaboradorRespostas;
    
    public ColaboradorQuestionario() {
	}

    public ColaboradorQuestionario(Colaborador colaborador, Turma turma, Long questionarioId,  String questionarioTitulo, String cursoNome) 
    {
    	this.setTurma(turma);
    	this.colaborador = colaborador;
    	this.questionario = new Questionario();
    	this.questionario.setId(questionarioId);
    	this.questionario.setTitulo(questionarioTitulo);
    	this.nomeCursoTurmaAvaliacao = "Curso: "+cursoNome + " - Turma: " + turma.getDescricao() + " - Avaliação: " + questionarioTitulo;
    }
    
    // findForRankingPerformanceAvaliacaoCurso
    public ColaboradorQuestionario(Long cursoId, String cursoNome, Long turmaId, String turmaDescricao, Long colaboradorId, String colaboradorNome, Long avaliacaoCursoId, String avaliacaoCursoTitulo, Character avaliacaoCursoTipo, Double performance)
    {
    	this.setTurma(new Turma(cursoId, cursoNome, turmaId, turmaDescricao));
    	this.setProjectionColaboradorId(colaboradorId);
    	this.setProjectionColaboradorNome(colaboradorNome);
    	this.setProjectionAvaliacaoCursoId(avaliacaoCursoId);
    	this.getAvaliacaoCurso().setTitulo(avaliacaoCursoTitulo);
    	this.getAvaliacaoCurso().setTipo(avaliacaoCursoTipo);
		this.setPerformance(performance);
    }

    public ColaboradorQuestionario(AvaliacaoDesempenho avaliacaoDesempenho, Long avaliadoId, Long avaliadorId) 
    {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
		
		if (avaliacaoDesempenho != null)
			this.avaliacao = avaliacaoDesempenho.getAvaliacao();
		
		this.colaborador = new Colaborador();
		this.colaborador.setId(avaliadoId);
		
		this.avaliador = new Colaborador();
		this.avaliador.setId(avaliadorId);
	}

    public ColaboradorQuestionario(Long id, Long avaliacaoId, String avaliacaoTitulo, Long candidatoId) 
    {
    	this.setId(id);
    	this.setProjectionAvaliacaoId(avaliacaoId);
    	this.setProjectionAvaliacaoTitulo(avaliacaoTitulo);
    	this.setProjectionCandidatoId(candidatoId);
    }

	public void setAvaliadoOuAvaliador(Long colaboradorId, boolean isAvaliado) {
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		
		if (isAvaliado)
			this.colaborador = colaborador;
		else
			this.avaliador = colaborador;
	}
	
	//projections
    private void newCandidato()
    {
    	if(this.candidato == null)
    		this.candidato = new Candidato();    	
    }

    private void newPessoalCandidato()
    {
    	if(this.candidato.getPessoal() == null)
    		this.candidato.setPessoal(new Pessoal());    	
    }
    
    private void newColaborador()
    {
    	if(this.colaborador == null)
    		this.colaborador = new Colaborador();    	
    }
    
    private void newAvaliador()
    {
    	if(this.avaliador == null)
    		this.avaliador = new Colaborador();    	
    }
    
    private void newPessoalColaborador()
    {
    	if(this.colaborador.getPessoal() == null)
    		this.colaborador.setPessoal(new Pessoal());    	
    }
    
    public void setProjectionCandidatoId(Long projectionCandidatoId)
    {
    	newCandidato();
    	this.candidato.setId(projectionCandidatoId);
    }
    
    public void setProjectionAvaliacaoId(Long projectionAvaliacaoId)
    {
    	newAvaliacao();
    	this.avaliacao.setId(projectionAvaliacaoId); 	
    }
    
    public void setProjectionAvaliacaoCursoId(Long projectionAvaliacaoCursoId)
    {
    	newAvaliacaoCurso();
    	this.avaliacaoCurso.setId(projectionAvaliacaoCursoId); 	
    }
    
    public void setProjectionAvaliacaoTitulo(String projectionAvaliacaoTitulo)
    {
    	newAvaliacao();
    	this.avaliacao.setTitulo(projectionAvaliacaoTitulo); 	
    }
    
    public void setProjectionAvaliacaoTipoModelo(Character tipoModelo)
    {
    	newAvaliacao();
    	this.avaliacao.setTipoModeloAvaliacao(tipoModelo); 	
    }
    
    public void setProjectionAvaliacaoCabecalho(String projectionAvaliacaoCabecalho)
    {
    	newAvaliacao();
    	this.avaliacao.setCabecalho(projectionAvaliacaoCabecalho); 	
    }

    public void setProjectionAvaliacaoDesempenhoExibeResultadoAutoAvaliacao(Boolean projectionAvaliacaoDesempenhoExibeResultadoAutoAvaliacao)
    {
    	newAvaliacaoDesempenho();
    	this.avaliacaoDesempenho.setExibeResultadoAutoAvaliacao(projectionAvaliacaoDesempenhoExibeResultadoAutoAvaliacao == null ? false : projectionAvaliacaoDesempenhoExibeResultadoAutoAvaliacao); 	
    }
    
    public void setProjectionAvaliacaoAvaliarCompetenciasCargo(Boolean projectionAvaliacaoAvaliarCompetenciasCargo)
    {
    	newAvaliacao();    	
    	this.avaliacao.setAvaliarCompetenciasCargo(projectionAvaliacaoAvaliarCompetenciasCargo == null ? false : projectionAvaliacaoAvaliarCompetenciasCargo); 	
    }
    
    public void setProjectionAvaliacaoRespostasCompactas(Boolean projectionAvaliacaoRespostasCompactas)
    {
    	newAvaliacao();    	
    	this.avaliacao.setRespostasCompactas(projectionAvaliacaoRespostasCompactas == null ? false : projectionAvaliacaoRespostasCompactas); 	
    }
   
    public void setProjectionAvaliacaoDesempenhoId(Long projectionAvaliacaoDesempenhoId)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setId(projectionAvaliacaoDesempenhoId); 	
    }
    
    public void setProjectionAvaliacaoDesempenhoTitulo(String projectionAvaliacaoDesempenhoTitulo)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setTitulo(projectionAvaliacaoDesempenhoTitulo); 	
    }
    
    public void setProjectionAvaliacaoDesempenhoInicio(Date projectionAvaliacaoDesempenhoInicio)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setInicio(projectionAvaliacaoDesempenhoInicio); 	
    }
    
    public void setProjectionAvaliacaoDesempenhoFim(Date projectionAvaliacaoDesempenhoFim)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setFim(projectionAvaliacaoDesempenhoFim); 	
    }
    
    public void setProjectionAvaliacaoDesempenhoAnonima(Boolean anonima)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setAnonima(anonima == null ? false : anonima); 	
    }
    
    public void setProjectionExibirPerformanceProfissional(Boolean exibirPerformanceProfissional)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setExibirPerformanceProfissional(BooleanUtils.toBoolean(exibirPerformanceProfissional)); 	
    }
    
    public void setProjectionAvaliacaoDesempenhoPermiteAutoAvaliacao(Boolean projectionAvaliacaoDesempenhoPermiteAutoAvaliacao)
    {
    	newAvaliacaoDesempenho();
    	
    	this.avaliacaoDesempenho.setPermiteAutoAvaliacao(projectionAvaliacaoDesempenhoPermiteAutoAvaliacao == null ? false : projectionAvaliacaoDesempenhoPermiteAutoAvaliacao); 	
    }

	private void newAvaliacaoDesempenho() {
		if (avaliacaoDesempenho == null)
    		avaliacaoDesempenho = new AvaliacaoDesempenho();
	}
    
    public void setProjectionAvaliadorId(Long projectionAvaliadorId)
    {
    	if (projectionAvaliadorId != null)
    	{
	    	if (avaliador == null)
	    		avaliador = new Colaborador();
	    	
	    	this.avaliador.setId(projectionAvaliadorId);
    	}
    }

    private void newAvaliacao()
	{
    	if(this.avaliacao == null)
    		this.avaliacao = new Avaliacao();
	}

    private void newAvaliacaoCurso()
    {
    	if(this.avaliacaoCurso == null)
    		this.avaliacaoCurso = new AvaliacaoCurso();
    }
    
	public void setProjectionCandidatoNome(String projectionCandidatoNome)
    {
    	newCandidato();
    	this.candidato.setNome(projectionCandidatoNome);
    }
    
    public void setProjectionCandidatoNascimento(Date projectionCandidatoNascimento)
    {
    	newCandidato();
    	newPessoalCandidato();
    	this.candidato.getPessoal().setDataNascimento(projectionCandidatoNascimento);
    }
    
    public void setProjectionCandidatoRg(String projectionCandidatoRg)
    {
    	newCandidato();
    	newPessoalCandidato();
    	
    	this.candidato.getPessoal().setRg(projectionCandidatoRg);
    }
    
    public void setProjectionCandidatoSexo(Character projectionCandidatoSexo)
    {
    	newCandidato();
    	newPessoalCandidato();
    	
    	if(projectionCandidatoSexo == null)
    		this.candidato.getPessoal().setSexo(' ');
    	else	
    		this.candidato.getPessoal().setSexo(projectionCandidatoSexo);
    }
    
    public void setProjectionCandidatoEstadoCivil(String projectionCandidatoEstadoCivil)
    {
    	newCandidato();
    	newPessoalCandidato();
    	
    	this.candidato.getPessoal().setEstadoCivil(projectionCandidatoEstadoCivil);
    }
    
    public void setProjectionColaboradorNascimento(Date projectionColaboradorNascimento)
    {
    	newColaborador();
    	newPessoalColaborador();
    	this.colaborador.getPessoal().setDataNascimento(projectionColaboradorNascimento);
    }
    
    public void setProjectionColaboradorRg(String projectionColaboradorRg)
    {
    	newColaborador();
    	newPessoalColaborador();
    	
    	this.colaborador.getPessoal().setRg(projectionColaboradorRg);
    }
    
    public void setProjectionColaboradorSexo(Character projectionColaboradorSexo)
    {
    	newColaborador();
    	newPessoalColaborador();
    	
    	if(projectionColaboradorSexo == null)
    		this.colaborador.getPessoal().setSexo(' ');
    	else
    		this.colaborador.getPessoal().setSexo(projectionColaboradorSexo);
    }
    
    public void setProjectionColaboradorEstadoCivil(String projectionColaboradorEstadoCivil)
    {
    	newColaborador();
    	newPessoalColaborador();
    	
    	this.colaborador.getPessoal().setEstadoCivil(projectionColaboradorEstadoCivil);
    }

    public void setProjectionAreaOrganizacionalId(Long projectionAreaOrganizacionalId)
    {
    	newColaborador();
    	if(this.colaborador.getAreaOrganizacional() == null)
    		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
    	this.colaborador.getAreaOrganizacional().setId(projectionAreaOrganizacionalId);
    }

    public void setProjectionAreaOrganizacionalNome(String projectionAreaOrganizacionalNome)
    {
    	newColaborador();
    	if(this.colaborador.getAreaOrganizacional() == null)
    		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
    	this.colaborador.getAreaOrganizacional().setNome(projectionAreaOrganizacionalNome);
    }

    public void setEstabelecimentoNomeProjection(String estabelecimentoNome)
	{
    	newColaborador();
		if(this.colaborador.getEstabelecimento() == null)
			this.colaborador.setEstabelecimento(new Estabelecimento());
		this.colaborador.getEstabelecimento().setNome(estabelecimentoNome);
    }
    
    public void setProjectionAvaliadorAreaOrganizacionalNome(String areaOrganizacionalNome)
    {
    	newAvaliador();
    	if(this.avaliador.getAreaOrganizacional() == null)
    		this.avaliador.setAreaOrganizacional(new AreaOrganizacional());
    	this.avaliador.getAreaOrganizacional().setNome(areaOrganizacionalNome);
    }
    
    public void setProjectionFaixaSalarialNome(String projectionFaixaSalarialNome)
    {
    	newColaborador();
    	if(this.colaborador.getFaixaSalarial() == null)
    		this.colaborador.setFaixaSalarial(new FaixaSalarial());
    	this.colaborador.getFaixaSalarial().setNome(projectionFaixaSalarialNome);
    }
    
    public void setProjectionCargoNome(String projectionCargoNome)
    {
    	newColaborador();
    	if(this.colaborador.getFaixaSalarial() == null)
    		this.colaborador.setFaixaSalarial(new FaixaSalarial());
    	if(this.colaborador.getFaixaSalarial().getCargo() == null)
    		this.colaborador.getFaixaSalarial().setCargo(new Cargo());
    	
    	this.colaborador.getFaixaSalarial().getCargo().setNome(projectionCargoNome);
    }
    
    public void setProjectionAvaliadorFaixaSalarialNome(String faixaSalarialNome)
    {
    	newAvaliador();
    	if(this.avaliador.getFaixaSalarial() == null)
    		this.avaliador.setFaixaSalarial(new FaixaSalarial());
    	this.avaliador.getFaixaSalarial().setNome(faixaSalarialNome);
    }
    
    public void setProjectionAvaliadorCargoNome(String cargoNome)
    {
    	newAvaliador();
    	if(this.avaliador.getFaixaSalarial() == null)
    		this.avaliador.setFaixaSalarial(new FaixaSalarial());
    	if(this.avaliador.getFaixaSalarial().getCargo() == null)
    		this.avaliador.getFaixaSalarial().setCargo(new Cargo());
    	
    	this.avaliador.getFaixaSalarial().getCargo().setNome(cargoNome);
    }

    public void setProjectionColaboradorId(Long projectionColaboradorId)
    {
    	if (projectionColaboradorId != null)
    	{
	    	newColaborador();
	    	this.colaborador.setId(projectionColaboradorId);
    	}
    }
    
    public void setProjectionColaboradorDataAdmissao(Date dataAdmissao)
	{
    	newColaborador();
    	this.colaborador.setDataAdmissao(dataAdmissao);
	}

    public void setProjectionColaboradorNomeComercial(String projectionColaboradorNomeComercial)
    {
    	newColaborador();
    	this.colaborador.setNomeComercial(projectionColaboradorNomeComercial);
    }

    public void setProjectionAvaliadorNome(String projectionColaboradorNome)
    {
    	newAvaliador();
    	this.avaliador.setNome(projectionColaboradorNome);
    }
    
    public void setProjectionColaboradorNome(String projectionColaboradorNome)
    {
    	newColaborador();
    	this.colaborador.setNome(projectionColaboradorNome);
    }
    
    public void setProjectionColaboradorDesligado(boolean desligado)
    {
    	newColaborador();
    	this.colaborador.setDesligado(desligado);
    }
    
    public void setProjectionColaboradorEmpresaId(Long colaboradorEmpresaId)
    {
    	newColaborador();
    	this.colaborador.setEmpresaId(colaboradorEmpresaId);
    }
    public void setProjectionColaboradorEmpresaNome(String colaboradorEmpresaNome)
    {
    	newColaborador();
    	
    	this.colaborador.setEmpresaNome(colaboradorEmpresaNome);
    }

    public void setProjectionColaboradorContatoEmail(String projectionColaboradorContatoEmail)
    {
    	newColaborador();
    	if(this.colaborador.getContato() == null)
    		this.colaborador.setContato(new Contato());

    	this.colaborador.getContato().setEmail(projectionColaboradorContatoEmail);
    }

    public void setProjectionQuestionarioId(Long projectionQuestionarioId)
    {
    	newQuestionario();
    	this.questionario.setId(projectionQuestionarioId);
    }
    
    public void setProjectionQuestionarioTitulo(String questionarioTitulo)
    {
    	newQuestionario();
    	this.questionario.setTitulo(questionarioTitulo);
    }

    public void setProjectionQuestionarioCabecalho(String questionarioCabecalho)
    {
    	newQuestionario();
    	this.questionario.setCabecalho(questionarioCabecalho);
    }
	
    private void newQuestionario() 
	{
		if(this.questionario == null)
    		this.questionario = new Questionario();
	}

	public ColaboradorQuestionario(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}
    
 	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Questionario getQuestionario()
	{
		return questionario;
	}
	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Boolean getRespondida()
	{
		return respondida;
	}

	public String getRespondidaString()
	{
		return respondida ? "Sim" : "Não";
	}

	public void setRespondida(Boolean respondida)
	{
		this.respondida = respondida;
	}

	public Date getRespondidaEm()
	{
		return respondidaEm;
	}

	public void setRespondidaEm(Date respondidaEm)
	{
		this.respondidaEm = respondidaEm;
	}

	public Turma getTurma()
	{
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public void setProjectionTurmaId(Long turmaId)
	{
		if (this.turma == null)
			this.turma = new Turma();
		
		this.turma.setId(turmaId);
	}
	
	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	@NaoAudita
	public String getPessoaNome()
	{
		if(colaborador != null && colaborador.getId() != null)
			return colaborador.getNome();
		if(candidato != null && candidato.getId() != null)
			return candidato.getNome();

		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome)
	{
		this.pessoaNome = pessoaNome;
	}

	@NaoAudita
	public String getPessoaVinculo()
	{
		if(colaborador != null && colaborador.getId() != null)
			return "Colaborador";
		if(candidato != null && candidato.getId() != null)
			return "Candidato";
		
		return "";
	}

	public void setPessoaVinculo(String pessoaVinculo)
	{
		this.pessoaVinculo = pessoaVinculo;
	}

	public Avaliacao getAvaliacao()
	{
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao)
	{
		this.avaliacao = avaliacao;
	}

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}
	
	@NaoAudita
	public String getPerformanceFormatada() {
		if (performance == null)
			return "0%";

		return FormataPercentual(performance);
	}
	
	@NaoAudita
	public String getPerformanceNivelCompetenciaFormatada() {
		if (performanceNivelCompetencia == null)
			return "0%";
		return FormataPercentual(performanceNivelCompetencia);
	}

	@NaoAudita
	public String getPerformanceFinal() {
		if (performanceNivelCompetencia == null)
			performanceNivelCompetencia = 0.0;

		if (performance == null)
			performance = 0.0;
		
		return FormataPercentual(performance + performanceNivelCompetencia);
	}

	@NaoAudita
	private String FormataPercentual(Double numero) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt","BR"));//new DecimalFormat("0.##");
		df.applyPattern("0.##");
		
		return df.format((numero * 100)) + "%";
	}
	
	@NaoAudita
	public Double getPerformanceCalculada() 
	{
		if (performance == null)
			return 0.0;
		
		return performance * 100;
	}
	
	@NaoAudita
	public String getDataMaisTempoPeriodoExperiencia()
	{
		String dataFmt = "";
		if (respondidaEm != null)
		{
			dataFmt = DateUtil.formataDiaMesAno(respondidaEm);
		
			if ( colaborador.getDataAdmissao() != null)
				dataFmt = dataFmt + " (" + DateUtil.diferencaEntreDatas(colaborador.getDataAdmissao(), respondidaEm, false) + " dias)";
		}
		return dataFmt;
	}
	

	public String getObservacao()
	{
		if(observacao == null)
			observacao = "";
		
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public Colaborador getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador) {
		this.avaliador = avaliador;
	}
	
	public String getNomeComercialAvaliadoOuAvalidor()
	{
		String nomeComercialAvaliadoOuAvalidor = "Sem Nome Comercial";
		
		if(getAvaliador() != null && getAvaliador().getNomeComercial() != null)
			nomeComercialAvaliadoOuAvalidor = getAvaliador().getNomeComercial();

		if(getColaborador() != null && getColaborador().getNomeComercial() != null)
			nomeComercialAvaliadoOuAvalidor = getColaborador().getNomeComercial();
		
		return nomeComercialAvaliadoOuAvalidor;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}
	
	public void setAvaliacaoDesempenhoId(Long avaliacaoDesempenhoId) {
		if(this.avaliacaoDesempenho == null)
			this.avaliacaoDesempenho = new AvaliacaoDesempenho();
		
		this.avaliacaoDesempenho.setId(avaliacaoDesempenhoId);
	}
	
	@NaoAudita
	public void limparCampos() 
	{
		this.setRespondida(false);
		this.setRespondidaEm(null);
		this.setObservacao(null);
		this.setPerformance(null);
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public String getNomeCursoTurmaAvaliacao()
	{
		return nomeCursoTurmaAvaliacao;
	}

	public AvaliacaoCurso getAvaliacaoCurso() {
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso) {
		this.avaliacaoCurso = avaliacaoCurso;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas() {
		return colaboradorRespostas;
	}

	public void setColaboradorRespostas(
			Collection<ColaboradorResposta> colaboradorRespostas) {
		this.colaboradorRespostas = colaboradorRespostas;
	}

	public Double getPerformanceNivelCompetencia() {
		return performanceNivelCompetencia;
	}

	public void setPerformanceNivelCompetencia(Double performanceNivelCompetencia) {
		this.performanceNivelCompetencia = performanceNivelCompetencia;
	}

	/**
	 * Este método retorna a descrição do TipoModeloAvalicao considerando que  
	 * uma avaliação de desempenho possa estar usando um modelo de acompanhamento 
	 * do periodo de experiência.
	 */
	public String getDescricaoModeloAvaliacao() {
		
		TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
		
		if(this.avaliacaoDesempenho == null || this.avaliacaoDesempenho.getId() == null)
			return tipoModeloAvaliacao.get(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		else
			return tipoModeloAvaliacao.get(TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
	}

	public Integer getQtdPeriodoExperiencia()
	{
		return qtdPeriodoExperiencia;
	}

	public void setQtdPeriodoExperiencia(Integer qtdPeriodoExperiencia)
	{
		this.qtdPeriodoExperiencia = qtdPeriodoExperiencia;
	}

	public Integer getQtdAvaliacaoDesempenho()
	{
		return qtdAvaliacaoDesempenho;
	}

	public void setQtdAvaliacaoDesempenho(Integer qtdAvaliacaoDesempenho)
	{
		this.qtdAvaliacaoDesempenho = qtdAvaliacaoDesempenho;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() 
	{
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}
	
	public void setConfiguracaoNivelCompetenciaColaboradorId(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		inicializaConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setId(configuracaoNivelCompetenciaColaboradorId);
	}
	
	public void setConfiguracaoNivelCompetenciaColaboradorData(Date configuracaoNivelCompetenciaColaboradorData) 
	{
		inicializaConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setData(configuracaoNivelCompetenciaColaboradorData);
	}

	public void setConfiguracaoNivelCompetenciaColaboradorFaixaSalarialId(Long faixaSalarialId){
		inicializaConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setProjectionFaixaSalarialId(faixaSalarialId);
	}
	
	private void inicializaConfiguracaoNivelCompetenciaColaborador() 
	{
		if(this.configuracaoNivelCompetenciaColaborador == null)
			this.configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
	}

	public Integer getPesoAvaliador() {
		return pesoAvaliador;
	}

	public void setPesoAvaliador(Integer pesoAvaliador) {
		this.pesoAvaliador = pesoAvaliador;
	}

	public boolean isRespondidaParcialmente() {
		return respondidaParcialmente;
	}

	public void setRespondidaParcialmente(boolean respondidaParcialmente) {
		this.respondidaParcialmente = respondidaParcialmente;
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialId(Long configuracaoNivelCompetenciaFaixaSalarialId){
		inicializaConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarialId(configuracaoNivelCompetenciaFaixaSalarialId);
	}
}