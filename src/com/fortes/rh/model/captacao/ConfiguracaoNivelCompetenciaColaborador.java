package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoNivelCompetenciaColaborador_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetenciaColaborador extends AbstractModel implements Serializable
{
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private Colaborador avaliador;
	@ManyToOne
	private ColaboradorQuestionario colaboradorQuestionario;
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@Temporal(TemporalType.DATE)
	private Date data;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="configuracaoNivelCompetenciaColaborador")
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial;
	
	public ConfiguracaoNivelCompetenciaColaborador()
	{
	}
	
	public Colaborador getColaborador() 
	{
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador) 
	{
		this.colaborador = colaborador;
	}
	
	public void setColaboradorNome(String colaboradorNome) 
	{
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setNome(colaboradorNome);
	}
	
	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) 
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public Date getData() 
	{
		return data;
	}
	
	public String getDataFormatada() 
	{
		return DateUtil.formataDiaMesAno(data);
	}

	public void setData(Date data) 
	{
		this.data = data;
	}
	
	public void setProjectionColaboradorId(Long colaboradorId)
	{
		inicializarColaborador();
		colaborador.setId(colaboradorId);
	}

	private void inicializarColaborador() {
		if (colaborador == null)
			colaborador = new Colaborador();
	}
	
	public void setProjectionColaboradorNome(String nome)
	{
		inicializarColaborador();
		colaborador.setNome(nome);
	}
	
	public void setProjectionCargoId(Long cargoId)
	{
		inicializarFaixaSalarial();
		
		if (faixaSalarial.getCargo() == null)
			faixaSalarial.setCargo(new Cargo());
		
		faixaSalarial.getCargo().setId(cargoId);
	}

	public void setProjectionCargoNome(String nome)
	{
		inicializarFaixaSalarial();
		
		if (faixaSalarial.getCargo() == null)
			faixaSalarial.setCargo(new Cargo());
		
		faixaSalarial.getCargo().setNome(nome);
	}
	
	public void setProjectionFaixaSalarialId(Long faixaId)
	{
		inicializarFaixaSalarial();
		
		faixaSalarial.setId(faixaId);
	}

	public void setProjectionFaixaSalarialNome(String nome)
	{
		inicializarFaixaSalarial();
		
		faixaSalarial.setNome(nome);
	}
	
	public void inicializarFaixaSalarial()
	{
		if (faixaSalarial == null)
			faixaSalarial = new FaixaSalarial();
	}

	public void setProjectionAvaliadorNome(String nome)
	{
		inicializarAvaliador();
		getAvaliador().setNome(nome);
	}

	public void setProjectionAvaliadorId(Long avaliadorId)
	{
		inicializarAvaliador();
		getAvaliador().setId(avaliadorId);
	}
	
	private void inicializarAvaliador()
	{
		if(avaliador == null)
			avaliador = new Colaborador();
	}

	private void inicializarColaboradorQuestionario()
	{
		if(colaboradorQuestionario == null)
			colaboradorQuestionario = new ColaboradorQuestionario();
	}
	
	public void setProjectionAvaliacaoDesempenhoId(Long avaliacaoDesempenhoId)
	{
		inicializarColaboradorQuestionario();
		getColaboradorQuestionario().setProjectionAvaliacaoDesempenhoId(avaliacaoDesempenhoId);
	}
	
	public void setProjectionAvaliacaoDesempenhoTitulo(String titulo)
	{
		inicializarColaboradorQuestionario();
		getColaboradorQuestionario().setProjectionAvaliacaoDesempenhoTitulo(titulo);
	}
	
	public void setProjectionAvaliacaoDesempenhoAnonima(Boolean anonima)
	{
		inicializarColaboradorQuestionario();
		getColaboradorQuestionario().setProjectionAvaliacaoDesempenhoAnonima(anonima);
	}

	public void setProjectionColaboradorQuestionarioId(Long colaboradorQuestionarioId)
	{
		inicializarColaboradorQuestionario();
		colaboradorQuestionario.setId(colaboradorQuestionarioId);
	}
	
	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias()
	{
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias)
	{
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		return colaboradorQuestionario;
	}
	
	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public Colaborador getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador) {
		this.avaliador = avaliador;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial() {
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		this.configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialId(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		inicializaConfiguracaoNivelCompetenciaFaixaSalarial();
			
		this.configuracaoNivelCompetenciaFaixaSalarial.setId(configuracaoNivelCompetenciaFaixaSalarialId);
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialData(Date configuracaoNivelCompetenciaFaixaSalarialData) {
		inicializaConfiguracaoNivelCompetenciaFaixaSalarial();
			
		this.configuracaoNivelCompetenciaFaixaSalarial.setData(configuracaoNivelCompetenciaFaixaSalarialData);
	}
	
	public void setCncfNivelCompetenciaHistoricoId(Long cncfNivelCompetenciaHistoricoId) {
		inicializaConfiguracaoNivelCompetenciaFaixaSalarial();
			
		if (this.configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico() == null)
			this.configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(new NivelCompetenciaHistorico());
		
		this.configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().setId(cncfNivelCompetenciaHistoricoId);
	}
	
	public void setCncfData(Date cncfData) {
		inicializaConfiguracaoNivelCompetenciaFaixaSalarial();
			
		this.configuracaoNivelCompetenciaFaixaSalarial.setData(cncfData);
	}
	
	private void inicializaConfiguracaoNivelCompetenciaFaixaSalarial() {
		if (this.configuracaoNivelCompetenciaFaixaSalarial == null)
			this.configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
	}
}
