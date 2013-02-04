package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaonivelcompetencia_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetencia extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	@Column
	private Character tipoCompetencia;
	@Column(name="competencia_id", nullable=false)
	private Long competenciaId;
	
	@Transient
	private String competenciaDescricao;	
	@Transient
	private Long cursoId;	
	@Transient
	private String cursoNome;	
	@Transient
	private NivelCompetencia nivelCompetenciaColaborador;
	@Transient
	private Colaborador colaborador;
	
	public ConfiguracaoNivelCompetencia()
	{
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
	
	public ConfiguracaoNivelCompetencia(Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public ConfiguracaoNivelCompetencia(Character tipoCompetencia, Long competenciaId, Long nivelCompetenciaId)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
	}
	
	public ConfiguracaoNivelCompetencia(Long id, String competenciaDescricao)
	{
		this.setId(id);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public ConfiguracaoNivelCompetencia(String faixaCompetencia, String faixaNivel, Integer faixaOrdem, String colaboradorNome, BigInteger colaboradorId, String colaboradorNivel, Integer colaboradorOrden)
	{
		competenciaDescricao = faixaCompetencia;
		nivelCompetencia = new NivelCompetencia();
		nivelCompetencia.setDescricao(faixaNivel);
		nivelCompetencia.setOrdem(faixaOrdem);
		
		configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		Colaborador colaborador = new Colaborador();
		if(colaboradorId != null)
			colaborador.setId(colaboradorId.longValue());
		colaborador.setNome(colaboradorNome);
		configuracaoNivelCompetenciaColaborador.setColaborador(colaborador);
		
		nivelCompetenciaColaborador = new NivelCompetencia();
		nivelCompetenciaColaborador.setDescricao(colaboradorNivel);
		nivelCompetenciaColaborador.setOrdem(colaboradorOrden);
	}
	
	public ConfiguracaoNivelCompetencia(BigInteger candidatoId, String candidatoNome, String competencia, String nivel, Integer ordem)
	{
		competenciaDescricao = competencia;
		
		nivelCompetencia = new NivelCompetencia();
		nivelCompetencia.setDescricao(nivel);
		nivelCompetencia.setOrdem(ordem);
		
		if(candidatoId != null)
		{
			this.candidato = new Candidato();
			candidato.setId(candidatoId.longValue());
			candidato.setNome(candidatoNome);
		}
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
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setId(faixaSalarialId);
	}
	
	public void setCandidatoIdProjection(Long candidatoId) 
	{
		if (this.candidato == null)
			this.candidato = new Candidato();
		
		this.candidato.setId(candidatoId);
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
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
		
		this.nivelCompetencia.setId(nivelCompetenciaId);
	}
	
	public void setProjectionConfiguracaoNivelCompetenciaColaboradorId(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		if (this.configuracaoNivelCompetenciaColaborador == null)
			this.configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		
		this.configuracaoNivelCompetenciaColaborador.setId(configuracaoNivelCompetenciaColaboradorId);
	}
	
	public void setProjectionNivelCompetenciaDescricao(String descricao) 
	{
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
		
		this.nivelCompetencia.setDescricao(descricao);
	}
	
	public void setProjectionNivelCompetenciaOrdem(Integer ordem) 
	{
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
		
		this.nivelCompetencia.setOrdem(ordem);
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

	public void setCompetenciaDescricao(String competenciaDescricao) 
	{
		this.competenciaDescricao = competenciaDescricao;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() {
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(
			ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}

	public NivelCompetencia getNivelCompetenciaColaborador() {
		return nivelCompetenciaColaborador;
	}

	public void setNivelCompetenciaColaborador(NivelCompetencia nivelCompetenciaColaborador) {
		this.nivelCompetenciaColaborador = nivelCompetenciaColaborador;
	}
	
	public boolean isFaixaSalarial() {
		return this.candidato == null 
		&& (this.configuracaoNivelCompetenciaColaborador == null 
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
}