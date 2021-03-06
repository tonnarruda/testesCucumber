package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaonivelcompetencia_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetencia extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial;
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	@Column
	private Character tipoCompetencia;
	@Column(name="competencia_id", nullable=false)
	private Long competenciaId;
	private Integer pesoCompetencia = 1;
	
	@OneToMany(mappedBy="configuracaoNivelCompetencia", cascade=CascadeType.ALL)
	private Collection<ConfiguracaoNivelCompetenciaCriterio> configuracaoNivelCompetenciaCriterios;
	
	@Transient
	private String competenciaDescricao;	
	@Transient
	private String competenciaObservacao;	
	@Transient
	private Collection<CriterioAvaliacaoCompetencia> criteriosAvaliacaoCompetencia;
	@Transient
	private Long cursoId;	
	@Transient
	private String cursoNome;	
	@Transient
	private NivelCompetencia nivelCompetenciaColaborador;
	@Transient
	private NivelCompetencia nivelCompetenciaFaixaSalarial;
	@Transient
	private Colaborador colaborador;
	@Transient
	private Integer avaliadorPeso;
	@Transient
	private Long avaliadorId;
	@Transient
	private Cargo cargo;
	@Transient
	private Competencia competencia;
	
	public ConfiguracaoNivelCompetencia()
	{
	}

	//findByAvaliacaaDesempenhoAndAvaliado
	public ConfiguracaoNivelCompetencia(Long colaboradorId, String colaboradorNome, Long competenciaId, String competenciaNome, Character competenciaTipo, Long nivelcompetenciaId, String nivelCompetenciaDescricao, Integer nivelCompetenciaOrdem, Long cargoId, String cargoNome)
	{
		this.colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		colaborador.setNome(colaboradorNome);

		this.competencia = new Competencia();
		this.competencia.setId(competenciaId);
		this.competencia.setTipo(competenciaTipo);
		this.competencia.setNome(competenciaNome);

		this.nivelCompetencia = new NivelCompetencia();
		this.nivelCompetencia.setId(nivelcompetenciaId);
		this.nivelCompetencia.setDescricao(nivelCompetenciaDescricao);
		this.nivelCompetencia.setOrdem(nivelCompetenciaOrdem);
		
		this.cargo = new Cargo();
		this.cargo.setId(cargoId);
		this.cargo.setNome(cargoNome);
	}
	
	// findColaboradoresCompetenciasAbaixoDoNivel
	public ConfiguracaoNivelCompetencia(BigInteger colaboradorId, String colaboradorNome, BigInteger faixaSalarialId, String empresaNome, String estabelecimentoNome, BigInteger competenciaId, Character tipoCompetencia, String nivelCompetenciaDescricao, String nivelCompetenciaColaboradorDescricao, String competenciaDescricao, BigInteger cursoId, String cursoNome)
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(colaboradorId.longValue());
		colaborador.setNome(colaboradorNome);
		colaborador.setEmpresaNome(empresaNome);
		colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		
		this.setFaixaSalarialIdProjection(faixaSalarialId.longValue());
		this.setCompetenciaId(competenciaId.longValue());
		this.setTipoCompetencia(tipoCompetencia);

		this.setNivelCompetencia(new NivelCompetencia());
		this.getNivelCompetencia().setDescricao(nivelCompetenciaDescricao);
		
		this.setNivelCompetenciaColaborador(new NivelCompetencia());
		this.getNivelCompetenciaColaborador().setDescricao(nivelCompetenciaColaboradorDescricao);
		this.setCompetenciaDescricao(competenciaDescricao);
		
		if (cursoId != null)
		{
			this.setCursoId(cursoId.longValue());
			this.setCursoNome(cursoNome);
		}
	}
	
	public ConfiguracaoNivelCompetencia(Long id, Long faixaSalarialId, Long nivelCompetenciaId, Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setId(id);
		this.setFaixaSalarialIdProjection(faixaSalarialId);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public ConfiguracaoNivelCompetencia(Character tipoCompetencia, Long competenciaId, String competenciaDescricao, String competenciaObservacao, Integer pesoCompetencia)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
		this.setCompetenciaObservacao(competenciaObservacao);
		this.setPesoCompetencia(pesoCompetencia);
	}
	
	public ConfiguracaoNivelCompetencia(Long id, Character tipoCompetencia, Long competenciaId, String competenciaDescricao, String competenciaObservacao, Long nivelCompetenciaId, String nivelCompetenciaDescricao, Integer nivelCompetenciaOrdem, Integer pesoCompetencia, Double nivelCompetenciaPercentual)
	{
		this.setId(id);
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
		this.setCompetenciaObservacao(competenciaObservacao);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
		this.setProjectionNivelCompetenciaDescricao(nivelCompetenciaDescricao);
		this.setNivelCompetenciaOrdemProjection(nivelCompetenciaOrdem);
		this.setNivelCompetenciaPercentualProjection(nivelCompetenciaPercentual);
		this.setPesoCompetencia(pesoCompetencia);
	}
	
	public ConfiguracaoNivelCompetencia(Character tipoCompetencia, Long competenciaId, Long nivelCompetenciaId, Integer nivelCompetenciaOrdem)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
		this.nivelCompetencia.setOrdem(nivelCompetenciaOrdem);
	}
	
	public ConfiguracaoNivelCompetencia(Long id, String competenciaDescricao)
	{
		this.setId(id);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	//findCompetenciaColaborador
	public ConfiguracaoNivelCompetencia(BigInteger competenciaId, String faixaCompetencia, String colaboradorNome, BigInteger colaboradorId, 
			String colaboradorNivel, Integer colaboradorOrden, BigInteger configNCColaboradorId, Date configCNData, String avaliadorNome, Boolean avaliacaoAnonima, Long configuracaoNivelCompetenciaFaixaSalarialId, Character tipoCompetencia, Long nivelCompetenciaHistoricoId)
	{
		this.competenciaId = competenciaId.longValue();
		this.competenciaDescricao = faixaCompetencia;
		
		configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		Colaborador colaborador = new Colaborador();
		if(colaboradorId != null)
			colaborador.setId(colaboradorId.longValue());
		colaborador.setNome(colaboradorNome);
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);

		if(configNCColaboradorId != null)
			configuracaoNivelCompetenciaColaborador.setId(configNCColaboradorId.longValue());
			
		configuracaoNivelCompetenciaColaborador.setData(configCNData);
		
		if(avaliadorNome != null)
		{
			Colaborador avaliador = new Colaborador();
			avaliador.setNome(avaliadorNome);
			configuracaoNivelCompetenciaColaborador.setAvaliador(avaliador);
		}

		nivelCompetenciaColaborador = new NivelCompetencia();
		nivelCompetenciaColaborador.setDescricao(colaboradorNivel);
		nivelCompetenciaColaborador.setOrdem(colaboradorOrden);
		
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(new ColaboradorQuestionario());
		configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().setAvaliacaoDesempenho(new AvaliacaoDesempenho());
		configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario().getAvaliacaoDesempenho().setAnonima(avaliacaoAnonima == null ? false : avaliacaoAnonima);
		
		configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		configuracaoNivelCompetenciaFaixaSalarial.setId(configuracaoNivelCompetenciaFaixaSalarialId);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
		this.tipoCompetencia = tipoCompetencia;
	}
	
	//findCompetenciasAndPesos
	public ConfiguracaoNivelCompetencia(Long id, Long competenciaId, Character tipoCompetencia, Integer ordem, Long avaliadorId, Integer avaliadorPeso, String nivelDescricao, Long cncfId, Long nivelcompetenciaHistoricoId)
	{
		setId(id);
		this.competenciaId = competenciaId;
		this.tipoCompetencia = tipoCompetencia;
		
		this.nivelCompetenciaColaborador = new NivelCompetencia();
		this.nivelCompetenciaColaborador.setDescricao(nivelDescricao);
		this.nivelCompetenciaColaborador.setOrdem(ordem);
		
		this.avaliadorId = avaliadorId;
		this.avaliadorPeso = avaliadorPeso;
		
		this.configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		this.configuracaoNivelCompetenciaFaixaSalarial.setId(cncfId);
		this.configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(new NivelCompetenciaHistorico());
		this.configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().setId(nivelcompetenciaHistoricoId);
	} 
	
	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}

	public Long getFaixaSalarialId() 
	{
		if (faixaSalarial == null)
			return null;
		
		return faixaSalarial.getId();
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) 
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public void setFaixaSalarialIdProjection(Long faixaSalarialId) 
	{
		inicializaFaixaSalarial();
		this.faixaSalarial.setId(faixaSalarialId);
	}
	
	public NivelCompetencia getNivelCompetencia() 
	{
		return nivelCompetencia;
	}
	
	public void setNivelCompetencia(NivelCompetencia nivelCompetencia) 
	{
		this.nivelCompetencia = nivelCompetencia;
	}
	
	public void setNivelCompetenciaIdProjection(Long nivelCompetenciaId) 
	{
		inicializaNivelCompetencia();
		this.nivelCompetencia.setId(nivelCompetenciaId);
	}
	
	public void setNivelCompetenciaPercentualProjection(Double nivelCompetenciaPercentual) 
	{
		inicializaNivelCompetencia();
		this.nivelCompetencia.setPercentual(nivelCompetenciaPercentual);
	}
	
	public void setNivelCompetenciaDescricaoProjection(String nivelCompetenciaDescricao) 
	{
		inicializaNivelCompetencia();
		this.nivelCompetencia.setDescricao(nivelCompetenciaDescricao);
	}
	
	public void setNivelCompetenciaOrdemProjection(Integer nivelCompetenciaOrdem) 
	{
		inicializaNivelCompetencia();
		this.nivelCompetencia.setOrdem(nivelCompetenciaOrdem);
	}

	private void inicializaNivelCompetencia() 
	{
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
	}
	
	public void setProjectionConfiguracaoNivelCompetenciaColaboradorId(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		inicializaConfiguracaoNivelCompetenciaColaborador();
		this.configuracaoNivelCompetenciaColaborador.setId(configuracaoNivelCompetenciaColaboradorId);
	}

	private void inicializaConfiguracaoNivelCompetenciaColaborador() 
	{
		if (this.configuracaoNivelCompetenciaColaborador == null)
			this.configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialId(Long configuracaoNivelCompetenciaFaixaSalarialId) 
	{
		if (this.configuracaoNivelCompetenciaFaixaSalarial == null)
			this.configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		
		this.configuracaoNivelCompetenciaFaixaSalarial.setId(configuracaoNivelCompetenciaFaixaSalarialId);
	}
	
	public void setProjectionNivelCompetenciaDescricao(String descricao) 
	{
		inicializaNivelCompetencia();
		
		this.nivelCompetencia.setDescricao(descricao);
	}
	
	public Long getCompetenciaId() 
	{
		return competenciaId;
	}
	
	public void setCompetenciaId(Long competenciaId) 
	{
		this.competenciaId = competenciaId;
	}
	
	public Character getTipoCompetencia() 
	{
		return tipoCompetencia;
	}
	
	public void setTipoCompetencia(Character tipoCompetencia) 
	{
		this.tipoCompetencia = tipoCompetencia;
	}

	public String getCompetenciaDescricao() 
	{
		return competenciaDescricao;
	}
	
	public String getCompetenciaDescricaoNivel() 
	{
		String retorno = competenciaDescricao;
		
		if (nivelCompetencia != null && StringUtils.isNotEmpty(nivelCompetencia.getDescricao()))
			retorno += " (" + nivelCompetencia.getDescricao() + ")";
		
		return retorno;
	}

	public void setCompetenciaDescricao(String competenciaDescricao) 
	{
		this.competenciaDescricao = competenciaDescricao;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() {
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador)
	{
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}

	public NivelCompetencia getNivelCompetenciaColaborador() {
		return nivelCompetenciaColaborador;
	}

	public void setNivelCompetenciaColaborador(NivelCompetencia nivelCompetenciaColaborador) {
		this.nivelCompetenciaColaborador = nivelCompetenciaColaborador;
	}
	
	public boolean isFaixaSalarial() {
		return (this.configuracaoNivelCompetenciaCandidato == null || this.configuracaoNivelCompetenciaCandidato.getId() == null) &&				
				(this.configuracaoNivelCompetenciaColaborador == null 
						|| this.configuracaoNivelCompetenciaColaborador.getColaborador() == null 
						|| this.configuracaoNivelCompetenciaColaborador.getColaborador().getId() == null); 
	}
	
	public boolean isColaborador() {
		return this.configuracaoNivelCompetenciaColaborador != null 
			&& this.configuracaoNivelCompetenciaColaborador.getColaborador() != null
			&& this.configuracaoNivelCompetenciaColaborador.getColaborador().getId() != null; 
	}
	
	public Long getColaboradorId() {
		if(this.configuracaoNivelCompetenciaColaborador != null && this.configuracaoNivelCompetenciaColaborador.getColaborador() != null)
			return getConfiguracaoNivelCompetenciaColaborador().getColaborador().getId();

		return null;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getCursoNome() {
		return cursoNome;
	}

	public void setCursoNome(String cursoNome) {
		this.cursoNome = cursoNome;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public String getCompetenciaObservacao() {
		return competenciaObservacao;
	}

	public void setCompetenciaObservacao(String competenciaObservacao) {
		this.competenciaObservacao = competenciaObservacao;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial() {
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		this.configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarial;
	}
	
	public void setConfiguracaoNivelCompetenciaFaixaSalarialData(Date ConfiguracaoNivelCompetenciaFaixaSalarialData)
	{
		if(this.configuracaoNivelCompetenciaFaixaSalarial == null)
			this.configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		
		this.configuracaoNivelCompetenciaFaixaSalarial.setData(ConfiguracaoNivelCompetenciaFaixaSalarialData);
	}

	public void setFaixaSalarialNome(String faixaSalarialNome)
	{
		inicializaFaixaSalarial();
		this.faixaSalarial.setNome(faixaSalarialNome);
	}
	
	public void setCargoNome(String cargoNome)
	{
		inicializaFaixaSalarial();
		this.faixaSalarial.setNomeCargo(cargoNome);
	}

	private void inicializaFaixaSalarial() 
	{
		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
	}

	public NivelCompetencia getNivelCompetenciaFaixaSalarial() {
		return nivelCompetenciaFaixaSalarial;
	}

	public void setNivelCompetenciaFaixaSalarial(NivelCompetencia nivelCompetenciaFaixaSalarial) {
		this.nivelCompetenciaFaixaSalarial = nivelCompetenciaFaixaSalarial;
	}

	public Collection<CriterioAvaliacaoCompetencia> getCriteriosAvaliacaoCompetencia() {
		return criteriosAvaliacaoCompetencia;
	}

	public void setCriteriosAvaliacaoCompetencia(
			Collection<CriterioAvaliacaoCompetencia> criteriosAvaliacaoCompetencia) {
		this.criteriosAvaliacaoCompetencia = criteriosAvaliacaoCompetencia;
	}

	public Collection<ConfiguracaoNivelCompetenciaCriterio> getConfiguracaoNivelCompetenciaCriterios() {
		return configuracaoNivelCompetenciaCriterios;
	}

	public void setConfiguracaoNivelCompetenciaCriterios(Collection<ConfiguracaoNivelCompetenciaCriterio> configuracaoNivelCompetenciaCriterios) {
		this.configuracaoNivelCompetenciaCriterios = configuracaoNivelCompetenciaCriterios;
	}

	public Integer getPesoCompetencia() {
		return pesoCompetencia;
	}

	public void setPesoCompetencia(Integer pesoCompetencia) {
		this.pesoCompetencia = pesoCompetencia;
	}

	public Long getAvaliadorId() {
		return avaliadorId;
	}

	public void setAvaliadorId(Long avaliadorId) {
		this.avaliadorId = avaliadorId;
	}

	public Integer getAvaliadorPeso() {
		return avaliadorPeso;
	}

	public void setAvaliadorPeso(Integer avaliadorPeso) {
		this.avaliadorPeso = avaliadorPeso;
	}

	public ConfiguracaoNivelCompetenciaCandidato getConfiguracaoNivelCompetenciaCandidato() {
		return configuracaoNivelCompetenciaCandidato;
	}

	public void setConfiguracaoNivelCompetenciaCandidato(ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato) {
		this.configuracaoNivelCompetenciaCandidato = configuracaoNivelCompetenciaCandidato;
	}
	
	public void setConfiguracaoNivelCompetenciaCandidatoId(Long configuracaoNivelCompetenciaCandidatoId) {
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setId(configuracaoNivelCompetenciaCandidatoId);
	}
	
	public void setConfiguracaoNivelCompetenciaCandidatoCNCFId(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setConfiguracaoNivelCompetenciaFaixaSalarialId(configuracaoNivelCompetenciaFaixaSalarialId);
	}
	
	public void setConfiguracaoNivelCompetenciaCandidatoNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) {
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setCNCFNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}
	
	public void setConfiguracaoNivelCompetenciaCandidatoCandidatoNome(String candidatoNome){
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setCandidatoNome(candidatoNome);
	}

	public void setConfiguracaoNivelCompetenciaCandidatoCandidatoId(Long candidatoId){
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setCandidatoId(candidatoId);
	}

	public void setConfiguracaoNivelCompetenciaCandidatoSolicitacaoId(Long solicitacaoId){
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setSolicitacaoId(solicitacaoId);
	}

	public void setConfiguracaoNivelCompetenciaCandidatoSolicitacaoDescricao(String solicitacaoDescricao){
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setSolicitacaoDescricao(solicitacaoDescricao);
	}
	
	public void setConfiguracaoNivelCompetenciaCandidatoSolicitacaoData(Date solicitacaoData){
		inicializaConfiguracaoNivelCompetenciaCandidato();
		this.configuracaoNivelCompetenciaCandidato.setSolicitacaoData(solicitacaoData);
	}

	private void inicializaConfiguracaoNivelCompetenciaCandidato(){
		if(this.configuracaoNivelCompetenciaCandidato == null)
			this.configuracaoNivelCompetenciaCandidato = new ConfiguracaoNivelCompetenciaCandidato();
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
}